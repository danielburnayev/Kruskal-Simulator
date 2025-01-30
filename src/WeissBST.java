import java.util.LinkedList;

/**
 * Implements an unbalanced binary search tree.
 * Note that all "matching" is based on the compareTo method.
 * @author Mark Allen Weiss, Daniel Burnayev
 * @param <T> Type of variable the binary search tree will hold 
 */
public class WeissBST<T extends Comparable<? super T>>
{

	/**
	* Inner class of a basic node stored in unbalanced binary search trees.
	* @param <T> Type of variable the node will hold 
	*/
	private class BinaryNode<T>
	{
		/**
		* Constructor for a binary node with an element of type T and left and right children, both of type T.
		* @param theElement will be the element of type T this node has in it
		*/
		BinaryNode(T theElement) {
	   		element = theElement;
            left = right = null;
        }
		
		/** The data in the node. */
		T element;

        /** The left child of this node. */
        BinaryNode<T> left;	
        
        /** The right child of this node. */
        BinaryNode<T> right;
	}
	
	/** The tree root. */
	private BinaryNode<T> root;
	

	/**
	* Construct the tree.
	*/
	public WeissBST()
	{
		root = null;
	}

	/**
	* Insert into the tree.
	* @param x the item to insert.
	* @throws Exception if x is already present.
	*/
	public void insert(T x)
	{
		root = insert(x, root);
	}

	/**
	* Remove minimum item from the tree.
	* @throws Exception if tree is empty.
	*/
	public void removeMin()
	{
		root = removeMin(root);
	}

	/**
	* Find the smallest item in the tree.
	* @return smallest item or null if empty.
	*/
	public T findMin()
	{
		return elementAt(findMin(root));
	}


	/**
	* Find an item in the tree.
	* @param x the item to search for.
	* @return the matching item or null if not found.
	*/
	public T find(T x)
	{
		return elementAt(find(x, root));
	}

	/**
	* Make the tree logically empty.
	*/
	public void makeEmpty()
	{
		root = null;
	}

	/**
	* Test if the tree is logically empty.
	* @return true if empty, false otherwise.
	*/
	public boolean isEmpty()
	{
		return root == null;
	}

	/**
	* Internal method to get element field.
	* @param t the node.
	* @return the element field or null if t is null.
	*/
	private T elementAt(BinaryNode<T> t)
	{
		return t == null ? null : t.element;
	}

	/**
	* Internal method to insert into a subtree.
	* @param x the item to insert.
	* @param t the node that roots the tree.
	* @return the new root.
	* @throws Exception if x is already present.
	*/
	private BinaryNode<T> insert(T x, BinaryNode<T> t)
	{
		if( t == null )
			t = new BinaryNode<T>(x);
		else if( x.compareTo(t.element) < 0 )
			t.left = insert(x, t.left);
		else if( x.compareTo(t.element) > 0 )
			t.right = insert(x, t.right);
		else
			throw new IllegalArgumentException("Duplicate Item: " + x.toString());  // Duplicate
		return t;
	}


	/**
	 * Internal method to remove minimum item from a subtree.
	 * @param t the node that roots the tree.
	 * @return the new root.
	 * @throws Exception if t is empty.
	 */
	private BinaryNode<T> removeMin( BinaryNode<T> t )
	{
		if( t == null )
			throw new IllegalArgumentException( "Min Item Not Found");
		else if( t.left != null )
		{
			t.left = removeMin( t.left );
			return t;
		}
		else
			return t.right;
	}	

	/**
	 * Internal method to find the smallest item in a subtree.
	 * @param t the node that roots the tree.
	 * @return node containing the smallest item.
	 */
	private BinaryNode<T> findMin( BinaryNode<T> t )
	{
		if( t != null )
			while( t.left != null )
				t = t.left;

		return t;
	}


	/**
	 * Internal method to find an item in a subtree.
	 * @param x is item to search for.
	 * @param t the node that roots the tree.
	 * @return node containing the matched item.
	 */
	private BinaryNode<T> find( T x, BinaryNode<T> t )
	{
		while( t != null )
		{
			if( x.compareTo( t.element ) < 0 )
				t = t.left;
			else if( x.compareTo( t.element ) > 0 )
				t = t.right;
			else
				return t;	// Match
		}
		
		return null;		 // Not found
	}

	/**
	* Remove from the tree..
	* @param x the item to remove.
	* @throws Exception if x is not found.
	*/
	public void remove( T x )
	{
		root = remove( x, root );
	}

