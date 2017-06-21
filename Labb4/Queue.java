// Queue är en kö med metoderna Put, Get, IsEmpty och Empty.

class ListNode {
	Object element;
	ListNode next = null;
}

class Queue {
	private ListNode front = null, back = null;
	private int size = 0;
	
	public void Put(Object element) {
		if (IsEmpty())
			back = front = new ListNode();
		else
			back = back.next = new ListNode();
		back.element = element;
		size++;
	}

	public Object Get() throws Exception {
		if (IsEmpty())
			throw new Exception();
		Object element = front.element;
		front = front.next;
		size--;
		return element;
	}

	public boolean IsEmpty() {
		return front == null;
	}

	public void Empty() {
		front = back = null;
		size = 0;
	}
	
	public int size() {
		return size;
	}
}
