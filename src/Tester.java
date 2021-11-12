import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import java.awt.Color;

/**
 * @author E. Aguilar
 */
public class Tester
{
	//TODO when done testing remove public static variables.
	public static FrameDisplay frame;
	
	public static void main(String[] args)
	{
		new Tester();
	}

	public Tester()
	{
		// frame size
		int frameWidth = 900;
		int frameHeight = 900;
		
		long seed = System.currentTimeMillis();
		System.out.println("Tester Seed: " + seed);
		Random rand = new Random(seed);
		int segmentCount = 100;
		int coordinateRange = 900;

		ArrayList<GeometricObject> geometricList = new ArrayList<>(segmentCount);

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
			
			geometricList.add(segment);
		}
		
		// Graphing
		frame = new FrameDisplay(frameWidth, frameHeight, geometricList);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		ArrayList<LineSegment> segments = new ArrayList<>();
		for (GeometricObject geometry : geometricList)
		{
			segments.add((LineSegment)geometry);
		}
		
		BentleyOttmann bo = new BentleyOttmann(segments);
		bo.findIntersections(5); // Set non-zero delay in ms to visualize sweep line algorithm progression.
		frame.repaint();
	}
	
	/**
	 * Method that makes the thread that it's called from to pause.
	 * 
	 * @param ms milliseconds duration the thread will pause for
	 */
	public static void delay(int ms)
	{
		try
		{
			Thread.sleep(ms);
		} catch (InterruptedException e) {}
	}
}
