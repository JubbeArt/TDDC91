public class Stack<E> {
	protected StackItem<E> top;
	protected int size;

	public static class StackItem<E> {
		public E element;
		public StackItem<E> next;

		public StackItem(E e, StackItem<E> n) {
			element = e;
			next = n;
		}
	}

	public Stack() {
		top = null;
		size = 0;
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public void push(E element) {
		top = new StackItem<E>(element, top);
		size++;
	}

	public E pop() throws EmptyContainerException {
		if (isEmpty()) {
			throw new EmptyContainerException(">>> Stack is empty.");
		}
		E tmp = top.element;
		top = top.next;
		size--;
		return tmp;
	}
}
