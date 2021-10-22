/**
 * @author E. Aguilar
 * @author A. Hernandez
 *         <p>
 *         This is a special Node object for a {@link BinarySearchTree} which is specialized for ordering
 *         {@link LineSegment} objects.
 *         </p>
 */
public class Node
{
	private LineSegment segment; // Line segment held by this node
	private Node parent; // Link to parent node above
	private Node left; // Link to left child
	private Node right; // Link to right child
	private Node predecessor; // Link to inorder predecessor of this node.
	private Node successor; // Link to inorder successor of this node.

	public Node()
	{
		segment = null;
		parent = left = right = null;
		predecessor = successor = null;
	}

	public LineSegment getInfo()
	{
		return segment;
	}

	public Node getLeftChild()
	{
		return left;
	}

	public Node getParent()
	{
		return parent;
	}

	public Node getPredecessor()
	{
		return predecessor;
	}

	public Node getRightChild()
	{
		return right;
	}

	public Node getSuccessor()
	{
		return successor;
	}

	public void setInfo(LineSegment segment)
	{
		this.segment = segment;
	}

	public void setLeftChild(Node left)
	{
		this.left = left;
	}

	public void setNode(LineSegment segment, Node parent, Node left, Node right, Node predecessor, Node successor)
	{
		this.segment = segment;
		this.parent = parent;
		this.left = left;
		this.right = right;
		this.predecessor = predecessor;
		this.successor = successor;
	}

	public void setParent(Node parent)
	{
		this.parent = parent;
	}

	public void setPredecessor(Node predecessor)
	{
		this.predecessor = predecessor;
	}

	public void setRightChild(Node right)
	{
		this.right = right;
	}

	public void setSuccessor(Node successor)
	{
		this.successor = successor;
	}
}
