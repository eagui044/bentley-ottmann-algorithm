
/**
 * @author E. Aguilar
 * @author A. Hernandez
 *         <p>
 *         This is a Binary Search Tree which is specialized for ordering geometric {@link LineSegment} objects and
 *         maintaining the sweep line status, the line segments are ordered based on whether they are above or below
 *         each other. Successor and predecessor {@link Node} objects which contain the line segments can be retrieved
 *         in constant time.
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
		root.setSegment(null);
	}

	public Node add(LineSegment s, Point eventPoint)
	{
		if (root.getLeftChild() == null)
		{
			Node first = new Node();
			first.setNode(s, null, null, null, null, null);
			root.setLeftChild(first);

			return first;
		} else
		{
			return insert(s, eventPoint, root.getLeftChild());
		}
	}

	public boolean contains(LineSegment s, Point eventPoint)
	{
		return search(s, eventPoint, root.getLeftChild());
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

	private Node findMaxNodeFrom(Node p)
	{
		Node current = p;

		while (current.getRightChild() != null)
		{
			current = current.getRightChild();
		}

		return current;
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

	public Node findNode(LineSegment s, Point eventPoint)
	{
		return findNode(s, eventPoint, root.getLeftChild());
	}

	private Node findNode(LineSegment s, Point eventPoint, Node p)
	{
		if (p == null)
		{
			return null;
		} else if (s.compareTo(p.getSegment(), eventPoint) == 0)
		{
			return p;
		} else if (s.compareTo(p.getSegment(), eventPoint) == -1)
		{
			return findNode(s, eventPoint, p.getLeftChild());
		} else
		{
			return findNode(s, eventPoint, p.getRightChild());
		}
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

	public int getCountOf(LineSegment s, Point eventPoint)
	{
		return getCountOf(s, eventPoint, root.getLeftChild());
	}

	private int getCountOf(LineSegment s, Point eventPoint, Node p)
	{
		if (p != null)
		{
			if (s.compareTo(p.getSegment(), eventPoint) == 0)
			{
				return 1 + getCountOf(s, eventPoint, p.getRightChild());
			} else if (s.compareTo(p.getSegment(), eventPoint) == -1)
			{
				return getCountOf(s, eventPoint, p.getLeftChild());
			} else
			{
				return getCountOf(s, eventPoint, p.getRightChild());
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

	private LineSegment getMax(Node p)
	{
		if (p.getRightChild() == null)
		{
			return p.getSegment();
		} else
		{
			return getMax(p.getRightChild());
		}
	}

	public Node getMaxNode()
	{
		return findMaxNodeFrom(root.getLeftChild());
	}

	public LineSegment getMin()
	{
		return getMin(root.getLeftChild());
	}

	private LineSegment getMin(Node p)
	{
		if (p.getLeftChild() == null)
		{
			return p.getSegment();
		} else
		{
			return getMin(p.getLeftChild());
		}
	}

	public Node getMinNode()
	{
		return findMinNodeFrom(root.getLeftChild());
	}

	private void inorderDisplay(Node p)
	{
		if (p != null)
		{
			inorderDisplay(p.getLeftChild());
			System.out.print(p.getSegment() + " ");
			inorderDisplay(p.getRightChild());
		}
	}

	public void inorderPredecessorSuccessorDisplay(boolean reversed)
	{
		if (!this.isEmpty())
		{
			if (!reversed)
			{
				Node current = getMinNode();

				while (current != null)
				{
					System.out.println(current.getSegment());
					current = current.getSuccessor();
				}
			} else
			{
				Node current = getMaxNode();

				while (current != null)
				{
					System.out.println(current.getSegment());
					current = current.getPredecessor();
				}
			}
		}
	}

	private Node insert(LineSegment s, Point eventPoint, Node p)
	{
		if (s.compareTo(p.getSegment(), eventPoint) == -1) // If < current node, insert left.
		{
			if (p.getLeftChild() == null)
			{
				Node newChild = new Node();
				newChild.setNode(s, p, null, null, null, null);
				p.setLeftChild(newChild);
				newChild.setPredecessor(findInorderPredecessorOf(newChild));
				newChild.setSuccessor(findInorderSuccessorOf(newChild));

				// Update successors and predecessors after insertion of new node.
				if (newChild.getPredecessor() != null)
				{
					newChild.getPredecessor().setSuccessor(newChild);
				}

				if (newChild.getSuccessor() != null)
				{
					newChild.getSuccessor().setPredecessor(newChild);
				}

				return newChild;
			} else
			{
				return insert(s, eventPoint, p.getLeftChild());
			}
		} else // if >= current node, insert right.
		{
			if (p.getRightChild() == null)
			{
				Node newChild = new Node();
				newChild.setNode(s, p, null, null, null, null);
				p.setRightChild(newChild);
				newChild.setPredecessor(findInorderPredecessorOf(newChild));
				newChild.setSuccessor(findInorderSuccessorOf(newChild));

				// Update successors and predecessors after insertion of new node.
				if (newChild.getPredecessor() != null)
				{
					newChild.getPredecessor().setSuccessor(newChild);
				}

				if (newChild.getSuccessor() != null)
				{
					newChild.getSuccessor().setPredecessor(newChild);
				}

				return newChild;
			} else
			{
				return insert(s, eventPoint, p.getRightChild());
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
			System.out.print(p.getSegment() + " ");
		}
	}

	private void preorderDisplay(Node p)
	{
		if (p != null)
		{
			System.out.print(p.getSegment() + " ");
			preorderDisplay(p.getLeftChild());
			preorderDisplay(p.getRightChild());
		}
	}

	public void remove(LineSegment s, Point eventPoint)
	{
		// TODO Using findNode function might be redundant and unnecessary, test code without it.
		Node p = findNode(s, eventPoint, root.getLeftChild());

		if (p != null)
		{
			// Update successors and predecessors before deletion of node.
			if (p.getPredecessor() != null)
			{
				p.getPredecessor().setSuccessor(p.getSuccessor());
			}

			if (p.getSuccessor() != null)
			{
				p.getSuccessor().setPredecessor(p.getPredecessor());
			}

			root.setLeftChild(remove(s, eventPoint, root.getLeftChild()));

			if (root.getLeftChild() != null)
			{
				root.getLeftChild().setParent(null);
			}
		}
	}

	private Node remove(LineSegment s, Point eventPoint, Node p)
	{
		// If tree is empty.
		if (p == null)
		{
			return p;
		} else if (s.compareTo(p.getSegment(), eventPoint) == -1)
		{
			p.setLeftChild(remove(s, eventPoint, p.getLeftChild()));

			if (p.getLeftChild() != null)
			{
				p.getLeftChild().setParent(p);
			}
		} else if (s.compareTo(p.getSegment(), eventPoint) == 1)
		{
			p.setRightChild(remove(s, eventPoint, p.getRightChild()));

			if (p.getRightChild() != null)
			{
				p.getRightChild().setParent(p);
			}
		} else
		{ // If matching LineSegment is found.

			// Node with no child.
			if (p.getLeftChild() == null && p.getRightChild() == null)
			{
				return null;
			} else if (p.getLeftChild() == null) // Node with only a right child.
			{
				return p.getRightChild();
			} else if (p.getRightChild() == null) // Node with only a left child.
			{
				return p.getLeftChild();
			} else // If node has both children, copy inorder successor contents to it, and remove successor.
			{
				Node successor = findInorderSuccessorOf(p);

				// Copy contents of successor to node.
				p.setSegment(successor.getSegment());
				p.setPredecessor(successor.getPredecessor());
				p.setSuccessor(successor.getSuccessor());

				if (p.getPredecessor() != null)
				{
					p.getPredecessor().setSuccessor(p);
				}

				if (p.getSuccessor() != null)
				{
					p.getSuccessor().setPredecessor(p);
				}

				// Remove the inorder successor.
				p.setRightChild(remove(successor.getSegment(), eventPoint, p.getRightChild()));

				if (p.getRightChild() != null)
				{
					p.getRightChild().setParent(p);
				}
			}
		}

		return p;
	}

	private boolean search(LineSegment s, Point eventPoint, Node p)
	{
		if (p == null)
		{
			return false;
		} else if (s.compareTo(p.getSegment(), eventPoint) == 0)
		{
			return true;
		} else if (s.compareTo(p.getSegment(), eventPoint) == -1)
		{
			return search(s, eventPoint, p.getLeftChild());
		} else
		{
			return search(s, eventPoint, p.getRightChild());
		}
	}

	public void swapNodeInfo(Node p, Node q)
	{
		LineSegment temp = p.getSegment();

		// Swapping p with q.
		p.setSegment(q.getSegment());

		// Swapping q with p.
		q.setSegment(temp);
	}

	public String toString()
	{
		return toString(root.getLeftChild());
	}

	private String toString(Node p)
	{
		if (p != null)
		{
			return toString(p.getLeftChild()) + p.getSegment() + "\n" + toString(p.getRightChild());
		} else
		{
			return "";
		}
	}
}
