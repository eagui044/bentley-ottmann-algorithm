/**
 * @author E. Aguilar
 * @author S. Peca
 *         <p>
 *         A line segment is an ordered tuple of {@link Point} objects, p1 should be the first point, and p2 the last
 *         point. Implements the {@link Comparable} interface so that line segments can be compared based on whether
 *         they are above or below each other using the y-coordinates of their endpoints, typically p1.y is used for
 *         comparisons unless the reversedCompare flag is set, then p2.y is used.
 *         </p>
 */
public class LineSegment implements Comparable<LineSegment>
{
	private Point p1; // The first endpoint.
	private Point p2; // The second endpoint.
	private boolean reversedCompare; // Normally p1 (first point) of segment is used for comparisons, but when this flag
										// is set then p2 (second point) is used.

	public LineSegment(Point firstPoint, Point lastPoint)
	{
		this.p1 = firstPoint;
		this.p2 = lastPoint;
		reversedCompare = false;
	}

	// Finds the area of the triangle formed by three points using the shoelace
	// method.
	private double area(Point a, Point b, Point c)
	{
		return 0.5 * (a.getX() * (b.getY() - c.getY()) + b.getX() * (c.getY() - a.getY())
				+ c.getX() * (a.getY() - b.getY()));
	}

	// Given two collinear line segments, determines if they intersect at more than
	// one point
	private boolean collinearIntersects(LineSegment other)
	{
		/*
		 * NOTE: If two line segments share an endpoint, then they are adjacent to each other in the polygon and are
		 * thus NOT considered intersecting. This method checks if the line segments have any overlap at more than one
		 * point
		 */

		// We will use the x-values of the endpoints to determine intersection (and
		// y-values in the case where the line segments are vertical)

		if (Math.abs(this.p1.getX() - this.p2.getX()) < Globals.POINT_EPSILON)
		{
			// This is the case where the line segments are vertical. We take the
			// y-coordinates
			// of the top and bottom point of each line segment
			double thisTop, thisBottom;
			if (this.p1.getY() > this.p2.getY())
			{
				thisTop = this.p1.getY();
				thisBottom = this.p2.getY();
			} else
			{
				thisTop = this.p2.getY();
				thisBottom = this.p1.getY();
			}

			double otherTop, otherBottom;
			if (other.p1.getY() > other.p2.getY())
			{
				otherTop = other.p1.getY();
				otherBottom = other.p2.getY();
			} else
			{
				otherTop = other.p2.getY();
				otherBottom = other.p1.getY();
			}

			/*
			 * For the line segments to intersect, two conditions must hold: 1. The bottom point of this line segment
			 * must be below the top point of other line segment 2. The bottom point of other line segment must be below
			 * the top point of this line segment.
			 */
			if (thisBottom < otherTop && otherBottom < thisTop)
			{
				return true;
			} else
			{
				return false;
			}
		} else
		{
			// This case handles all cases where the collinear line segments are not
			// vertical.
			// We take the x coordinates of the left and right points of each line segment.
			double thisRight, thisLeft;
			if (this.p1.getX() > this.p2.getX())
			{
				thisRight = this.p1.getX();
				thisLeft = this.p2.getX();
			} else
			{
				thisRight = this.p2.getX();
				thisLeft = this.p1.getX();
			}

			double otherRight, otherLeft;
			if (other.p1.getX() > other.p2.getX())
			{
				otherRight = other.p1.getX();
				otherLeft = other.p2.getX();
			} else
			{
				otherRight = other.p2.getX();
				otherLeft = other.p1.getX();
			}

			/*
			 * For the line segments to intersect, two conditions must hold: 1. The left point of this line segment must
			 * be to the left of the right point of other line segment 2. The left point of other line segment must be
			 * to the left of the right point of this line segment
			 */
			if (thisLeft < otherRight && otherLeft < thisRight)
			{
				return true;
			} else
			{
				return false;
			}
		}
	}

	@Override
	public int compareTo(LineSegment other)
	{
		double thisY = reversedCompare ? p2.getY() : p1.getY();
		double otherY = other.reversedCompare ? other.p2.getY() : other.p1.getY();

		if (Math.abs(thisY - otherY) < Globals.POINT_EPSILON)
		{
			// TODO: May need to find better way to handle degenerate cases where the
			// y-coordinates are equal.
			return 0;
		} else if (thisY > otherY)
		{
			return 1;
		} else
		{
			return -1;
		}

	}

