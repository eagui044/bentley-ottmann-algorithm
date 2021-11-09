
/**
 * @author E. Aguilar
 */
public class Event
{
	public enum Type
	{
		LEFT, RIGHT, INTERSECTION
	}

	private Point eventPoint;
	private LineSegment segment;
	private LineSegment intersectionSegment; // Typically null unless Event is of type intersection.

	private Type eventType;

	public Event(Point eventPoint, LineSegment segment, LineSegment intersectionSegment, Type eventType)
	{
		this.eventPoint = eventPoint;
		this.segment = segment;
		this.intersectionSegment = intersectionSegment;
		this.eventType = eventType;
	}

	public Event(Point eventPoint, LineSegment segment, Type eventType)
	{
		this.eventPoint = eventPoint;
		this.segment = segment;
		this.eventType = eventType;

		this.intersectionSegment = null;
	}

	public Point getEventPoint()
	{
		return eventPoint;
	}

	public Type getEventType()
	{
		return eventType;
	}

	public LineSegment getIntersectionSegment()
	{
		return intersectionSegment;
	}

	public LineSegment getSegment()
	{
		return segment;
	}

	public void setEventPoint(Point eventPoint)
	{
		this.eventPoint = eventPoint;
	}

	public void setEventType(Type eventType)
	{
		this.eventType = eventType;
	}

	public void setIntersectionSegment(LineSegment intersectionSegment)
	{
		this.intersectionSegment = intersectionSegment;
	}

	public void setSegment(LineSegment segment)
	{
		this.segment = segment;
	}

	@Override
	public String toString()
	{
		return String.format("Event: eventPoint: %s, segment: %s, intersectionSegment: %s, eventType: %s", eventPoint,
				segment, intersectionSegment, eventType);
	}
}
