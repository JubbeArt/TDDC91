public class BST {
	/* Root of BST */
	private Node root;
	/* Number of nodes in BST */
	private int size;

	public BST() {
		this.root = null;
		this.size = 0;
	}

	/**
	 * Is the BST empty?
	 */
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Return root of BST
	 */
	public Node getRoot() {
		return root;
	}

	/**
	 * Return number of key-value pairs in BST
	 */
	public int size() {
		return size;
	}

	/**
	 * Does there exist a key-value pair with given key?
	 */
	public boolean contains(int key) {
		return find(key) != null;
	}

	/**
	 * Return value associated with the given key, or null if no such key exists
	 */
	public String find(int key) {
		return find(root, key);
	}

	/**
	 * Return value associated with the given key, or null if no such key exists
	 * in subtree rooted at x
	 */
	private String find(Node x, int key) {
		if (x == null) {
			return null;
		}
		if (key < x.key) {
			return find(x.left, key);
		} else if (key > x.key) {
			return find(x.right, key);
		} else {
			return x.val;
		}
	}

	/**
	 * Insert key-value pair into BST. If key already exists, update with new
	 * value
	 */
	public void insert(int key, String val) {
		if (val == null) {
			remove(key);
			return;
		}
		root = insert(root, key, val);
	}

	/**
	 * Insert key-value pair into subtree rooted at x. If key already exists,
	 * update with new value.
	 */
	private Node insert(Node x, int key, String val) {
		if (x == null) {
			size++;
			return new Node(key, val);
		}
		if (key < x.key) {
			x.left = insert(x.left, key, val);
		} else if (key > x.key) {
			x.right = insert(x.right, key, val);
		} else {
			x.val = val;
		}

		return x;
	}

	/**
	 * Remove node with given key from BST
	 */
	public void remove(int key) {
		if(!contains(key)) // No node found with the key sent it
			return;
		
		size--; // It exists SO IT WILL BE REMOVED
		
		Node delete = findNode(key); // Key => node

		if(delete == root) { // Speciallfallet då man ska ta bort rooten
			deleteRoot();
			return;
		}
		
		Node parent = findParent(delete.key);
			
		if(delete.left == null || delete.right == null) {// Has one or less children
			removeChild(parent, delete);
		} else { // The deleted has children on both sides (the hard case)
				
			Node predecessor = findPredecessor(delete); // Go one to the left, the as far as right as you can
			Node predParent = findParent(predecessor.key); 
			
			if(predParent != delete) { // 
				if(predecessor.left != null) // If the predecessor has a left child (the only case for the predecessor)
					predParent.right = predecessor.left;
				else // Added
					predParent.right = null;
				
				predecessor.left = delete.left;
			}
			
			// Change the predecessor connections to the deleteds connections	
			predecessor.right = delete.right;
				
			if(delete == parent.left) // Move the predecessor
				parent.left = predecessor;
			else if(delete == parent.right) 
				parent.right = predecessor;	

		}	

	} 

	// Only works with one or less children on the node child
	private void removeChild(Node parent, Node child) {
		Node grandChild = null;
		
		if(child.left != null) // Get the child of the removed if it exsits
			grandChild = child.left;
		else if(child.right != null)
			grandChild = child.right;
			
		if(parent.left == child) // Check where the child is
			parent.left = grandChild;
		else if(parent.right == child)
			parent.right = grandChild;		
	}
	
	private void deleteRoot() {
		
		if(root.left == null || root.right == null) // Has or less one children, find child (or null) > replace root
			root = root.left != null ? root.left : root.right;
		else { // Has children on both sides
			
			// Find the predecessor and the parent of the predecessor
			Node predecessor = findPredecessor(root);				
			Node predParent = findParent(predecessor.key);
					
			if(predecessor != root.left) {
				// Har lagt till de här
				if(predecessor.left != null) { // Predecessor has a left child
					predParent.right = predecessor.left; // Move left child to the parents right child
				} else {
					predParent.right = null;
				}
				
				predecessor.left = root.left;
														
			}
			
			// Change the root and its connections
			predecessor.right = root.right; // Allways inherit the right side of root
			root = predecessor;			
		}
	}
	
	private String toString(Node n) {
		String s = " : " + n.key +  ", left : ";
		s += n.left != null ? n.left.key : "null";
		s +=  ", right : ";
		s += n.right != null ? n.right.key : "null";
		return s;
	}
	
	// Return a node from a key
	private Node findNode(int key) {
			
		Node currentNode = root; // Start at root
		
		while(true) {
			if(key == currentNode.key) // Found the node
				return currentNode;
			else if(key < currentNode.key && currentNode.left != null) // Compare key, go left
				currentNode = currentNode.left;
			else if(key > currentNode.key && currentNode.right != null) // Compare key, go right
				currentNode = currentNode.right;
		}	
			
	}
	
	// Return the parent of a node (the key sent in)
	private Node findParent(int key) {
		Node currentNode = root;
		
		while(true) {		
			// Has found the parent (child is at the left of parent)
			if(currentNode.left != null && key == currentNode.left.key) 
					return currentNode;
			// Has found the parent (child is at the right)
			else if(currentNode.right != null && key == currentNode.right.key) 
					return currentNode;

			if(key < currentNode.key && currentNode.left != null) // Compare keys, move to left
				currentNode = currentNode.left;
			else // Must move to the right 
				currentNode = currentNode.right;
		}	
	}
	
	private Node findPredecessor(Node delete) {
		Node predecessor = delete.left; // Allways go one to the left (we want to find a smaller number than the deleted)
		
		while(predecessor.right != null) { // Find the closest number to the key by going to right as far as possible
			predecessor = predecessor.right;
		}
		
		return predecessor;
	}

	public PreorderIterator preorder() {
		return new PreorderIterator(root);
	}

	public LevelorderIterator levelorder() {
		return new LevelorderIterator(root);
	}
}
