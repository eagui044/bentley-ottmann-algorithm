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
		Random rand = new Random(0);
		int segmentCount = 20;
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
		System.out.println();

		for (int i = 0; i < segmentCount; i++)
		{
			bst.remove(segments[i]);
			System.out.println(bst);
			System.out.println();
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
		}

		System.out.println("Constant time access predecessor and successor test:");
		System.out.println();

		System.out.println("Inorder traversal using only successor and predecessor links:");
		bst.inorderPredecessorSuccessorTraversal();
		System.out.println();

		for (int i = 0; i < segmentCount; i++)
		{
			bst.remove(segments[i]);
			bst.inorderPredecessorSuccessorTraversal();
			System.out.println();
		}
	}
}
