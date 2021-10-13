/**
 * @author E. Aguilar, adaptation from Data Structures class (COP 3530)
 * @author A. Hernandez
 *         <p>
 *         This is a Binary Search Tree which is specialized for ordering geometric {@link LineSegment} objects, the
 *         line segments are ordered based on whether they are above or below each other using the y-coordinates of
 *         their endpoints. Successor and antecessor {@link Node} objects which contain the line segments can be
 *         retrieved in constant time.
 *         </p>
 */
public class BinarySearchTree
{
	private Node root; // Root of the bst, implemented as a dummy node.

	public BinarySearchTree()
	{
		root = new Node(); // Dummy node as the root
		root.setLeftChild(null);
		root.setRightChild(null);
		root.setInfo(null);
	}

	public void add(LineSegment s)
	{
		if (root.getLeftChild() == null)
		{
			Node p = new Node();
			p.setNode(s, null, null);
			root.setLeftChild(p);
		} else
		{
			insert(s, root.getLeftChild());
		}
	}

	public boolean contains(LineSegment s)
	{
		return search(s, root.getLeftChild());
	}

	public void display()
	{
		preorderDisplay(root.getLeftChild());
		System.out.println();
		inorderDisplay(root.getLeftChild());
		System.out.println();
		postorderDisplay(root.getLeftChild());
		System.out.println();
	}

	public int getCount()
	{
		return getCount(root.getLeftChild());
	}

	private int getCount(Node p)
	{
		if (p != null)
		{
			return 1 + getCount(p.getLeftChild()) + getCount(p.getRightChild());
		} else
		{
			return 0;
		}
	}

	public int getCountOf(LineSegment s)
	{
		return getCountOf(s, root.getLeftChild());
	}

	private int getCountOf(LineSegment s, Node p)
	{
		if (p != null)
		{
			if (s.compareTo(p.getInfo()) == 0)
			{
				return 1 + getCountOf(s, p.getRightChild());
			} else if (s.compareTo(p.getInfo()) == -1)
			{
				return getCountOf(s, p.getLeftChild());
			} else
			{
				return getCountOf(s, p.getRightChild());
			}
		} else
		{
			return 0;
		}
	}

	public int getHeight()
	{
		return getHeight(root.getLeftChild());
	}

	private int getHeight(Node p)
	{
		if (p != null)
		{
			return 1 + Math.max(getHeight(p.getLeftChild()), getHeight(p.getRightChild()));
		} else
		{
			return -1;
		}
	}

	public LineSegment getMax()
	{
		return getMax(root.getLeftChild());
	}

	public LineSegment getMax(Node p)
	{
		if (p.getRightChild() == null)
		{
			return p.getInfo();
		} else
		{
			return getMax(p.getRightChild());
		}
	}

	public LineSegment getMin()
	{
		return getMin(root.getLeftChild());
	}

	private LineSegment getMin(Node p)
	{
		if (p.getLeftChild() == null)
		{
			return p.getInfo();
		} else
		{
			return getMin(p.getLeftChild());
		}
	}

	private void inorderDisplay(Node p)
	{
		if (p != null)
		{
			inorderDisplay(p.getLeftChild());
			if (!p.isDeleted())
			{
				System.out.print(p.getInfo() + " ");
			}
			inorderDisplay(p.getRightChild());
		}
	}

	private void insert(LineSegment s, Node p)
	{
		if (s.compareTo(p.getInfo()) == -1) // If < current node, insert left.
		{
			if (p.getLeftChild() == null)
			{
				Node q = new Node();
				q.setNode(s, null, null);
				p.setLeftChild(q);
			} else
			{
				insert(s, p.getLeftChild());
			}
		} else // if >= current node, insert right.
		{
			if (p.getRightChild() == null)
			{
				Node q = new Node();
				q.setNode(s, null, null);
				p.setRightChild(q);
			} else
			{
				insert(s, p.getRightChild());
			}
		}
	}

	public boolean isEmpty()
	{
		return root.getLeftChild() == null;
	}

	private void postorderDisplay(Node p)
	{
		if (p != null)
		{
			postorderDisplay(p.getLeftChild());
			postorderDisplay(p.getRightChild());
			if (!p.isDeleted())
			{
				System.out.print(p.getInfo() + " ");
			}
		}
	}

	private void preorderDisplay(Node p)
	{
		if (p != null)
		{
			if (!p.isDeleted())
			{
				System.out.print(p.getInfo() + " ");
			}
			preorderDisplay(p.getLeftChild());
			preorderDisplay(p.getRightChild());
		}
	}

	public void remove(LineSegment s)
	{
		remove(s, root.getLeftChild());
	}

	private void remove(LineSegment s, Node p)
	{
		if (p != null)
		{
			if (s.compareTo(p.getInfo()) == 0)
			{
				p.setDeleted(true);
				remove(s, p.getRightChild());
			} else if (s.compareTo(p.getInfo()) == -1)
			{
				remove(s, p.getLeftChild());
			} else
			{
				remove(s, p.getRightChild());
			}
		}
	}

	private boolean search(LineSegment s, Node p)
	{
		if (p == null)
		{
			return false;
		} else if (s.compareTo(p.getInfo()) == 0)
		{
			return true;
		} else if (s.compareTo(p.getInfo()) == -1)
		{
			return search(s, p.getLeftChild());
		} else
		{
			return search(s, p.getRightChild());
		}
	}

	public String toString()
	{
		return toString(root.getLeftChild());
	}

	private String toString(Node p)
	{
		if (p != null)
		{
			return toString(p.getLeftChild()) + " " + p.getInfo() + " " + toString(p.getRightChild());
		} else
		{
			return "";
		}
	}
}
