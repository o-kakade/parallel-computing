//******************************************************************************
//
// File:    DivIndexVbl.java

// Version: 1.0
//
//******************************************************************************



import java.io.IOException;
import edu.rit.io.InStream;
import edu.rit.io.OutStream;
import edu.rit.io.Streamable;
import edu.rit.pj2.Vbl;
/**
 * Class DivIndexVbl provides a reduction variable.
 * <P>
 * Class DivIndexVbl supports the <I>parallel reduction</I> pattern. Each
 * thread creates a thread-local copy of the shared variable by calling the
 * {@link edu.rit.pj2.Loop#threadLocal(Vbl) threadLocal()} method of class
 * {@linkplain edu.rit.pj2.Loop Loop}. The reduction is performed by the shared variable's {@link
 * #reduce(Vbl) reduce()} method which gives a call to the {@link
 *  #DivIndexVbl() add()} method from {@linkplain DivIndexVbl}.
 *  This Class implements Vbl and Streamable which allows it to behave as a Tuple in a map reduce program.
 * @author Omkar Kakade
 * @version 03-Dec-2018
 */

public class DivIndexVbl implements Vbl, Streamable{
	
	//The DivIndexVbl Object itself.
		private double [] population;
		private double total;
			/**
			 * Construct an initialized DivIndexValueVbl object.
			 */
			public DivIndexVbl()
				{
				population = new double [6];
				total = 0.0;
				}
			
			/**
			 * getter method for returning population array.
			 * @return population
			 */
			public double[] getPopulation() {
				return population;
			}
			
			/**
			 * setter method for setting population array.
			 * @param population
			 */
			public void setPopulation(double[] population) {
				this.population = population;
			}
			
			/**
			 * getter method for getting total field.
			 * @return total
			 */
			public double getTotal() {
				return total;
			}
			
			/**
			 * setter method for setting total field.
			 * @param total
			 */
			public void setTotal(double total) {
				this.total = total;
			}

			/** 
			 * Construct an object with the given parameters.
			 * @param divIndex
			 */
			public DivIndexVbl(int len) {
				population = new double [len];
				total = 0.0;
			}
		
			
			/**
			 * Create a clone of this DivIndexVbl.
			 * 
			 * @return Clone
			 * @exception CloneNotSupportedException
			 * 	Thrown to indicate that the clone method in class Object has been called to clone an object,
			 *  but that the object's class does not implement the Cloneable interface
			 */
			public Object clone()
			{
			
			try {
				DivIndexVbl vbl;
				vbl = (DivIndexVbl) super.clone();
				vbl.set (this);
				return vbl;
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
				return null;
			}

			/**
			 * Set this shared variable to the given shared variable.
			 * @param  vbl  Shared variable.
			 *
			 * @exception  ClassCastException
			 *     (unchecked exception) Thrown if the class of <TT>vbl</TT> is not
			 *     compatible with the class of this shared variable.
			 * 
			 */
			public void set
			(Vbl vbl)
			{
			this.population = (double[]) ((DivIndexVbl)vbl).population.clone();
			this.total = (double) ((DivIndexVbl)vbl).total;
			}

			/**
			 * Reduction methods which adds the element at the same index from the 
			 * given object's double array to this object's double array.
			 * @param i
			 * @param x
			 */
			public void add
			(int i,double x)
					{
					population[i] += x;
					}

			
			/**
			* Reduce the given shared variable into this shared variable. The two
			* variables are combined together using this shared variable's reduction
			* operation, and the result is stored in this shared variable.
			*
			* @param  vbl  Shared variable.
			*
			* @exception  ClassCastException
			*     (unchecked exception) Thrown if the class of <TT>vbl</TT> is not
			*     compatible with the class of this shared variable.
			*/
			@Override
			public void reduce
			(Vbl vbl)
			{
			reduce (((DivIndexVbl)vbl).population, ((DivIndexVbl)vbl).total);
			}
			
			/**
			 * Reduce the given DivIndexValueVbl object's array into this shared variable. 
			 * Call to add method from {@linkplain DivIndexValueVbl}.
			 * @param divIndexObj
			 */
			public void reduce
			(double[] array, double total)
			{
			int len = Math.min (population.length, array.length);
			for (int i = 0; i < len; ++ i) {
				add (i, array[i]);}
			this.total +=total;
			}
			
			/**
			 * read In method for Streaming.
			 * @throws IOException
			 */
			@Override
			public void readIn
			(InStream in)
			throws IOException
			{
			population = in.readDoubleArray();
			total = in.readDouble();
			}

			/**
			 * write Out method for Streaming.
			 * @throws IOException
			 */
			@Override
			public void writeOut
			(OutStream out)
			throws IOException
			{
			out.writeDoubleArray (population);
			out.writeDouble(total);
			}
			
			/**
			 * Calculates the diversity Index from the given array 
			 * according to the divIndex formula.
			 * @return divIndex
			 */
			public double getDivIndex() {
				double divIndex = 0.0;
				double total = this.total;
				double divIndexSum = 0.0;
				
				for (int i = 0; i < this.population.length; i++ ) {
					
					divIndexSum += ( this.population[i] * ( total - this.population[i] ) );
				}
				
				divIndex = ( 1 / (total*total) ) * divIndexSum;
				
				return divIndex;
			}	
			
}
