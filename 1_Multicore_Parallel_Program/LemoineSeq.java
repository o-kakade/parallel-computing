//******************************************************************************
//
// File:    LemoineSeq.java

// Version: 1.0
//
//******************************************************************************


import edu.rit.pj2.Task;
import java.lang.System;

/**
 * 
 * Class LemoineSeq provides a sequential implementation of finding the Lemoine Conjecture.
 * This program extends class Task from {@linkplain edu.rit.pj2}. 
 * This program uses {@linkplain LemoineVbl} for storing the values of n, p and q.
 * This program uses {@linkplain Prime} and uses its iterator sub class to get odd prime numbers.
 * Usage: java pj2 <TT>LemoineSeq <I>upperBound</I> <I>lowerBound</I>
 * 
 * @author Omkar Kakade
 * @version 26-Sep-2018
 */
public class LemoineSeq extends Task{
	
	
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
			System.err.println("LemoineSmp: Upper bound must be >= lower bound");
			usage();
			 terminate (1);
			
		}
		
		//Initialize fields.
		int lowerBound = Integer.parseInt(args[0]);
		int upperBound = Integer.parseInt(args[1]);
		
		
		int n = lowerBound;
	

		//Initialize Global variable.
		lemoineVbl = new LemoineVbl(new Lemoine());
		
		//Create new iterator object for each new odd n.
		Prime.Iterator primeObj = new Prime.Iterator(); 

		
		//for loop for value of n.
		for( int i =lowerBound; i<= upperBound ;i+=2){
			
			n =i;
			primeObj.restart();
			lemoineVbl.lemo.smallestP(primeObj, n);
					
		}
		
		//Print results
		System.out.println(lemoineVbl.lemo.n+" = "+lemoineVbl.lemo.p+" + 2*"+lemoineVbl.lemo.q);
		
	}
	
	
	 /**
	  * Print a usage statement and exit.
	  */
	private static void usage()
	{
        System.err.println ("Usage: java pj2 " +
          "LemoineSeq <lb> <ub> ");
        terminate (1);
        
	}
	
	/**
	 * Specify that this task requires one core.
	 * @return
	 */
	protected static int coresRequired(){
		
	return 1; 
}
	
}
