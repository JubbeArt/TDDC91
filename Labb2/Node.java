public class Node {
	/* Sorted by key */
	public int key;
	/* Associated data */
	public String val;
	/* Left and subtree */
	public Node left;
	/* Right subtree */
	public Node right;

	public Node(int key, String val) {
		this.key = key;
		this.val = val;
	}
}
