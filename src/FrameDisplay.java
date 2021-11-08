import java.util.List;
import javax.swing.*;

public class FrameDisplay extends JFrame
{   
    /**
	 * 
	 */
	private static final long serialVersionUID = -1135730730153245479L;
	
	private GraphDisplay panel;

    /**
     * Creates an initializes the window frame.
     * 
     * @param width frame width
     * @param height frame height
     * @param geometryList geometric objects to be depicted in this frame
     */
    public FrameDisplay(int width, int height, List<GeometricObject> geometryList)
    {
        setTitle("Set of Line Segments");
        setSize(width, height);
        //setResizable(false);

        panel = new GraphDisplay(width, height, geometryList);
        add(panel);
        pack(); //sizes the frame so that all its contents (panel in this case) 
                //are at or above their preferred sizes
    }
    
    public void setGeometryList(List<GeometricObject> geometryList)
    {
    	panel.setGeometryList(geometryList);
    }
    
    public void repaint()
    {
    	panel.repaint();
    }
}
