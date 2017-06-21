import java.util.Iterator;

public class LevelorderIterator implements Iterator<Node> {
	protected Queue<Node> q;

	public LevelorderIterator(Node tree) {
		q = new Queue<Node>();
		q.enqueue(tree);
		
		/*
		Queue<Node> apa = new Queue<Node>();
		Node lat = null;
		
		apa.enqueue(tree);
		
		while(!apa.isEmpty()) {
			lat = apa.dequeue();
			
			// Add to actual queueueue
			q.enqueue(lat);
			
			if(lat.left != null)
				apa.enqueue(lat.left);
			if(lat.right != null)
				apa.enqueue(lat.right);
				
		}*/
	}
	
	public void remove() {
		// Leave empty
	}

	public Node next() {
		Node tmp = q.dequeue();
		
		if(tmp.left != null)
			q.enqueue(tmp.left);
		if(tmp.right != null)
			q.enqueue(tmp.right);
			
		return tmp;
	}

	public boolean hasNext() {
		return q.size != 0;
	}
}
