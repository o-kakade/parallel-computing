//******************************************************************************
//
// File:    LargestTriangleClu.java

// Version: 1.0
//
//******************************************************************************


import edu.rit.pj2.Loop;
import edu.rit.pj2.Task;
import edu.rit.util.Instance;
import java.lang.System;
import edu.rit.pj2.Job;



/**
 * Class LargestTriangleClu provides a cluster parallel implementation of finding the largest triangle from a given number of points.
 * This program extends class Job of from {@linkplain edu.rit.pj2}. 
 * This program utilizes the TriangleVbl Class for reduction.
 * Usage: java pj2 jar=<I>"jarfile"</I> workers=<I> K</I> <TT>LargestTriangleClu <I>"pointspec"</I> 
 * @author Omkar Kakade
 * @version 19-Oct-2018
 */
public class LargestTriangleClu extends Job {
		
		// Job main program.

		   /**
		    * Job main program.
		    * @throws Exception
		    */
		   public void main
		      (String[] args) throws Exception
		      {
		      // Parse command line arguments.
			   if(args.length !=1) {
					System.err.println("Please enter atleast one argument.");
					usage();
				}
			    
			    try {
			    	
			    //Get and process command line arguments.
			    String ctor = args[0];
			    PointSpec points = ((PointSpec)Instance.newInstance(ctor));
			    int size = points.size();
			    
			   
			    // Set up a task group of K worker tasks.
			    masterSchedule (proportional);
			    masterChunk (10);
			    masterFor (0, size-2, WorkerTask.class) .args (""+ctor);
		      
			    // Set up reduction task.
			    rule() .atFinish() .task (ReduceTask.class).runInJobProcess();
			    }
			    //Catch Exception
			    catch(Exception e) {
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
		      System.err.println ("Usage: java pj2 [workers=<K>] LargestTraingleClu <pointspec>");
		      System.err.println ("<K> = Number of worker tasks (default: 1)");
		      System.err.println ("<pointspec> = constructor expression for PointSpec object.");
		      terminate (1);
		      }

		// Task subclasses.

		   /**
		    * Class LargestTriangleClu.WorkerTask performs part of the computation for the
		    * LargestTriangle program.
		    *
		    * @author  Omkar Kakade	
		    * @version 19-Oct-2018
		    */
		   private static class WorkerTask
		      extends Task
		      {
		      // Command line arguments.
			   String ctor;
			   
			   // PointSpec which get PointSpec Object that has random points.
			   PointSpec points;
			   
		      // TriangleVbl reduction object
		      TriangleVbl triangleVbl;
		      
		      double[] xPoints;
	          double[] yPoints;
	          
		      /**
		       * Main program.
		       * @throws Exception
		       */
		      public void main
		         (String[] args)
		         throws Exception
		         {
		         // Parse command line arguments.
		    	  ctor = args[0];

		         // Initialize class variables.
		         triangleVbl = new TriangleVbl(new Triangle());
		         points = ((PointSpec)Instance.newInstance(ctor));
		         xPoints = new double[points.size()];
	        	 yPoints = new double[points.size()];
	        	
	        	//Get point in an array.
	        	int counter = 0;
	        	while (points.hasNext()) {
	        			
	        			Point currPoint = points.next();
	        			xPoints[counter] = currPoint.x;
	        			yPoints[counter] = currPoint.y;
	        			++counter;
	        			
	        		}
	        	
	        	  //workerFor loop for finding area of the triangle for all combinations.
		          workerFor() .schedule (dynamic).exec (new Loop()
		            {
		        	//Declare fields.
		            TriangleVbl thrTriangleVbl;
		            double vertex1X;
		            double vertex1Y;
		            double vertex2X;
		            double vertex2Y;
		            double vertex3X;
		            double vertex3Y;
		            double[] xPointsThr;
		            double[] yPointsThr;
		            int size;
		            
		            /**
		             * start() which gets a copy of global reduction variable and initializes the fields.
		             */
		            public void start() throws Exception
		               {
		            	
		            	vertex1X = 0.0;
		                vertex1Y = 0.0;
		                vertex2X = 0.0;
		                vertex2Y = 0.0;
		                vertex3X = 0.0;
		                vertex3Y = 0.0;
		        		xPointsThr = xPoints;
		        		yPointsThr = yPoints;
		        		thrTriangleVbl = threadLocal (triangleVbl);
		        		size =xPointsThr.length;
		               }
		            
		            /**
		             * run() which performs iterations.
		             */
		            public void run (int i)
		               {
		            	int locali = i;
		            	vertex1X = xPointsThr[locali];
		    			vertex1Y = yPointsThr[locali];
		        		for (int j= i+1; j < size-1; j++) {
		    				
		    				vertex2X = xPointsThr[j];
		    				vertex2Y = yPointsThr[j];

		    				for (int k= j+1; k < size; k++) {
		    					vertex3X = xPointsThr[k];
		    					vertex3Y = yPointsThr[k];
		    		
		    					thrTriangleVbl.triangle.calcArea(vertex1X, vertex1Y, vertex2X, vertex2Y,vertex3X, vertex3Y, locali,  j,  k);
		    				}
		    	
		    			}	
		        		
		               }
		            });

		         // Report results.
		         putTuple (triangleVbl);
		         }
		      }

		   /**
		    * Class LargestTriangle.ReduceTask combines the worker tasks' results and prints the
		    * overall result for the Cluster Triangle program.
		    *
		    * @author  Omkar Kakade
		    * @version 19-Oct-2018
		    */
		   private static class ReduceTask
		      extends Task
		      {
		     /**
		      * Reduce task main program.
		      * @throws Exception
		      */
		      public void main
		         (String[] args)
		         throws Exception
		         {
		         TriangleVbl triangleRed = new TriangleVbl(new Triangle());
		         TriangleVbl template = new TriangleVbl(new Triangle());
		         TriangleVbl taskCount;
		         while ((taskCount = tryToTakeTuple (template)) != null) {
		            triangleRed.reduce (taskCount);
		         }
		         
		        //Print the output.
		        System.out.printf ("%d %.5g %.5g%n", triangleRed.triangle.index1, triangleRed.triangle.vertex1X,triangleRed.triangle.vertex1Y);
		 		System.out.printf ("%d %.5g %.5g%n", triangleRed.triangle.index2, triangleRed.triangle.vertex2X, triangleRed.triangle.vertex2Y);
		 		System.out.printf ("%d %.5g %.5g%n", triangleRed.triangle.index3, triangleRed.triangle.vertex3X, triangleRed.triangle.vertex3Y);
		 		System.out.printf ("%.5g%n", triangleRed.triangle.area);
		         }
		      }
		
	}


