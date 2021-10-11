/**
* @author A. Hernandez
* @author E. Aguilar,
* adaptation from Data Structures class (COP 3530)
*/
public class Node
{
	private int info;     //element stored in this node
    private Node left;    //link to left child
    private Node right;   //link to right child
    private boolean isDeleted; //deleted node flag

    Node()
    {
        info = 0;
        left = right = null;
        isDeleted = false;
    }

    public void setNode(int x, Node l, Node r)
    {
        info = x;
        left = l;
        right = r;
    }

    public int getInfo()
    {
        return info;
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

    public void setInfo(int x)
    {
        info = x;
    }

    public void setLeftChild(Node l)
    {
        left = l;
    }

    public void setRightChild(Node r)
    {
        right = r;
    }
    
    public void setDeleted(boolean delete)
    {
    	isDeleted = delete;
    }
}
