//******************************************************************************
//
// File:    TriangleVbl.java

// Version: 1.0
//
//******************************************************************************


import java.io.IOException;
import edu.rit.io.InStream;
import edu.rit.io.OutStream;
import edu.rit.pj2.Tuple;
import edu.rit.pj2.Vbl;

/**
 * Class TriangleVbl provides a reduction variable for a Triangle Object shared by
 * multiple threads executing a {@linkplain edu.rit.pj2.ParallelStatement
 * ParallelStatement}. 
 * <P>
 * Class TriangleVbl supports the <I>parallel reduction</I> pattern. Each
 * thread creates a thread-local copy of the shared variable by calling the
 * {@link edu.rit.pj2.Loop#threadLocal(Vbl) threadLocal()} method of class
 * {@linkplain edu.rit.pj2.Loop Loop}. The reduction is performed by the shared variable's {@link
 * #reduce(Vbl) reduce()} method which gives a call to the {@link
 *  #Triangle() maxAreaTri()} method from {@linkplain Triangle}.
 *  This Class extends {@linkplain Tuple} and implements which allows it to behave as a Tuple in a parallel cluster program.
 * @author Omkar Kakade
 * @version 19-Oct-2018
 */
public class TriangleVbl extends Tuple implements Vbl {
	
	//The Triangle Object itself.
	public Triangle triangle;
	
	/**
	 * Construct an uninitialized TriangleVbl object.
	 */
	public TriangleVbl()
		{
		}

	/**
	 * Construct a new Triangle reduction variable wrapping the given
	 * Triangle Object.
	 * @param triangle
	 */
	public TriangleVbl
		(Triangle triangle)
		{
		this.triangle = triangle;
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
		TriangleVbl vbl;
		vbl = (TriangleVbl) super.clone();
		if (this.triangle != null)
			vbl.triangle = (Triangle) this.triangle.clone();
		return vbl;
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
		this.triangle.copy (((TriangleVbl)vbl).triangle);
		}

	/**
	 * Reduce the given Triangle object into this shared variable. 
	 * Call to max method from {@linkplain Triangle}.
	 * @param triangle
	 */
	public void reduce
		(Triangle triangle)
		{
		this.triangle.maxAreaTri (triangle);
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
		
		this.reduce (((TriangleVbl)vbl).triangle);
		
	}
		
	
	/**
	 * readIn method for Object Streaming.
	 * @throws IOException
	 */
	@Override
	public void readIn(InStream in) throws IOException {
			
			this.triangle = (Triangle) in.readObject();
	}

	/**
	 * writeOut method for Object Streaming.
	 * @throws IOException
	 */
	@Override
	public void writeOut(OutStream out) throws IOException {

			out.writeObject(triangle);
	}
}
