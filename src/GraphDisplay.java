import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GraphDisplay extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1802022021377994284L;

	private ArrayList<GeometricObject> geometricList; // geometric objects

	/**
	 * Parameterized constructor.
	 */
	public GraphDisplay(int width, int height, List<GeometricObject> geometricList)
	{
		this.geometricList = new ArrayList<>(geometricList);

		setBackground(Color.white);
		Dimension d = new Dimension(width, height);
		setPreferredSize(d);
	}

	public void addGeometricObject(GeometricObject object)
	{
		geometricList.add(object);
		this.repaint();
	}

	public void addGeometricObjects(List<GeometricObject> objects)
	{
		geometricList.addAll(objects);
		this.repaint();
	}
	
	/**
	 * Paints this panel; the system invokes it every time it deems necessary to redraw the panel.
	 */
	@Override
	public void paint(Graphics g)
	{
		super.paint(g); // clears window

		// draws geometric objects
		for (int i = 0; i < geometricList.size(); i++)
		{
			geometricList.get(i).draw(g); // invokes object's draw method through polymorphism
		}

	}

	public void setGeometricList(List<GeometricObject> geometricList)
	{
		this.geometricList = new ArrayList<>(geometricList);
	}
}
