import java.awt.Graphics;

/**
 * @author E. Aguilar
 * @author S. Peca
 *         <p>
 *         A line segment is a 2-tuple of {@link Point} objects, p1 is the begin point, and p2 is the end point, no
 *         guarantees in order. Line segments can be compared based on whether they are above or below each other using
 *         the y-coordinates of the points contained within a line segment.
 *         </p>
 */
public class LineSegment extends GeometricObject
{
	private Point p1; // The begin endpoint.
	private Point p2; // The end endpoint.

	public LineSegment(Point begin, Point end)
	{
		this.p1 = begin;
		this.p2 = end;
	}

	// Finds the area of the triangle formed by three points using the shoelace formula.
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

	public int compareTo(LineSegment other, Point crossingPoint)
	{
		Point thisBegin, otherBegin, thisEnd, otherEnd;

		if (p1.getX() <= p2.getX())
		{
			thisBegin = p1;
			thisEnd = p2;
		} else
		{
			thisBegin = p2;
			thisEnd = p1;
		}
		
		if (other.p1.getX() <= other.p2.getX())
		{
			otherBegin = other.p1;
			otherEnd = other.p2;
		} else
		{
			otherBegin = other.p2;
			otherEnd = other.p1;
		}
		
		// Case where line segments are the same, i.e., line segment has been found.
		if (thisBegin.equals(otherBegin) && thisEnd.equals(otherEnd))
		{
			return 0;
		}
		// Case where line segments share an exact endpoint.
		else if (thisBegin.equals(otherBegin))
		{
			if (thisEnd.getY() >= otherEnd.getY())
			{
				return 1;
			} else
			{
				return -1;
			}
		}
		// General case where line segments are compared at their crossing points with the sweep line.
		else
		{
			double x, y, x0, y0; // points
			double v1, v2; // vector components in the directions of the x and y axes respectively.
			
			x = crossingPoint.getX();
			v1 = otherEnd.getX() - otherBegin.getX();
			v2 = otherEnd.getY() - otherBegin.getY();
			x0 = otherBegin.getX();
			y0 = otherBegin.getY();
			
			// Using parametric equation for lines, to obtain interior crossing points.
			double t = (x - x0) / v1;
			y = y0 + v2*t;
			
			if (Math.abs(crossingPoint.getY() - y) < Globals.POINT_EPSILON)
			{
				if (thisEnd.getY() >= otherEnd.getY())
				{
					return 1;
				} else
				{
					return -1;
				}
			}
			else if (crossingPoint.getY() > y)
			{
				return 1;
			} else
			{
				return -1;
			}
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
		 * segments intersect at exactly one endpoint, then this means that they are adjacent to each other in the
		 * polygon, and so for the purposes of this method, we will consider them not intersecting. In the case where
		 * two line segments share an endpoint.
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
		return "LineSegment: {" + p1 + ", " + p2 + "}";
	}

	public double length()
	{
		return Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2));
	}

	public boolean isHorizontal()
	{
		return Math.abs(p1.getY() - p2.getY()) < Globals.POINT_EPSILON ? true : false;
	}

	public boolean isVertical()
	{
		return Math.abs(p1.getX() - p2.getX()) < Globals.POINT_EPSILON ? true : false;
	}
	
	public Point getLeftEndpoint()
	{
		if (!isVertical())
		{
			return (p1.getX() < p2.getX()) ? p1 : p2;
		} else
		{
			return (p1.getY() > p2.getY()) ? p1 : p2;
		}
	}
	
	public Point getRightEndpoint()
	{
		if (!isVertical())
		{
			return (p1.getX() > p2.getX()) ? p1 : p2;
		} else
		{
			return (p1.getY() < p2.getY()) ? p1 : p2;
		}
	}

	@Override
	public void draw(Graphics g)
	{
		// line segment is drawn by using its boundary, not its interior
		// (width of line=0)
		
		g.setColor(getBoundaryColor());
		g.drawLine((int)p1.getX(), (int)p1.getY(), (int)p2.getX(), (int)p2.getY());
		p1.draw(g);
		p2.draw(g);
	}
}