import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GraphDisplay extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1802022021377994284L;

	private List<GeometricObject> geometryList; // geometric objects

	/**
	 * Parameterized constructor.
	 */
	public GraphDisplay(int width, int height, List<GeometricObject> geometryList)
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
		for (int i = 0; i < geometryList.size(); i++)
		{
			geometryList.get(i).draw(g); // invokes object's draw method through polymorphism
		}

	}

	public void setGeometryList(List<GeometricObject> geometryList)
	{
		this.geometryList = geometryList;
	}
}
