/**
 * @author E. Aguilar, adaptation from Data Structures class (COP 3530)
 * @author A. Hernandez
 *         <p>
 *         This is a special Node object for a {@link BinarySearchTree} which is specialized for ordering
 *         {@link LineSegment} objects.
 *         </p>
 */
public class Node
{
	private LineSegment lineSegment; // Line segment held by this node
	private Node left; // Link to left child
	private Node right; // Link to right child
	private boolean isDeleted; // Deleted node flag

	public Node()
	{
		lineSegment = null;
		left = right = null;
		isDeleted = false;
	}

	public LineSegment getInfo()
	{
		return lineSegment;
	}

	public Node getLeftChild()
	{
		return left;
	}

	public Node getRightChild()
	{
		return right;
	}

	public boolean isDeleted()
	{
		return isDeleted;
	}

	public void setDeleted(boolean delete)
	{
		isDeleted = delete;
	}

	public void setInfo(LineSegment s)
	{
		lineSegment = s;
	}

	public void setLeftChild(Node l)
	{
		left = l;
	}

	public void setNode(LineSegment s, Node l, Node r)
	{
		lineSegment = s;
		left = l;
		right = r;
	}

	public void setRightChild(Node r)
	{
		right = r;
	}
}
