/**
* @author A. Hernandez,
* @author E. Aguilar,
* adaptation from Data Structures class (COP 3530)
*/
public class BinarySearchTree
{	
	private Node root;    //root of the bst; implemented as a dummy node.
	
    public BinarySearchTree()
    {
        root = new Node();        //dummy node as the root
        root.setLeftChild(null);
        root.setRightChild(null);
        root.setInfo(-1);
    }

    public void add(int x)
    {
        if (root.getLeftChild() == null)
        {
            Node p = new Node();
            p.setNode(x, null, null);
            root.setLeftChild(p);
        } else
            insert(x, root.getLeftChild());
    }
    
    public boolean contains(int x)
    {
        return search(x, root.getLeftChild());
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
	    	return 1 + getCount(p.getLeftChild()) + 
	    				getCount(p.getRightChild());
    	}
    	else
    	{
    		return 0;
    	}
    }
    
    public int getCountOf(int x)
    {
    	return getCountOf(x, root.getLeftChild());
    }
    
    private int getCountOf(int x, Node p)
    {
    	if (p != null)
    	{
    		if (p.getInfo() == x)
        	{
        		return 1 + getCountOf(x, p.getRightChild());
        	}
        	else
        	{
        		if (x < p.getInfo())
        		{
        			return getCountOf(x, p.getLeftChild());
        		}
        		else
        		{
        			return getCountOf(x, p.getRightChild());
        		}
        	}
    	}
    	else
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
    		return 1 + Math.max(getHeight(p.getLeftChild()), 
    							getHeight(p.getRightChild()));
    	}
    	else
    	{
    		return -1;
    	}
    }
    
    public int getMax()
    {
    	return getMax(root.getLeftChild());
    }
    
    public int getMax(Node p)
    {
    	if (p.getRightChild() == null)
    	{
    		return p.getInfo();
    	}
    	else
    	{
    		return getMax(p.getRightChild());
    	}
    }
    
    public int getMin()
    {
        return getMin(root.getLeftChild());
    }
    
    private int getMin(Node p)
    {
        if (p.getLeftChild() == null)
            return p.getInfo();
        else
            return getMin(p.getLeftChild());
    }
    
    private void inorderDisplay(Node p)
    {
        if (p != null)
        {
            inorderDisplay(p.getLeftChild());
            if (!p.isDeleted()) System.out.print(p.getInfo() + " ");
            inorderDisplay(p.getRightChild());
        }
    }
    
    private void insert(int x, Node p)
    {
        if (x < p.getInfo())
            if (p.getLeftChild() == null)
            {
                Node q = new Node();
                q.setNode(x, null, null);
                p.setLeftChild(q);
            } else
                insert(x, p.getLeftChild());
        else if (p.getRightChild() == null)
        {
            Node q = new Node();
            q.setNode(x, null, null);
            p.setRightChild(q);
        } else
            insert(x, p.getRightChild());
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
            if (!p.isDeleted()) System.out.print(p.getInfo() + " ");
        }
    }

    private void preorderDisplay(Node p)
    {
    	if (p != null)
        {
    		if (!p.isDeleted()) System.out.print(p.getInfo() + " ");
    		preorderDisplay(p.getLeftChild());
    		preorderDisplay(p.getRightChild());
        }
    }

    void remove(int x)
    {
    	remove(x, root.getLeftChild());
    }
    
    void remove(int x, Node p)
    {
    	if (p != null)
    	{
    		if (x == p.getInfo())
    		{
    			p.setDeleted(true);
    			remove(x, p.getRightChild());
    		}
    		else 
    		{
    			if (x < p.getInfo())
	    		{
	    			remove(x, p.getLeftChild());
	    		}
	    		else
	    		{
	    			remove(x, p.getRightChild());
	    		}
    		}
    	}
    }
    
    private boolean search(int x, Node p)
    {
        if (p == null)
            return false;
        else if (x == p.getInfo())
            return true;
        else if (x < p.getInfo())
            return search(x, p.getLeftChild());
        else
            return search(x, p.getRightChild());
    }
    
    public String toString()
    {
    	return toString(root.getLeftChild());
    }
    
    private String toString(Node p)
    {    	
    	if (p != null)
    	{
    		return toString(p.getLeftChild()) +
    				p.getInfo() + " " +
    				toString(p.getRightChild());
    	}
    	else
    	{
    		return "";
    	}
    }
}
