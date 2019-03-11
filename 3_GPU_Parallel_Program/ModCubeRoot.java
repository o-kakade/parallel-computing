//******************************************************************************
//
// File:    ModCubeRoot.java
// Version: 1.0
//
//******************************************************************************


import edu.rit.gpu.Kernel;
import edu.rit.gpu.Gpu;
import edu.rit.gpu.GpuLongArray;
import edu.rit.gpu.GpuLongVbl;
import edu.rit.gpu.Module;
import edu.rit.pj2.Task;
import edu.rit.util.Sorting;

/**
 * Class ModCubeRoot is a GPU parallel program that calculates 
 * the modular cube roots to break the RSA public key encryption algorithm.
 * The computation done is c = m^3 (mod n)
 * where c is the ciphertext and m is the message.
 * Usage: <TT>java pj2 ModCubeRoot <I>c</I> <I>N</I></TT>
 * <BR><TT><I>c</I></TT> = number whose modular cube root(s) are to be found
 * <BR><TT><I>N</I></TT> = modulus
 * 
 * @author omkarkakade
 * @version 12-Nov-2018
 *
 */
public class ModCubeRoot
   extends Task
   {

   /**
    * Kernel function Interface
    * with argument c, n and final_M which is an array of size 3.
    * @author omkar
    *
    */
   private static interface ModCubeRootKernel
      extends Kernel
      {
      public void computeModularCubeRoot
         (long long_c ,
          long long_n,
          GpuLongArray final_M);
      }

   /**
    * Task main program.
    */
   public void main
      (String[] args)
      throws Exception
      {
	   
      // Validate command line arguments.
      if (args.length != 2) {usage();}
      
      
      
     
		  try {

			if (Integer.parseInt(args[0]) > Integer.parseInt(args[1])-1){
			System.err.println("ModCubeRoot: c must be decimal integer 0 <= c <= N-1.");
			terminate (1);
			
		}
      			if (Integer.parseInt(args[1]) < 2){
			System.err.println("ModCubeRoot: N must be decimal integer >= 2.");
			terminate (1);
			
		}
			  int c = Integer.parseInt (args[0]);
			  int N = Integer.parseInt (args[1]);
			  long long_c = (long)c;
			  long long_n = (long)N;
			  
			  // Initialize GPU.
			  Gpu gpu = Gpu.gpu();
			  gpu.ensureComputeCapability (2, 0);

			  // Set up GPU variables.
			  Module module = gpu.getModule ("ModCubeRoot.ptx");
			  GpuLongArray final_M = gpu.getLongArray (3);
			  GpuLongVbl count = module.getLongVbl ("count");
			  
			  //Initialize and send to GPU.
			  count.item = 0;
			  count.hostToDev();
			  
			  //Set up GPU Kernel.
			  ModCubeRootKernel kernel = module.getKernel (ModCubeRootKernel.class); 
			  kernel.setBlockDim (1024); 
			  kernel.setGridDim (gpu.getMultiprocessorCount()); 
			  kernel.computeModularCubeRoot (long_c, long_n, final_M);

			  // Download from GPU.
			  final_M.devToHost();
			  count.devToHost();
			  
			  // Print results.
			  long final_count = count.item;
			  int final_count_int = (int) final_count;
			  Sorting.sort(final_M.item,0,final_count_int);
			  
			  if (count.item ==0){
					System.out.println("No cube roots of " + c + " (mod " + N + ")" );
				  }
			  
			  for (int x=0; x < count.item; x++) {
				  System.out.println( final_M.item[x] + "^3" + " = " + c + " (mod " + N + ")" );
			  }
			  
		} catch (Exception e) {
			System.err.println("Illegal argument");
			usage();
			terminate(1);
		}
	
}
   
   /**
    * Print a usage message and exit.
    */
   private static void usage()
      {
      System.err.println ("Usage: java pj2 ModCubeRoot <c> <N>");
      System.err.println ("<c> = number whose modular cube root(s) are to be found");
      System.err.println ("<N> = modulus");
      terminate (1);
      }

   /**
    * Specify that this task requires one core.
    */
   protected static int coresRequired()
      {
      return 1;
      }

   /**
    * Specify that this task requires one GPU accelerator.
    */
   protected static int gpusRequired()
      {
      return 1;
      }

   }
