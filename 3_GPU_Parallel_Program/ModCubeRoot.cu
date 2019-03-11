//******************************************************************************
//
// File:    ModCubRoot.cu
//
// Version: 1.0
//******************************************************************************


// Number of threads per block.
#define NT 1024

// Overall counter variable in global memory.
__device__ unsigned long long int count;
__device__ unsigned long long int arraySize = 3;


/**
 * Device kernel to compute modular cube root.
 *
 *
 * @author  Omkar Kakade
 */
extern "C" __global__ void computeModularCubeRoot
   ( unsigned long long int c,
     unsigned long long int N,
     unsigned long long int *final_M)
   {
   unsigned long long int thr, size, rank;
   unsigned long long int local_c;
   unsigned long long int local_m;
   unsigned long long int increment;
   // Determine number of threads and this thread's rank.
   thr = threadIdx.x;
   size = gridDim.x*NT;
   rank = blockIdx.x*NT + thr;
   
   // Initialize per-thread.
   local_c = 0;
   local_m = 0;
   increment = 1;
   
   unsigned long long int atom_result =0;

   // Compute modular cube roots.
   for (unsigned long long int i = rank; i < N; i += size)
      {
      unsigned long long int first_mod = (i)%N;
      unsigned long long int second_mod = (first_mod * i)%N;
      unsigned long long int third_mod = (second_mod * i)%N;
      local_c = third_mod;
      local_m = i;
      
    
      if (local_c == c){
	  
	  // atomic counter value updation.
	  atom_result = atomicAdd(&count,increment);
	  
	  if (atom_result < arraySize) {
	  	
          	final_M[atom_result]=local_m;
		
    	}
  
	}
      }
 

}
  
   
