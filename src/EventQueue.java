
/**
 * @author E. Aguilar
 * @author A. Hernandez
 */
public class EventQueue
{
	/**
	 * Heapifies a subtree rooted with node index i, producing a min heap through Floyd's method which utilizes the
	 * sift-down technique. The building of the heap is done in an optimal manner, taking O(n) time overall for all
	 * event points. Note that the Event array passed to this method will be modified, according to each Event's event
	 * point, sorting the points from left to right on the x-axis.
	 *
	 * @param events array of Event objects containing event points, the array will be heapified.
	 * @param size   Given size of the heap.
	 * @param i      The root index of the subtree.
	 */
	private static void heapify(Event[] events, int size, int i)
	{
		// By minimum we refer to the event points that have a smaller x, and thus should be higher on the binary heap.
		int minimum = i; // Initialize minimum as root of subtree.
		int left = 2 * i; // left child = 2*i
		int right = 2 * i + 1; // right child = 2*i + 1

		double minX = events[minimum].getEventPoint().getX();

		if (left <= size)
		{
			double leftX = events[left].getEventPoint().getX();

			// If the two event points have the same x, then the minimum will be the point with the minimum y.
			if (Math.abs(leftX - minX) < Globals.POINT_EPSILON)
			{
				double minY = events[minimum].getEventPoint().getY();
				double leftY = events[left].getEventPoint().getY();

				if (leftY < minY)
				{
					minimum = left;
				}
			}
			// Else if leftX is less than minX then the left event point is smaller.
			else if (leftX < minX)
			{
				minimum = left;
				minX = leftX;
			}
		}

		if (right <= size)
		{
			double rightX = events[right].getEventPoint().getX();

			// If the two event points have the same x, then the minimum will be the point with the minimum y.
			if (Math.abs(rightX - minX) < Globals.POINT_EPSILON)
			{
				double minY = events[minimum].getEventPoint().getY();
				double rightY = events[right].getEventPoint().getY();

				if (rightY < minY)
				{
					minimum = right;
				}
			}
			// If rightX is less than minX then the right event point is smaller.
			else if (rightX < minX)
			{
				minimum = right;
				minX = rightX;
			}
		}

		// If the minimum event point is not the root of the subtree.
		if (minimum != i)
		{
			// Swap parent and left or right child.
			swap(events, i, minimum);

			// Recursively heapify the affected sub-tree.
			heapify(events, size, minimum);
		}
	}

	/**
	 * Swaps two elements of an array.
	 * 
	 * @param events the array
	 * @param x      the index of the first element to be swapped.
	 * @param y      the index of the second element to be swapped.
	 */
	private static void swap(Event[] events, int x, int y)
	{
		Event swap = events[x];
		events[x] = events[y];
		events[y] = swap;
	}

	private Event[] events;

	private int arraySize; // size of the array
	private int length; // current number of elements in the array

	public EventQueue(Event[] events)
	{
		this.events = new Event[events.length + 1];
		System.arraycopy(events, 0, this.events, 1, events.length);
		length = events.length;
		arraySize = events.length + 1;

		// Build heap (rearranges array)
		// i starts at last non-leaf node, heapfiying by sift-down technique.
		for (int i = length / 2; i > 0; i--)
		{
			heapify(this.events, length, i);
		}
	}

	public EventQueue(int arraySize)
	{
		length = 0;
		this.arraySize = arraySize;

		events = new Event[this.arraySize];
	}

	public void add(Event e)
	{
		int loc = ++length;

		events[0] = e;
		double eventX = e.getEventPoint().getX();
		while (eventX < events[loc / 2].getEventPoint().getX())
		{
			events[loc] = events[loc / 2];
			loc /= 2;
		}

		// If the two event points have the same x, then bubble up the point with the minimum y.
		double eventY = e.getEventPoint().getY();
		while (Math.abs(eventX - events[loc / 2].getEventPoint().getX()) < Globals.POINT_EPSILON
				&& eventY < events[loc / 2].getEventPoint().getY())
		{
			events[loc] = events[loc / 2];
			loc /= 2;
		}

		events[loc] = e;
	}

	public void deleteEventPoint(Point eventPoint)
	{
		events[0] = null;
		for (int i = 1; i <= length; i++)
		{
			if (events[i].getEventPoint().equals(eventPoint))
			{
				for (int j = i; j <= length - 1; j++)
				{
					events[j] = events[j + 1];
				}
				for (int j = length; j < arraySize; j++)
				{
					events[j] = null;
				}

				length--;

				// Rebuild heap (rearranges array)
				// i starts at last non-leaf node, heapfiying by sift-down technique.
				for (int j = length / 2; j > 0; j--)
				{
					heapify(events, length, j);
				}

				break;
			}
		}
	}

	public boolean isEmpty()
	{
		return (length == 0) ? true : false;
	}

	public Event min()
	{
		return events[1];
	}

	public Event removeMin()
	{
		Event min = events[1];
		Event lastEvent = events[length];
		length--;

		int parent = 1;
		while (parent * 2 <= length)
		{
			int child = parent * 2;

			double rightChildX = events[child + 1].getEventPoint().getX();
			double leftChildX = events[child].getEventPoint().getX();
			double rightChildY = events[child + 1].getEventPoint().getY();
			double leftChildY = events[child].getEventPoint().getY();

			if (child != length && (rightChildX < leftChildX
					|| (Math.abs(rightChildX - leftChildX) < Globals.POINT_EPSILON && rightChildY < leftChildY)))
			{
				child++;
			}

			double childX = events[child].getEventPoint().getX();
			double childY = events[child].getEventPoint().getY();

			if (childX < lastEvent.getEventPoint().getX()
					|| (Math.abs(childX - lastEvent.getEventPoint().getX()) < Globals.POINT_EPSILON
							&& childY < lastEvent.getEventPoint().getY()))
			{
				events[parent] = events[child];
			} else
			{
				break;
			}

			parent = child;
		}

		events[parent] = lastEvent;

		return min;
	}
}
