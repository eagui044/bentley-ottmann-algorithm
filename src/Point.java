import java.awt.Graphics;

/**
 * @author E. Aguilar
 */
public class Point extends GeometricObject
{
	private double x;
	private double y;

	public Point()
	{
		x = y = 0;
	}

	public Point(double x1, double y1)
	{
		x = x1;
		y = y1;
	}

	/**
	 * Calculates distance between this point and point <code>p</code>.
	 *
	 * @param p Point object
	 *
	 * @return distance between this point and point p
	 */
	public double distance(Point p)
	{
		return Math.sqrt(Math.pow(this.x - p.x, 2) + Math.pow(this.y - p.y, 2));
	}

	@Override
	public boolean equals(Object other)
	{
		if (!(other instanceof Point))
		{
			return false;
		}

		Point otherPoint = (Point) other;
		return Math.abs(this.x - otherPoint.x) < Globals.POINT_EPSILON
				&& Math.abs(this.y - otherPoint.y) < Globals.POINT_EPSILON;
	}

	public double getX()
	{
		return x;
	}

	public double getY()
	{
		return y;
	}

	@Override
	public int hashCode()
	{
		Double result = x;
		result = 31.0 * result + y;
		return result.hashCode();
	}

	public void setX(double d)
	{
		x = d;
	}

	public void setY(double d)
	{
		y = d;
	}

	public String toString()
	{
		return "Point: (" + x + ", " + y + ")";
	}

	@Override
	public void draw(Graphics g)
	{
		int radius = 3;
        
        g.setColor(getInteriorColor());
        g.fillOval((int) x - radius, (int) y - radius, 2 * radius, 2 * radius);
        g.setColor(getBoundaryColor());
        g.drawOval((int) x - radius, (int) y - radius, 2 * radius, 2 * radius);
	}
}