	/**
	* Internal method to remove from a subtree.
	* @param x the item to remove.
	* @param t the node that roots the tree.
	* @return the new root.
	* @throws Exception if x is not found.
	*/
	private BinaryNode<T> remove( T x, BinaryNode<T> t )
	{
		if( t == null )
			throw new IllegalArgumentException( "Item Not Found: " + x.toString( ) );
		if( x.compareTo( t.element ) < 0 )
			t.left = remove( x, t.left );
		else if( x.compareTo( t.element ) > 0 )
			t.right = remove( x, t.right );
		else if( t.left != null && t.right != null ) // Two children
		{
			t.element = findMax(t.left).element;
			t.left = removeMax(t.left);
		}
		else
			t = ( t.left != null ) ? t.left : t.right;
			
			
		return t;
	}

	/** 
	* Gets the size of the unbalanced binary search tree. 
	* @return The number of nodes in the tree
	*/
	public int size(){
		return sizeHelper(root);
	}
	
	/**
	* Recursive helper method that finds the size() method get the size.
	* @param node The node the method is currently on
	* @return The size of the subtree, and eventually the whole tree
	*/
	private int sizeHelper(BinaryNode<T> node) {
		if (node == null) {return 0;}
		
		return 1 + sizeHelper(node.left) + sizeHelper(node.right);
	}

	/**
	* Gets the String representation of the unbalanced binary search tree.
	* @return The string representation
	*/
	public String toString(){
		return toStringHelper(root);
  	}
  	
	/**
	* Recursive helper method that gets the String of a tree in pre-order traversal.
	* @param node The node the method is currently on
	* @return Parts of the string
	*/
	private String toStringHelper(BinaryNode<T> node) {
		if (node == null) {return "";}
		
		return toStringHelper(node.left) + elementAt(node) + " " + toStringHelper(node.right);
	}
	
	/**
	* Gets a list of all the values in an unbalanced binary search tree.
	* @return A linked list of all the values of the tree's elements
	*/
	public LinkedList<T> values(){
		LinkedList<T> answer = new LinkedList<T>();
		valuesHelper(root, answer);
		return answer;
	}
	
	/**
	* Recursive helper method that puts the values of the tree into a list one by one in in-order traversal.
	* @param node The node the method is currently on
	* @param list The linked list that's getting values inserted in it
	*/
	private void valuesHelper(BinaryNode<T> node, LinkedList<T> list) {
		if (node == null) {return;}
		
		list.add(elementAt(node));
		valuesHelper(node.left, list);
		valuesHelper(node.right, list);
	}

	/**
	* Finds the biggest element in an unbalanced binary search tree.
	* @param t The node that the method is currently on
	* @return The biggest node in the tree
	*/
	private BinaryNode<T> findMax( BinaryNode<T> t )
	{
		if( t != null )
			while( t.right != null )
				t = t.right;

		return t;
	}
	
	/**
	* Finds and removes the biggest node from an unbalanced binary search tree.
	* @param t The node that the method is currently on
	* @return The node that was removed
	*/
	private BinaryNode<T> removeMax( BinaryNode<T> t )
	{
		if( t == null )
			throw new IllegalArgumentException( "Max Item Not Found");
		else if( t.right != null )
		{
			t.right = removeMin( t.right );
			return t;
		}
		else
			return t.left;
	}


	/**
	* Method that allows coders to test this classes code.
	* @param args Command line arguments given to the method
	*/
	public static void main( String [ ] args )
	{
		WeissBST<Integer> t = new WeissBST<Integer>( );

		if (t.size()==0 && t.isEmpty() && t.find(310)==null){
			System.out.println("Yay 1");
		}
		
		t.insert(310);
		t.insert(112);
		t.insert(440);
		t.insert(330);
		t.insert(471);
		LinkedList<Integer> values = t.values();
  		
		
		if (t.size()==5 && t.toString().equals("112 310 330 440 471 ") && !t.isEmpty()){
			System.out.println("Yay 2");
		}

		if (values.size()==5 && values.get(0)==310 && values.get(1)==112 &&
			values.get(2) == 440 && values.get(3)== 330 && values.get(4)== 471){
			System.out.println("Yay 3");
		}
		
		t.remove(440);
		
		values = t.values();
		if (values.size() == 4 && values.get(2)==330 && values.get(3)==471){
			System.out.println("Yay 4");		
		}
	
	}
	
}
