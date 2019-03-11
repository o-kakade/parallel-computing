//******************************************************************************
//
// File:    LargestTriangleSeq.java

// Version: 1.0
//
//******************************************************************************


import edu.rit.pj2.Task;
import edu.rit.util.Instance;


/**
 * 
 * Class LargestTriangleSeq provides a sequential implementation of finding the largest triangle from a given number of points.
 * This program extends class Task from {@linkplain edu.rit.pj2}. 
 * This program uses {@linkplain TriangleVbl} for storing the indexes and vertex points and also the area.
 * Program uses {@linkplain PointSpec} object to get RandomPoints.
 * Usage: java pj2 jar=<I>"jarfile"</I> <TT>LargestTriangleSeq <I>"pointspec"</I> 
 * 
 * @author Omkar Kakade
 * @version 19-Oct-2018
 */
public class LargestTriangleSeq extends Task {
	//Class Fields for PointSpec and Reduction Variable.
	PointSpec points;
	TriangleVbl triangleVbl;

	/**
	 * Main program
	 * @throws Exception
	 */
	
	public void main(String[] args)throws Exception{
		
		//Check command line arguments
		if(args.length !=1) {
			System.err.println("Please enter atleast one argument.");
			usage();
		}
		try {
			
		
		//Process command line arguments.
		String ctor = args[0];
		points = ((PointSpec)Instance.newInstance(ctor));
		double[] xPoints = new double[points.size()];
		double[] yPoints = new double[points.size()];
		
		int counter = 0;
		while (points.hasNext()) {
			
			Point currPoint = points.next();
			xPoints[counter] = currPoint.x;
			yPoints[counter] = currPoint.y;
			++counter;
			
		}
		 // fields.
		 int size =xPoints.length;
		 double vertex1X = 0.0;
         double vertex1Y = 0.0;
         double vertex2X = 0.0;
         double vertex2Y = 0.0;
         double vertex3X = 0.0;
         double vertex3Y = 0.0;
		
		//Initialize Global variable.
		triangleVbl =new TriangleVbl(new Triangle());
		
		//find 3 vertices and calculate the largest area.
		for (int i= 0; i < size-2 ; i++) {
			
			vertex1X = xPoints[i];
			vertex1Y = yPoints[i];

			for (int j= i+1; j < size-1; j++) {
				
				vertex2X = xPoints[j];
				vertex2Y = yPoints[j];

				for (int k= j+1; k < size; k++) {
					vertex3X = xPoints[k];
					vertex3Y = yPoints[k];
					

					triangleVbl.triangle.calcArea(vertex1X, vertex1Y, vertex2X,vertex2Y,vertex3X,vertex3Y, i, j,k);
				}
	
			}		

		}
		
		System.out.printf ("%d %.5g %.5g%n", triangleVbl.triangle.index1, triangleVbl.triangle.vertex1X,triangleVbl.triangle.vertex1Y);
 		System.out.printf ("%d %.5g %.5g%n", triangleVbl.triangle.index2, triangleVbl.triangle.vertex2X, triangleVbl.triangle.vertex2Y);
 		System.out.printf ("%d %.5g %.5g%n", triangleVbl.triangle.index3, triangleVbl.triangle.vertex3X, triangleVbl.triangle.vertex3Y);
 		System.out.printf ("%.5g%n", triangleVbl.triangle.area);
         
		
	}
		catch(Exception e) {
			
			System.err.println("Illegal argument");
			usage();
			terminate(1);
		}
	
	}
	
	
	/**
	* Print a usage statement and exit.
	*/
	private static void usage()
	{
       System.err.println ("Usage: java pj2 jar=<jarfile> " +
         "LargestTriangleSeq <pointspec> ");
       System.err.println ("<pointspec> = constructor expression for PointSpec object.");
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

