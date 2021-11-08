import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import java.awt.Color;

public class Tester
{
	//TODO when done testing remove public static variables.
	public static FrameDisplay frame;
	public static ArrayList<GeometricObject> geometryList;
	
	public static void main(String[] args)
	{
		new Tester();
	}

	public Tester()
	{
		// frame size
		int frameWidth = 900;
		int frameHeight = 900;
		
		Random rand = new Random();
		int segmentCount = 100;
		int coordinateRange = 900;

		geometryList = new ArrayList<>(segmentCount);

		// Generate random line segments to insert into tree.
		for (int i = 0; i < segmentCount*2; i += 2)
		{
			double x1 = coordinateRange * rand.nextDouble();
			double y1 = coordinateRange * rand.nextDouble();
			double x2 = coordinateRange * rand.nextDouble();
			double y2 = coordinateRange * rand.nextDouble();
//			double x1 = rand.nextInt(coordinateRange);
//			double y1 = rand.nextInt(coordinateRange);
//			double x2 = rand.nextInt(coordinateRange);
//			double y2 = rand.nextInt(coordinateRange);
			Point p1 = new Point(x1, y1);
			Point p2 = new Point(x2, y2);
			p1.setInteriorColor(Color.black);
			p2.setInteriorColor(Color.black);
			LineSegment segment = new LineSegment(p1, p2);
			
			geometryList.add(segment);
		}
		
		// Graphing
		frame = new FrameDisplay(frameWidth, frameHeight, geometryList);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		ArrayList<LineSegment> segments = new ArrayList<>();
		for (GeometricObject geometry : geometryList)
		{
			segments.add((LineSegment)geometry);
		}
		
		BentleyOttman bo = new BentleyOttman(segments);
		bo.findIntersections();
//		geometryList.addAll(bo.findIntersections());
//		frame.repaint();
	}
	
	/**
	 * Method that makes the thread that it's called from to pause.
	 * 
	 * @param ms milliseconds duration the thread will pause for
	 */
	public void delay(int ms)
	{
		try
		{
			Thread.sleep(ms);
		} catch (InterruptedException e) {}
	}
}
