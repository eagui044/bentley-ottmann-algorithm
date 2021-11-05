import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import java.awt.*;

public class Tester
{
	public static void main(String[] args)
	{
		new Tester();
	}

	public Tester()
	{
		// frame size
		int frameWidth = 900;
		int frameHeight = 900;
		
		BinarySearchTree bst = new BinarySearchTree();
		Random rand = new Random();
		int segmentCount = 5;
		int coordinateRange = 900;

		ArrayList<GeometricObject> geometryList = new ArrayList<>(segmentCount);
		Event[] events = new Event[segmentCount*2];

		// Generate random line segments to insert into tree.
		for (int i = 0; i < segmentCount*2; i += 2)
		{
			double x1 = coordinateRange * rand.nextDouble();
			double y1 = coordinateRange * rand.nextDouble();
			double x2 = coordinateRange * rand.nextDouble();
			double y2 = coordinateRange * rand.nextDouble();
			Point p1 = new Point(x1, y1);
			Point p2 = new Point(x2, y2);
			p1.setInteriorColor(Color.black);
			p2.setInteriorColor(Color.black);
			LineSegment segment = new LineSegment(p1, p2);

			events[i] = new Event(segment.getLeftEndpoint(), segment, Event.Type.LEFT);
			events[i+1] = new Event(segment.getRightEndpoint(), segment, Event.Type.RIGHT);
		}
		
		System.out.println("Event Priority Queue:\n");
		
		EventQueue eq = new EventQueue(events);
		while(!eq.isEmpty())
		{
			System.out.println(eq.removeMin());
		}
		eq = new EventQueue(events);
		
		//System.out.println(bst);
		
		//graphing
		FrameDisplay frame = new FrameDisplay(frameWidth, frameHeight, geometryList);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
//		while(!eq.isEmpty())
//		{
//			Event e = eq.removeMin();
//			
//			if (e.getEventType() == Event.Type.LEFT)
//			{
//				geometryList.add(e.getSegment());
//			} else
//			{
//				geometryList.remove(e.getSegment());
//			}
//			
//			frame.repaint();
//			delay(500);
//		}
		
		while(!eq.isEmpty())
		{
			Event e = eq.removeMin();
			
			if (e.getEventType() == Event.Type.LEFT)
			{
				bst.add(e.getSegment(), e.getEventPoint());
			} else
			{
				bst.remove(e.getSegment(), e.getEventPoint());
			}
			
			System.out.println();
			System.out.println(bst);
			
			if (!bst.isEmpty())
			{
				Node current = bst.getMinNode();
				geometryList = new ArrayList<>(segmentCount);
				frame.setGeometryList(geometryList);
				while (current != null)
				{
					geometryList.add(current.getInfo());
					current = current.getSuccessor();
				}
			} else
			{
				geometryList = new ArrayList<>(segmentCount);
				frame.setGeometryList(geometryList);
			}
			frame.repaint();
			delay(500);
		}
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
