import java.util.Iterator;

public class PreorderIterator implements Iterator<Node> {
	protected Stack<Node> s;

	public PreorderIterator(Node tree) {
		s = new Stack<Node>();
		if (tree != null)
			s.push(tree);
	}

	public void remove() {
		// Leave empty
	}

	public Node next() {
		Node tmp = s.pop();
		
		if(tmp.right != null)
			s.push(tmp.right);
		if(tmp.left != null)
			s.push(tmp.left);
		
		return tmp;
		
	}

	public boolean hasNext() {
		return s.size != 0;
	}
}
