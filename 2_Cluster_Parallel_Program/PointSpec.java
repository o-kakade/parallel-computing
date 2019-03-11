import java.util.Iterator;

/**
 * Interface PointSpec specifies the interface for an object that specifies a
 * group of points. To obtain the points:
 * <OL TYPE=1>
 * <P><LI>
 * Call the {@link #size() size()} method to obtain the number of points.
 * <P><LI>
 * Repeatedly call the {@link #hasNext() hasNext()} and {@link #next() next()}
 * methods to obtain the points themselves.
 * </OL>
 *
 * @author  Alan Kaminsky
 * @version 28-Sep-2018
 */
public interface PointSpec
	extends Iterator<Point>
	{

// Exported operations.

	/**
	 * Get the number of points.
	 *
	 * @return  Number of points.
	 */
	public int size();

	/**
	 * Determine if there are more points.
	 *
	 * @return  True if there are more points, false if not.
	 */
	public boolean hasNext();

	/**
	 * Get the next point.
	 * <P>
	 * <I>Note:</I> The <TT>next()</TT> method is permitted to return the
	 * <I>same Point object</I>, with different coordinates, on every call.
	 * Extract the coordinates from the returned point object and store them in
	 * another data structure; do not store a reference to the returned point
	 * object itself.
	 *
	 * @return  Next point.
	 *
	 * @exception  NoSuchElementException
	 *     (unchecked exception) Thrown if there are no more points.
	 */
	public Point next();

	}