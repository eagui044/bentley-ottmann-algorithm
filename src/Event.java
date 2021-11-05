
public class Event
{
	public enum Type
	{
		LEFT, RIGHT
	}
	private Point eventPoint;
	private LineSegment segment;

	private Type eventType;

	public Event(Point eventPoint, LineSegment segment, Type eventType)
	{
		this.eventPoint = eventPoint;
		this.segment = segment;
		this.setEventType(eventType);
	}

	public Point getEventPoint()
	{
		return eventPoint;
	}

	public Type getEventType()
	{
		return eventType;
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

	public void setSegment(LineSegment segment)
	{
		this.segment = segment;
	}

	@Override
	public String toString()
	{
		return String.format("Event: eventPoint: %s, segment: %s, type: %s", eventPoint, segment, eventType);
	}
}