	// Returns only the k-component of the cross product, not the entire orthogonal
	// vector.
	// In 2D space when extending the cross product to 3D space, the i and j
	// components are zero, and only the k-component is non-zero, making it useful
	// for determining clockwise or counterclockwise orientation.
	private double crossProductK(Point v1p1, Point v1p2, Point v2p1, Point v2p2)
	{
		return (v1p2.getX() - v1p1.getX()) * (v2p2.getY() - v2p1.getY())
				- (v1p2.getY() - v1p1.getY()) * (v2p2.getX() - v2p1.getX());
	}

	public Point getP1()
	{
		return p1;
	}

	public Point getP2()
	{
		return p2;
	}

	/*
	 * Checks for line segment intersection.
	 */
	public boolean intersects(LineSegment other)
	{
		/*
		 * There are two cases 1. The line segments are collinear. In this case, we check if the line segments intersect
		 * by comparing their x-values (if the right endpoint of the leftmost line segment is to the left of the left
		 * endpoint of the rightmost line segment, then the segments do not intersect) 2. The two line segments are not
		 * collinear. In this case, we need to check that both line segments straddle each other (i.e. the directions
		 * from one line segment to the two endpoints of the other line segment are opposites). NOTE: If the line
		 * segments intersect at exactly one point, then this means that they are adjacent to each other in the polygon,
		 * and so for the purposes of this method, we will consider them not intersecting. In the case where two line
		 * segments share an endpoint,
		 */

		// First we check if the line segments share an endpoint.
		if (this.p1.equals(other.p2) || this.p2.equals(other.p1) || this.p1.equals(other.p1)
				|| this.p2.equals(other.p2))
		{
			/*
			 * The only way for these two line segments to intersect (at any point besides the point they share) is for
			 * them to be collinear and have some overlap. Thus we check for collinearity (if their cross product is
			 * zero, then they are collinear)
			 */
			if (this.isCollinearTo(other))
			{
				return collinearIntersects(other);
			} else
			{
				// If the adjacent line segments are not collinear, then they cannot intersect
				return false;
			}
		}

		// The line segments do not share an endpoint. They are parallel if their cross
		// product
		// is zero
		if (this.isCollinearTo(other))
		{
			return collinearIntersects(other);
		} else
		{
			// If the line segments are not collinear, we check if both line segments
			// straddle each other.
			// If so, then they intersect, otherwise, they do not.
			if (this.straddles(other) && other.straddles(this))
				return true;

			return false;
		}
	}

	// Checks if two line segments are collinear
	private boolean isCollinearTo(LineSegment other)
	{
		/*
		 * This method has two parts: 1. We check if the two endpoints of this line segment are collinear with p1 of
		 * other line segment, 2. We check if the two endpoints of this line segment are collinear with p2 of other line
		 * segment. To check if three points are collinear, we find the area of the triangle formed by the three points.
		 * If the area is zero, then the points are collinear
		 */

		return Math.abs(area(this.p1, this.p2, other.p1)) < Globals.POINT_EPSILON
				&& Math.abs(area(this.p1, this.p2, other.p2)) < Globals.POINT_EPSILON;
	}

	public boolean isReversedCompare()
	{
		return reversedCompare;
	}

	public void setReversedCompare(boolean reversedCompare)
	{
		this.reversedCompare = reversedCompare;
	}

	// Checks if an line segment straddles another (i.e. the directions from one
	// line segment to the two endpoints of the other line segment are opposites).
	private boolean straddles(LineSegment other)
	{
		/*
		 * We use three vectors in question: 1. The vector from the first point of "this" line segment to the first
		 * point of other line segment, 2. The vector from the first point of "this" line segment to the last point of
		 * other line segment, 3. This line segment we take the cross product of vector (1) and vector (3), and the
		 * cross product of vector (2) and vector (3). If they have the same sign (or if one of the cross products is
		 * 0), then this line segment does not "straddle" the other line segment.
		 */

		double orientationWithFirstPoint = crossProductK(this.p1, other.p1, this.p1, this.p2);
		double orientationWithLastPoint = crossProductK(this.p1, other.p2, this.p1, this.p2);

		if ((orientationWithFirstPoint < 0.0 && orientationWithLastPoint < 0.0)
				|| (orientationWithFirstPoint > 0.0 && orientationWithLastPoint > 0.0)
				|| Math.abs(orientationWithFirstPoint) < Globals.POINT_EPSILON
				|| Math.abs(orientationWithLastPoint) < Globals.POINT_EPSILON)
		{
			return false;
		}

		return true;
	}

	public String toString()
	{
		return "{" + p1 + ", " + p2 + "}";
	}
}