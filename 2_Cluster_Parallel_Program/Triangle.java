//******************************************************************************
//
// File:    Triangle.java

// Version: 1.0
//
//******************************************************************************


import java.io.IOException;
import edu.rit.io.InStream;
import edu.rit.io.OutStream;
import edu.rit.io.Streamable;
import edu.rit.pj2.Tuple;

/**
 * Class Triangle which acts as a container class for the value of index1, index2, index3
 * and the 3 vertices in the format vertex<I>(1,2,3)</I><I>(X,Y)</I> and area.
 * Contains methods to make a copy, calculate Area and update the fields after checking maxArea
 * in the object.
 * This Class extends {@linkplain Tuple} and implements {@linkplain Streamable} and {@linkplain Cloneable} 
 * which allows it to behave as a Tuple in a parallel cluster program.
 * @author Omkar Kakade
 * @version 19-Oct-2018
 */
public class Triangle extends Tuple implements Cloneable, Streamable {
	
	
	public int index1;
	public int index2;
	public int index3;
	public double vertex1X;
	public double vertex1Y;
	public double vertex2X;
	public double vertex2Y;
	public double vertex3X;
	public double vertex3Y;
	public double area;

	/**
	 * Construct an uninitialized Triangle object.
	 */
	public Triangle() {
		
	}
	
	/**
	 * Construct an object with the given parameters.
	 * @param vertex1X
	 * @param vertex1Y
	 * @param vertex2X
	 * @param vertex2Y
	 * @param vertex3X
	 * @param vertex3Y
	 */
	public Triangle(double vertex1X, double vertex1Y,double vertex2X,double vertex2Y,double vertex3X, double vertex3Y){
		this.vertex1X = vertex1X;
        this.vertex1Y = vertex1Y;
        this.vertex2X = vertex2X;
        this.vertex2Y = vertex2Y;
        this.vertex3X = vertex3X;
        this.vertex3Y = vertex3Y;
	}
	
	/**
	 * Construct a new Triangle that is a deep copy of the given Triangle.
	 * @param triangle
	 */
	public Triangle(Triangle triangle)
    {
    copy (triangle);
    }
	
	/**
	 * Make this Triangle be a deep copy of the given Triangle Object.
	 * @param triangle
	 * @return
	 */
	public Triangle copy (Triangle triangle)
    {
	this.vertex1X = triangle.vertex1X;
    this.vertex1Y = triangle.vertex1Y;
    this.vertex2X = triangle.vertex2X;
    this.vertex2Y = triangle.vertex2Y;
    this.vertex3X = triangle.vertex3X;
    this.vertex3Y = triangle.vertex3Y;
    this.index1 = triangle.index1;
    this.index2 = triangle.index2;
    this.index3 = triangle.index3;
    this.area = triangle.area;

    return this;
    }

	/**
	 * Create a clone of this Triangle.
	 * 
	 * @return Clone
	 * @exception CloneNotSupportedException
	 * 	Thrown to indicate that the clone method in class Object has been called to clone an object,
	 *  but that the object's class does not implement the Cloneable interface
	 */
	public Object clone()
    {
    Triangle triangle = (Triangle) super.clone();
       triangle.copy (this);
       return triangle;
    }


	/**
	 * distance method which returns the Eucledian distance between two points
	 * that are supplied in the method signature.
	 * @param vertexAX
	 * @param vertexAY
	 * @param vertexBX
	 * @param vertexBY
	 * @return dist
	 */
	public double distance(double vertexAX, double vertexAY, double vertexBX, double vertexBY) {
		
		double dist;
		double x = (vertexBX-vertexAX)*(vertexBX-vertexAX);
		double y = (vertexBY-vertexAY)*(vertexBY-vertexAY);
		dist = Math.sqrt(x+y);
		return dist;
	}	
	
	/**
	 * calcArea method which calculates the Area calls maxArea()
	 * to update the values of the object.
	 * @param vertex1X
	 * @param vertex1Y
	 * @param vertex2X
	 * @param vertex2Y
	 * @param vertex3X
	 * @param vertex3Y
	 * @param index1
	 * @param index2
	 * @param index3
	 */
	public void calcArea(double vertex1X,double vertex1Y,double vertex2X,double vertex2Y,double vertex3X, double vertex3Y, int index1, int index2, int index3) {
	 double a = 0.0;
	 double b = 0.0;
	 double c = 0.0;

	 a = distance(vertex1X,vertex1Y, vertex2X, vertex2Y);
	 b = distance(vertex2X,vertex2Y,vertex3X,vertex3Y);
	 c = distance(vertex3X,vertex3Y,vertex1X, vertex1Y);
	double s = ((a+b+c)/2);
	double area = Math.sqrt((s*(s-a)*(s-b)*(s-c)));
	
	maxArea(area, index1, index2, index3, vertex1X, vertex1Y,vertex2X,vertex2Y,vertex3X,vertex3Y);
	
}
	
	/**
	 * Calculate and update the fields of the object if the area is greater.
	 * @param area
	 * @param index1
	 * @param index2
	 * @param index3
	 * @param vertex1X
	 * @param vertex1Y
	 * @param vertex2X
	 * @param vertex2Y
	 * @param vertex3X
	 * @param vertex3Y
	 */
	public void maxArea(double area, int index1, int index2, int index3,double vertex1X,double vertex1Y,double vertex2X,double vertex2Y,double vertex3X, double vertex3Y)
	{

		if (area > this.area) {
			 this.area = area;
			 this.index1 = index1;
			 this.index2 = index2;
			 this.index3 = index3;
			 this.vertex1X = vertex1X;
			 this.vertex1Y = vertex1Y;
			 this.vertex2X = vertex2X;
			 this.vertex2Y = vertex2Y;
			 this.vertex3X = vertex3X;
			 this.vertex3Y = vertex3Y;
		 }
		
			
	}
	
	/**
	 * Method called while performing reduction.
	 * Reduces given triangle to this triangle if the condition is satisfied.
	 * @param triangle
	 */
	public void maxAreaTri(Triangle triangle) {
		if(triangle.area > this.area) {
			this.area = triangle.area;
			this.index1 = triangle.index1;
			this.index2 = triangle.index2;
			this.index3 = triangle.index3;
			this.vertex1X = triangle.vertex1X;
			this.vertex1Y = triangle.vertex1Y;
			this.vertex2X = triangle.vertex2X;
			this.vertex2Y = triangle.vertex2Y;
			this.vertex3X = triangle.vertex3X;
			this.vertex3Y = triangle.vertex3Y;
		}
			
	}
	

	/**
	 * readIn Out method for Object Streaming.
	 * @throws IOException
	 */
	@Override
	public void readIn(InStream in) throws IOException {
		
		index1 = in.readInt();
		index2 = in.readInt();
		index3 = in.readInt();
		vertex1X = in.readDouble();
		vertex1Y = in.readDouble();
		vertex2X = in.readDouble();
		vertex2Y = in.readDouble();
		vertex3X = in.readDouble();
		vertex3Y = in.readDouble();
		area = in.readDouble();
	}

	
	/**
	 * write Out method for Object Streaming.
	 * @throws IOException
	 */
	@Override
	public void writeOut(OutStream out) throws IOException {
		
		out.writeInt(index1);
		out.writeInt(index2);
		out.writeInt(index3);
		out.writeDouble(vertex1X);
		out.writeDouble(vertex1Y);
		out.writeDouble(vertex2X);
		out.writeDouble(vertex2Y);
		out.writeDouble(vertex3X);
		out.writeDouble(vertex3Y);
		out.writeDouble(area);

		
	}
	
	
}
