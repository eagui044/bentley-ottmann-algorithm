/**
 * @author E. Aguilar, adaptation from Data Structures class (COP 3530)
 * @author A. Hernandez
 *         <p>
 *         This is a Binary Search Tree which is specialized for ordering geometric {@link LineSegment} objects, the
 *         line segments are ordered based on whether they are above or below each other using the y-coordinates of
 *         their endpoints. Successor and predecessor {@link Node} objects which contain the line segments can be
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
			Node first = new Node();
			first.setNode(s, null, null, null);
			root.setLeftChild(first);
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
				Node newChild = new Node();
				newChild.setNode(s, p, null, null);
				p.setLeftChild(newChild);
				newChild.setPredecessor(findInorderPredecessorOf(newChild));
				newChild.setSuccessor(findInorderSuccessorOf(newChild));

				if (newChild.getPredecessor() != null)
				{
					newChild.getPredecessor().setSuccessor(newChild);
				}

				if (newChild.getSuccessor() != null)
				{
					newChild.getSuccessor().setPredecessor(newChild);
				}
			} else
			{
				insert(s, p.getLeftChild());
			}
		} else // if >= current node, insert right.
		{
			if (p.getRightChild() == null)
			{
				Node newChild = new Node();
				newChild.setNode(s, p, null, null);
				p.setRightChild(newChild);
				newChild.setPredecessor(findInorderPredecessorOf(newChild));
				newChild.setSuccessor(findInorderSuccessorOf(newChild));

				if (newChild.getPredecessor() != null)
				{
					newChild.getPredecessor().setSuccessor(newChild);
				}

				if (newChild.getSuccessor() != null)
				{
					newChild.getSuccessor().setPredecessor(newChild);
				}
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

				if (p.getPredecessor() != null)
				{
					p.getPredecessor().setSuccessor(p.getSuccessor());
				}

				if (p.getSuccessor() != null)
				{
					p.getSuccessor().setPredecessor(p.getPredecessor());
				}

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

	private Node findInorderSuccessorOf(Node p)
	{
		if (p.getRightChild() != null)
		{
			// If right subtree exists, then successor is its minimum node.
			return findMinNodeFrom(p.getRightChild());
		} else
		{
			// If right subtree does not exist, then successor is found in ancestor node, unless this node is the max.
			// Climb up ancestors using parent link, until you find a node whose parent's right child is not equal to
			// it, that parent is the successor.
			Node child = p;
			Node parent = child.getParent();

			while (parent != null && child == parent.getRightChild())
			{
				child = parent;
				parent = parent.getParent();
			}

			return parent;
		}
	}

	private Node findInorderPredecessorOf(Node p)
	{
		if (p.getLeftChild() != null)
		{
			// If left subtree exists, then predecessor is its maximum node.
			return findMaxNodeFrom(p.getLeftChild());
		} else
		{
			// If left subtree does not exist, then predecessor is found in ancestor node, unless this node is the min.
			// Climb up ancestors using parent link, until you find a node whose parent's left child is not equal to
			// it, that parent is the predecessor.
			Node child = p;
			Node parent = child.getParent();

			while (parent != null && child == parent.getLeftChild())
			{
				child = parent;
				parent = parent.getParent();
			}

			return parent;
		}
	}

	private Node findMinNodeFrom(Node p)
	{
		Node current = p;

		while (current.getLeftChild() != null)
		{
			current = current.getLeftChild();
		}

		return current;
	}

	private Node findMaxNodeFrom(Node p)
	{
		Node current = p;

		while (current.getRightChild() != null)
		{
			current = current.getRightChild();
		}

		return current;
	}
}
