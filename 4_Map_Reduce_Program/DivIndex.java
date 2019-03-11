//******************************************************************************
//
// File:    DivIndex.java

// Version: 1.0
//
//******************************************************************************


import edu.rit.pjmr.Combiner;
import edu.rit.pjmr.Customizer;
import edu.rit.pjmr.Mapper;
import edu.rit.pjmr.PjmrJob;
import edu.rit.pjmr.Reducer;
import edu.rit.pjmr.TextFileSource;
import edu.rit.pjmr.TextId;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;

/**
 * Class DivIndex provides a map reduce implementation of finding the diversity index of 
 * the specified states and their counties.
 * This program extends class PjmrJob of from {@linkplain edu.rit.pjmr}. 
 * This program utilizes the DivIndexVbl Class for reduction.
 * Usage: java pj2 jar=<jar> threads=<NT> DivIndex <nodes> <file> <year> [ \"<state>\" ... ]
 * @author Omkar Kakade
 * @version 03-Dec-2018
 */
public class DivIndex extends PjmrJob<TextId, String, StateCountyObj, DivIndexVbl> {

	 /**
	    * PJMR job main program.
	    *
	    * @param  args  Command line arguments.
	    */
	   public void main
	      (String[] args) throws FileNotFoundException, IllegalArgumentException
	      {
	      // Parse command line arguments.
		  if (args.length < 3 || args.length < 2 || args.length < 1) {
				System.err.println("Please enter the required number of arguments.");
				usage();
			}
		  
	    	  try {
	    		// Parse command line arguments.
				if (Integer.parseInt(args[2]) < 1 || Integer.parseInt(args[2]) > 10){
					System.err.println("Year must be from 1-10 both inclusive.");
					usage();
					terminate (1);
					
				}
				  
				  String[] nodes = args[0].split (",");
				  String file = args[1];
				  Set<String> duplicateChecker = new HashSet<>();
				  for (int i = 3; i < args.length; i++) {
			            
			                if (duplicateChecker.contains(args[i]) ) {
			                   throw new IllegalArgumentException();
			                }
					else{
						duplicateChecker.add(args[i]);
					}
			            
			        }

				  // Determine number of mapper threads.
				  int NT = Math.max (threads(), 1);

				  // Configure mapper tasks.
				  for (String node : nodes)
				     mapperTask (node)
				        .source (new TextFileSource (file))
				        .mapper (NT, MyMapper.class,args);

				  // Configure reducer task.
				  reducerTask()
				     .customizer (MyCustomizer.class)
				     .reducer (MyReducer.class);

				  startJob();
			} catch (NumberFormatException e) {
				System.err.println("Enter the arguments in correct order.");
				System.err.println("Enter a number 1-10 for <year>");
				usage();
				terminate(1);
			} catch (IllegalArgumentException e) {
				System.err.println("Duplicate argument");
				usage();
				terminate(1);
			}
			 catch (Exception e) {
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
	      System.err.println ("Usage: java pj2 jar=<jar> threads=<NT> DivIndex <nodes> <file> <year> [ \"<state>\" ... ]");
	      terminate (1);
	      }
	   
	   /**
	    * Mapper class which maps record to <K,V> pair.
	    * @author omkar
	    *
	    */
	   private static class MyMapper extends Mapper<TextId, String, StateCountyObj, DivIndexVbl>
	   {

		   Set<String> cmdArgsHT ; 
		   double[] races;	
		   double total;
		   String year;
		   
		   /**
		    * start method with initializations.
		    */
		   public void start(String[] args, Combiner<StateCountyObj, DivIndexVbl> combiner) {
			   year = args[2];
			   cmdArgsHT = new HashSet<>();
			   for(int i = 3; i < args.length ; i++) {
				   
					   cmdArgsHT.add(args[i]);
			   }
			   races = new double[6];
			   total = 0.0;
		   }
		   
		   /**
		    * map method which maps the records to <K,V> pair.
		    */
		   @Override
		   public void map(TextId id, String contents, Combiner<StateCountyObj, DivIndexVbl> combiner) {
			   String[] contentsArray = contents.split(",");
			   String stateNameMap = contentsArray[3];
		       String countyNameMap = contentsArray[4];
		       String yearMap = contentsArray[5];

		       // if state is specified.
			   if((cmdArgsHT).contains(stateNameMap) && yearMap.equals(year) && (contentsArray[6].equals("0")) || cmdArgsHT.size() == 0 && yearMap.equals(year) && (contentsArray[6].equals("0"))) {
				   DivIndexVbl vblObj = new DivIndexVbl(6);
				   races[0] = Integer.parseInt(contentsArray[10])+Integer.parseInt(contentsArray[11]);
				   races[1] = Integer.parseInt(contentsArray[12])+Integer.parseInt(contentsArray[13]);
				   races[2] = Integer.parseInt(contentsArray[14])+Integer.parseInt(contentsArray[15]);
				   races[3] = Integer.parseInt(contentsArray[16])+Integer.parseInt(contentsArray[17]);
				   races[4] = Integer.parseInt(contentsArray[18])+Integer.parseInt(contentsArray[19]);
				   races[5] = Integer.parseInt(contentsArray[20])+Integer.parseInt(contentsArray[21]);
				   total = races[0]+races[1]+races[2]+races[3]+races[4]+races[5];
				   vblObj.setPopulation(races);
				   vblObj.setTotal(total);
				   combiner.add(new StateCountyObj(stateNameMap,countyNameMap),vblObj);
				   combiner.add(new StateCountyObj(stateNameMap," "), vblObj);
			   }
	   
		}
		   
	   }
	   
	   /**
	    * Reducer task customizer class.
	    * Sorts the whole combiner alphabetically,sorts states themselves,
	    * sorts the counties according to divIndex and sorts counties alphabetically if 
	    * divIndexes of two counties are the same.
	    * @author omkar
	    *
	    */
	   private static class MyCustomizer extends Customizer<StateCountyObj, DivIndexVbl>{
		   
		   
		   public boolean comesBefore(StateCountyObj key_1, DivIndexVbl value_1,StateCountyObj key_2, DivIndexVbl value_2) {
			   
			//Sort stateNames alphabetically.
			if(key_1.getStateName().compareTo(key_2.getStateName()) > 0) {
				
				return false;
			}
			
			//Sort states if stateNames are equal
			else if(key_2.getCountyName().equals(" ") && key_1.getStateName().equals(key_2.getStateName())) {
				return false;
			}
			
			//Sort by diversity index.
			else if(key_1.getStateName().equals(key_2.getStateName()) && !key_1.getCountyName().equals(" ") && !key_2.getCountyName().equals(" ")   && value_1.getDivIndex() < value_2.getDivIndex() ) {
				return false;
			}

			//Sort alphabetically if divIndex of two counties is same.
			else if(key_1.getStateName().equals(key_2.getStateName()) && !key_1.getCountyName().equals(" ") && !key_2.getCountyName().equals(" ")   &&value_1.getDivIndex()== value_2.getDivIndex() && key_1.getCountyName().compareTo(key_2.getCountyName())>0) {
				return false;
			}
			
			return true;

   }
	   }
	   
	   /**
	    * Reducer class which prints out the key value pairs.
	    * Also DivIndex is printed out.
	    * @author omkar
	    *
	    */
	   private static class MyReducer extends Reducer <StateCountyObj, DivIndexVbl>{

		@Override
		public void reduce(StateCountyObj key, DivIndexVbl value) {

			if (key.getCountyName().equals(" ")) {
				System.out.printf ("%s\t\t%.5g%n", key.getStateName(), value.getDivIndex());
				System.out.flush();
			}
			else {
			System.out.printf ("\t%s\t%.5g%n", key.getCountyName(), value.getDivIndex());;
	        System.out.flush();
			}
		}
		
		   
	   }

}
