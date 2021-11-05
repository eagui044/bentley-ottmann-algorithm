import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GraphDisplay extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1802022021377994284L;

	ArrayList<GeometricObject> geometryList; // geometric objects

	/**
	 * Parameterized constructor.
	 */
	public GraphDisplay(int width, int height, ArrayList<GeometricObject> geometryList)
	{
		this.geometryList = geometryList;

		setBackground(Color.white);
		Dimension d = new Dimension(width, height);
		setPreferredSize(d);
	}

	/**
	 * Paints this panel; the system invokes it every time it deems necessary to redraw the panel.
	 */
	@Override
	public void paint(Graphics g)
	{
		super.paint(g); // clears window

		// draws geometric objects
		for (GeometricObject e : geometryList)
		{
			e.draw(g); // invokes object's draw method through polymorphism
		}

	}

	public void setGeometryList(ArrayList<GeometricObject> geometryList)
	{
		this.geometryList = geometryList;
	}
}
