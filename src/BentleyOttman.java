import java.util.ArrayList;
import java.util.List;

import javax.swing.text.AttributeSet.ColorAttribute;

import java.awt.Color;

public class BentleyOttman
{
	private Event[] events;
	private EventQueue eq;
	private BinarySearchTree sweepLine;

	public BentleyOttman(List<LineSegment> segments)
	{
		int segmentCount = segments.size();
		events = new Event[segmentCount * 2];

		int j = 0;
		for (int i = 0; i < segmentCount; i++)
		{
			events[j] = new Event(segments.get(i).getLeftEndpoint(), segments.get(i), Event.Type.LEFT);
			events[j + 1] = new Event(segments.get(i).getRightEndpoint(), segments.get(i), Event.Type.RIGHT);
			j += 2;
		}

		eq = new EventQueue(events);
		sweepLine = new BinarySearchTree();
	}

	public void setSegments(List<LineSegment> segments)
	{
		int segmentCount = segments.size();
		events = new Event[segmentCount * 2];

		int j = 0;
		for (int i = 0; i < segmentCount; i++)
		{
			events[j] = new Event(segments.get(i).getLeftEndpoint(), segments.get(i), Event.Type.LEFT);
			events[j + 1] = new Event(segments.get(i).getRightEndpoint(), segments.get(i), Event.Type.RIGHT);
			j += 2;
		}

		eq = new EventQueue(events);
	}

	public List<Point> findIntersections()
	{
		ArrayList<Point> intersections = new ArrayList<>();
		
		// TODO delete when done testing.
		while (!eq.isEmpty())
		{
			System.out.println(eq.removeMin());
		}
		System.out.println();
		for (Event event : events)
		{
			eq.add(event);
		}
		while (!eq.isEmpty())
		{
			System.out.println(eq.removeMin());
		}
		System.out.println();
		eq = new EventQueue(events);
		while (!eq.isEmpty())
		{
			System.out.println(eq.min());
			eq.deleteEventPoint(eq.min().getEventPoint());
		}
		System.out.println();
		eq = new EventQueue(events);
		
		while (!eq.isEmpty())
		{
			Event event = eq.removeMin();

			if (event.getEventType() == Event.Type.LEFT)
			{
				// TODO delete when done testing.
				System.out.println("Insert: " + event);
				Node current = sweepLine.add(event.getSegment(), event.getEventPoint());
				Node above = current.getSuccessor();
				Node below = current.getPredecessor();

				if (above != null)
				{
					Point crossingPoint = current.getSegment().getIntersectionPointWith(above.getSegment());

					if (crossingPoint != null)
					{
						eq.add(new Event(crossingPoint, current.getSegment(), above.getSegment(),
								Event.Type.INTERSECTION));
					}
				}

				if (below != null)
				{
					Point crossingPoint = current.getSegment().getIntersectionPointWith(below.getSegment());

					if (crossingPoint != null)
					{
						eq.add(new Event(crossingPoint, current.getSegment(), below.getSegment(),
								Event.Type.INTERSECTION));
					}
				}
				
				if (above != null && below != null)
				{
					Point crossingPoint = above.getSegment().getIntersectionPointWith(below.getSegment());

					if (crossingPoint != null && crossingPoint.getX() > event.getEventPoint().getX())
					{
						eq.deleteEventPoint(crossingPoint);
					}
				}
				
			} else if (event.getEventType() == Event.Type.RIGHT)
			{
				// TODO delete when done testing.
				System.out.println("Delete: " + event);
				
				//TODO delete when done testing.
				event.getSegment().setBoundaryColor(Color.red);
				Tester.frame.repaint();
				System.out.println(sweepLine);

				Node removed = sweepLine.findNode(event.getSegment(), event.getEventPoint());
				Node above = removed.getSuccessor();
				Node below = removed.getPredecessor();

				sweepLine.remove(event.getSegment(), event.getEventPoint());

				if (above != null && below != null)
				{
					Point crossingPoint = above.getSegment().getIntersectionPointWith(below.getSegment());

					if (crossingPoint != null && crossingPoint.getX() > event.getEventPoint().getX())
					{
						eq.add(new Event(crossingPoint, above.getSegment(), below.getSegment(),
								Event.Type.INTERSECTION));
					}
				}
				
				//TODO delete when done testing.
				event.getSegment().setBoundaryColor(new Color(0.0f, 0.0f, 0.0f, 0.25f));
				Tester.frame.repaint();
			} else
			{
				// TODO delete when done testing.
				System.out.println("Intersection: " + event);

				// Report the intersecting pair.
				event.getEventPoint().setInteriorColor(Color.red);
				intersections.add(event.getEventPoint());
				
				//TODO delete when done testing.
				Tester.geometryList.add(event.getEventPoint());
				Tester.frame.repaint();
				
				LineSegment aboveSegment, belowSegment;
				
				if (event.getSegment().compareTo(event.getIntersectionSegment(), event.getEventPoint()) == 1)
				{
					aboveSegment = event.getSegment();
					belowSegment = event.getIntersectionSegment();
				} else
				{
					aboveSegment = event.getIntersectionSegment();
					belowSegment = event.getSegment();
				}

				//TODO delete when done testing.
				aboveSegment.setBoundaryColor(Color.red);
				belowSegment.setBoundaryColor(Color.blue);
				Tester.frame.repaint();
				System.out.println(sweepLine);
				
				Node above = sweepLine.findNode(aboveSegment, event.getEventPoint());
				Node below = sweepLine.findNode(belowSegment, event.getEventPoint());
				sweepLine.swapNodeInfo(above, below);

				Node top = above.getSuccessor();
				Node bottom = below.getPredecessor();

				if (top != null)
				{
					Point crossingPoint = above.getSegment().getIntersectionPointWith(top.getSegment());

					if (crossingPoint != null && crossingPoint.getX() > event.getEventPoint().getX())
					{
						eq.add(new Event(crossingPoint, above.getSegment(), top.getSegment(), Event.Type.INTERSECTION));
					}
					
					crossingPoint = below.getSegment().getIntersectionPointWith(top.getSegment());

					if (crossingPoint != null && crossingPoint.getX() > event.getEventPoint().getX())
					{
						eq.deleteEventPoint(crossingPoint);
					}
				}

				if (bottom != null)
				{
					Point crossingPoint = below.getSegment().getIntersectionPointWith(bottom.getSegment());

					if (crossingPoint != null && crossingPoint.getX() > event.getEventPoint().getX())
					{
						eq.add(new Event(crossingPoint, below.getSegment(), bottom.getSegment(),
								Event.Type.INTERSECTION));
					}
					
					crossingPoint = above.getSegment().getIntersectionPointWith(bottom.getSegment());

					if (crossingPoint != null && crossingPoint.getX() > event.getEventPoint().getX())
					{
						eq.deleteEventPoint(crossingPoint);
					}
				}
				
				//TODO delete when done testing.
				aboveSegment.setBoundaryColor(Color.black);
				belowSegment.setBoundaryColor(Color.black);
				Tester.frame.repaint();
			}
		}
		
		return intersections;
	}
}
