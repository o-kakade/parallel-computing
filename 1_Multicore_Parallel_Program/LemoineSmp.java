//******************************************************************************
//
// File:    LemoineSmp.java

// Version: 1.0
//
//******************************************************************************


import edu.rit.pj2.Loop;
import edu.rit.pj2.Task;
import java.lang.System;


/**
 * Class LemoineSmp provides a parallel implementation of finding the Lemoine Conjecture.
 * This program extends class Task of from {@linkplain edu.rit.pj2}. 
 * This program utilizes the LemoineVbl Class for reduction.
 * Usage: java pj2 cores=<I>k</I> <TT>LemoineSmp <I>upperBound</I> <I>lowerBound</I>
 * @author Omkar Kakade
 * @version 26-Sep-2018
 */
public class LemoineSmp extends Task{
	
	//Global reduction variable for max p among all numbers
	LemoineVbl lemoineVbl;
	
	/**
	 * Main program.
	 */
	public void main(String[] args)throws Exception{
		
		//check usage.
		if (args.length < 1){
			usage();
		}
		
		//Validate command line arguments.
		if (Integer.parseInt(args[0]) < 5){
			System.err.println("LemoineSeq: Lower bound must be > 5.");
			usage();
			 terminate (1);
			
		}
		
		if (Integer.parseInt(args[0])%2 == 0) {
			System.err.println("Enter odd number for lower bound.");
			usage();
			 terminate (1);
		}
		
		if (Integer.parseInt(args[1])%2 == 0) {
			System.err.println("Enter odd number for upper bound.");
			usage();
			 terminate (1);
			
		}
		
		if (Integer.parseInt(args[1]) <  Integer.parseInt(args[0])) {
			System.err.println("LemoineSmp: Upper bound must be >= lower bound.");
			usage();
			 terminate (1);
			
		}
		
		//Initialize fields.
		int lowerBound = Integer.parseInt(args[0]);
		int upperBound = Integer.parseInt(args[1]);
		
		int secondaryLB = (lowerBound+1)/2;
		int secondaryUB = (upperBound+1)/2;
		
		//Initialize Global variable.
		lemoineVbl = new LemoineVbl(new Lemoine());
		
		//for loop for value of n.
		parallelFor (secondaryLB,secondaryUB) .exec (new Loop()
		{
			//thread Local variable
			LemoineVbl thrLemoineVbl;
			
			//Prime Iterator object
			Prime.Iterator primeObj; 
			
			//start() which gets a copy of global reduction variable and initializes the Iterator.
			public void start()
			{
				thrLemoineVbl = threadLocal(lemoineVbl);	
				primeObj = new Prime.Iterator();
			}
			
			//run() which restarts the iterator and calculates p only for odd n.
			public void run(int n){
				n = n*2-1;
				primeObj.restart();
				thrLemoineVbl.lemo.smallestP(primeObj, n);

			}
			
		});

		//Print results.
		System.out.println(lemoineVbl.lemo.n+" = "+lemoineVbl.lemo.p+" + 2*"+lemoineVbl.lemo.q);
	}
	
	/**
	 * Print a usage message and exit.
	 */
	private static void usage()
	{
        System.err.println ("Usage: java pj2 " +
          "LemoineSmp <lb> <ub> ");
        terminate (1);
        
	}
	
}
