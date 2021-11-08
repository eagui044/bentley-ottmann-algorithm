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

	public int compareTo(LineSegment other, Point eventPoint)
	{
		Point thisLeft = this.getLeftEndpoint();
		Point thisRight = this.getRightEndpoint();
		Point otherLeft = other.getLeftEndpoint();
		Point otherRight = other.getRightEndpoint();

		// Case where line segments are the same, i.e., line segment has been found.
		if (thisLeft.equals(otherLeft) && thisRight.equals(otherRight))
		{
			return 0;
		}
		// Case where line segments share an exact endpoint.
		else if (thisLeft.equals(otherLeft))
		{
			double orientation = crossProductK(thisLeft, thisRight, otherLeft, otherRight);

			if (orientation <= 0.0)
			{
				return 1;
			} else
			{
				return -1;
			}
		}
		// General case where line segments are compared at their event points with the sweep line.
		else
		{
			double x, y, x0, y0; // points
			double v1, v2; // vector components in the directions of the x and y axes respectively.

			// This assumes that both line segments cannot be vertical, i.e., two vertical line segments should not be
			// intersecting when found by the sweep line.
			if (!other.isVertical())
			{
				x = eventPoint.getX();
				v1 = otherRight.getX() - otherLeft.getX();
				v2 = otherRight.getY() - otherLeft.getY();
				x0 = otherLeft.getX();
				y0 = otherLeft.getY();
			} else
			{
				x = otherLeft.getX();
				v1 = thisRight.getX() - thisLeft.getX();
				v2 = thisRight.getY() - thisLeft.getY();
				x0 = thisLeft.getX();
				y0 = thisLeft.getY();
			}

			// Using parametric equation for lines, to obtain interior points (i.e., non-endpoints).
			double t = (x - x0) / v1;
			y = y0 + v2 * t;

			if (t < 0.0 || t > 1.0)
			{
				System.err.println("Warning: Event point that is out of other line segment range "
						+ "is being used for comparisons, algorithm may not perform correctly.");
			}

			if (!other.isVertical())
			{
				if (Math.abs(eventPoint.getY() - y) < Globals.POINT_EPSILON)
				{
					Point crossingPoint = this.getIntersectionPointWith(other);
					double orientation = crossProductK(crossingPoint, thisRight, crossingPoint, otherRight);

					if (crossingPoint != null && crossingPoint.equals(eventPoint))
					{
						if (orientation <= 0.0)
						{
							return -1;
						} else
						{
							return 1;
						}
					} else
					{
						if (orientation <= 0.0)
						{
							return 1;
						} else
						{
							return -1;
						}
					}
				} else if (eventPoint.getY() > y)
				{
					return 1;
				} else
				{
					return -1;
				}
			} else
			{
				if (Math.abs(otherLeft.getY() - y) < Globals.POINT_EPSILON)
				{
					if (thisRight.getY() >= otherRight.getY())
					{
						return 1;
					} else
					{
						return -1;
					}
				} else if (otherLeft.getY() > y)
				{
					return -1;
				} else
				{
					return 1;
				}
			}
		}
	}

	// Returns only the k-component of the cross product, not the entire orthogonal
	// vector.
	// In 2D space when extending the cross product to 3D space, the i and j
	// components are zero, and only the k-component is non-zero, making it useful
	// for determining clockwise or counterclockwise orientation.
	// For v1 x v2, if v1 is above v2 (counterclockwise) then the k-component is negative,
	// if v1 is below v2 (clockwise) then the k-component is positive, else it is 0.0 if v1 and v2 are parallel.
	private double crossProductK(Point v1p1, Point v1p2, Point v2p1, Point v2p2)
	{
		return (v1p2.getX() - v1p1.getX()) * (v2p2.getY() - v2p1.getY())
				- (v1p2.getY() - v1p1.getY()) * (v2p2.getX() - v2p1.getX());
	}

	@Override
	public void draw(Graphics g)
	{
		// line segment is drawn by using its boundary, not its interior
		// (width of line=0)

		g.setColor(getBoundaryColor());
		g.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY());
		p1.draw(g);
		p2.draw(g);
	}

	/**
	 * Calculates the point of intersection between this and another line segment, but will return null if the line
	 * segments do not intersect or are collinear. Note that line segments that share just an endpoint are not
	 * considered intersecting.
	 * 
	 * @param other The other line segment
	 * @return The point where this and the other line segment intersect.
	 */
	public Point getIntersectionPointWith(LineSegment other)
	{
		if (this.isCollinearTo(other) || !this.intersects(other))
		{
			return null;
		} else
		{
			double x1 = p1.getX();
			double y1 = p1.getY();
			double x2 = other.p1.getX();
			double y2 = other.p1.getY();
			double m1, m2, b1, b2, x, y;

			if (!this.isVertical() && !other.isVertical())
			{
				m1 = (p2.getY() - p1.getY()) / (p2.getX() - p1.getX());
				m2 = (other.p2.getY() - other.p1.getY()) / (other.p2.getX() - other.p1.getX());
				b1 = y1 - m1 * x1;
				b2 = y2 - m2 * x2;

				x = (b2 - b1) / (m1 - m2);
				y = m1 * x + b1;
			} else if (this.isVertical())
			{
				m2 = (other.p2.getY() - other.p1.getY()) / (other.p2.getX() - other.p1.getX());
				b2 = y2 - m2 * x2;

				x = x1;
				y = m2 * x + b2;
			} else
			{
				m1 = (p2.getY() - p1.getY()) / (p2.getX() - p1.getX());
				b1 = y1 - m1 * x1;

				x = x2;
				y = m1 * x + b1;
			}

			return new Point(x, y);
		}
	}

	public Point getLeftEndpoint()
	{
		if (!isVertical())
		{
			return (p1.getX() < p2.getX()) ? p1 : p2;
		} else
		{
			return (p1.getY() < p2.getY()) ? p1 : p2;
		}
	}

	public Point getP1()
	{
		return p1;
	}

	public Point getP2()
	{
		return p2;
	}

	public Point getRightEndpoint()
	{
		if (!isVertical())
		{
			return (p1.getX() > p2.getX()) ? p1 : p2;
		} else
		{
			return (p1.getY() > p2.getY()) ? p1 : p2;
		}
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
		// product is zero
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

	public boolean isHorizontal()
	{
		return Math.abs(p1.getY() - p2.getY()) < Globals.POINT_EPSILON ? true : false;
	}

	public boolean isVertical()
	{
		return Math.abs(p1.getX() - p2.getX()) < Globals.POINT_EPSILON ? true : false;
	}

	public double length()
	{
		return Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2));
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
}