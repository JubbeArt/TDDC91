public class Queue<E> {
	protected QueueItem<E> fst;
	protected QueueItem<E> lst;
	protected int size;

	protected static class QueueItem<E> {
		protected E element;
		protected QueueItem<E> next;

		QueueItem(E e, QueueItem<E> n) {
			element = e;
			next = n;
		}
	}

	public Queue() {
		fst = lst = null;
		size = 0;
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public void enqueue(E item) {
		if (isEmpty()) {
			lst = fst = new QueueItem<E>(item, null);
		} else {
			lst.next = new QueueItem<E>(item, null);
			lst = lst.next;
		}
		size++;
	}

	public E dequeue() throws EmptyContainerException {
		if (isEmpty()) {
			throw new EmptyContainerException(">>> Queue is empty.");
		}
		E e = fst.element;
		fst = fst.next;
		size--;
		if (isEmpty()) {
			lst = null;
		}
		return e;
	}
}
