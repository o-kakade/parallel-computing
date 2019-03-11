//******************************************************************************
//
// File:    LemoinVbl.java

// Version: 1.0
//
//******************************************************************************

import edu.rit.pj2.Vbl;

/**
 * Class LemoineVbl provides a reduction variable for a Lemoine Object shared by
 * multiple threads executing a {@linkplain edu.rit.pj2.ParallelStatement
 * ParallelStatement}. 
 * <P>
 * Class LemoineVbl supports the <I>parallel reduction</I> pattern. Each
 * thread creates a thread-local copy of the shared variable by calling the
 * {@link edu.rit.pj2.Loop#threadLocal(Vbl) threadLocal()} method of class
 * {@linkplain edu.rit.pj2.Loop Loop}. The reduction is performed by the shared variable's {@link
 * #reduce(Vbl) reduce()} method which gives a call to the {@link
 *  #Lemoine() max()} method from {@linkplain Lemoine}.
 * @author Omkar Kakade
 * @version 26-Sep-2018
 */
public class LemoineVbl implements Vbl {
		
		//The Lemoine Object itself.
		public Lemoine lemo;

		/**
		 * Construct an uninitialized LemoineVbl object.
		 */
		public LemoineVbl()
			{
			}

		/**
		 * Construct a new Lemoine reduction variable wrapping the given
		 * Lemoine Object.
		 * @param lemo
		 */
		public LemoineVbl
			(Lemoine lemo)
			{
			this.lemo = lemo;
			}

		/**
		 * Create a clone of this shared variable.
		 * @return The cloned object. 
		 * @exception CloneNotSupportedException
		 * 	Thrown to indicate that the clone method in class Object has been called to clone an object,
		 *  but that the object's class does not implement the Cloneable interface
		 */
		public Object clone()
			{
			LemoineVbl vbl;
			try {
				vbl = (LemoineVbl) super.clone();
				if (this.lemo != null)
					vbl.lemo = (Lemoine) this.lemo.clone();
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
			this.lemo.copy (((LemoineVbl)vbl).lemo);
			}

		/**
		 * Reduce the given Lemoine object into this shared variable. 
		 * Call to max method from {@linkplain Lemoine}.
		 * @param lemo
		 */
		public void reduce
			(Lemoine lemo)
			{
			this.lemo.max (lemo);
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
		public void reduce(Vbl vbl) {
			
			this.reduce (((LemoineVbl)vbl).lemo);
			
		}
		}

	
