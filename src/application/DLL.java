package application;

public class DLL<T> {
	private Node<T> head, tail;
	private int size;

	public DLL() {
		head = tail = null;
		size = 0;
	}

	public void addFirst(T Data) {
		Node<T> n1 = new Node<T>(Data);
		if (size == 0) {
			head = tail = n1;

		} else {
			n1.setNext(head);
			head.setPrev(n1);
			head = n1;

		}
		size++;

	}

	public final void addLast(T data) {
		Node<T> n1 = new Node<T>(data);
		if (size == 0) {
			head = tail = n1;
		} else {
			tail.setNext(n1);
			n1.setPrev(tail);
			tail = n1;
		}
		size++;
	}

	public T getFirst() {
		if (size == 0)
			return null;
		else
			return head.getData();
	}

	public T getLast() {
		if (size == 0)
			return null;
		else
			return tail.getData();
	}

	public final T getIndex(int index) {
		if (index < 0 || index >= size) {
			System.out.println("Invalid index");
			return null;
		}

		Node<T> current;
		if (index < size / 2) {
			current = head;
			for (int i = 0; i < index; i++) {
				current = current.getNext();
			}
		} else {
			current = tail;
			for (int i = size - 1; i > index; i--) {
				current = current.getPrev();
			}
		}
		return current.getData();
	}

	public void add(int index, T data) {
		if (index <= 0) {
			addFirst(data);
		} else if (index >= size) {
			addLast(data);
		} else {
			Node<T> n1 = new Node<T>(data);
			Node<T> current = head;
			for (int i = 0; i < index - 1; i++) {
				current = current.getNext();
			}
			n1.setNext(current.getNext());
			n1.setPrev(current);
			current.getNext().setPrev(n1);
			current.setNext(n1);
			size++;
		}
	}

	public final boolean removeFirst() {
		if (size == 0) {
			System.out.println("there's no data");
			return false;
		} else if (size == 1) {
			head = tail = null;
		} else {
			head = head.getNext();
			head.setPrev(null);
		}
		size--;
		return true;
	}

	public final boolean removeLast() {
		if (size == 0) {
			System.out.println("there's no data");
			return false;
		} else if (size == 1) {
			head = tail = null;
		} else {
			tail = tail.getPrev();
			tail.setNext(null);
		}
		size--;
		return true;
	}

	public final boolean removeIndex(int index) {
		if (index < 0 || index >= size) {
			System.out.println("Invalid index");
			return false;
		} else if (index == 0) {
			return removeFirst();
		}

		else if (index == (size - 1)) {

			return removeLast();
		}

		else {
			Node<T> current = head;
			for (int i = 0; i < index; i++) {
				current = current.getNext();
			}
			current.getPrev().setNext(current.getNext());
			current.getNext().setPrev(current.getPrev());
			size--;
			return true;
		}
	}

}
