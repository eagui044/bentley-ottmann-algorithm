import java.util.Random;

public class Tester
{
	public static void main(String[] args)
	{
		new Tester();
	}

	public Tester()
	{
		BinarySearchTree bst = new BinarySearchTree();
		Random rand = new Random(1);
		int segmentCount = 25;
		int coordinateRange = 25;

		LineSegment[] segments = new LineSegment[segmentCount];

		// Generate random line segments to insert into tree.
		for (int i = 0; i < segmentCount; i++)
		{
			double x1 = coordinateRange * rand.nextDouble();
			double y1 = coordinateRange * rand.nextDouble();
			double x2 = coordinateRange * rand.nextDouble();
			double y2 = coordinateRange * rand.nextDouble();
			Point p1 = new Point(x1, y1);
			Point p2 = new Point(x2, y2);
			LineSegment segment = new LineSegment(p1, p2);

			bst.add(segment);
			segments[i] = segment;
		}

		System.out.println("Insertion and removal test:");
		System.out.println();

		System.out.println(bst);

		for (int i = 0; i < segmentCount; i++)
		{
			System.out.println("Removing: " + segments[i]);
			bst.remove(segments[i]);
			System.out.println(bst);
		}

		// Generate random line segments to insert into tree.
		for (int i = 0; i < segmentCount; i++)
		{
			double x1 = coordinateRange * rand.nextDouble();
			double y1 = coordinateRange * rand.nextDouble();
			double x2 = coordinateRange * rand.nextDouble();
			double y2 = coordinateRange * rand.nextDouble();
			Point p1 = new Point(x1, y1);
			Point p2 = new Point(x2, y2);
			LineSegment segment = new LineSegment(p1, p2);

			bst.add(segment);
			segments[i] = segment;
			// System.out.println(segment);
		}

		System.out.println("\n---------------------------------------------------------------------------------------");
		System.out.println("Constant time access predecessor and successor test, "
				+ "inorder traversal using only successor and predecessor links:");
		System.out.println();

		System.out.println("Inorder Display:");
		bst.inorderPredecessorSuccessorDisplay(false);
		System.out.println();

		System.out.println("Reversed Inorder Display:");
		bst.inorderPredecessorSuccessorDisplay(true);
		System.out.println();

		for (int i = 0; i < segmentCount; i++)
		{
			System.out.println("Removing: " + segments[i]);
			bst.remove(segments[i]);
			bst.inorderPredecessorSuccessorDisplay(false);
			System.out.println();
		}
	}
}
