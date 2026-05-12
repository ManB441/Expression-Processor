package application;

public class Node<T> {
	private T Data;
	private Node<T> next, prev;

	public Node(T Data) {

		this.Data = Data;
		next = prev = null;

	}

	public Node(T data, Node next, Node prev) {

		Data = data;
		this.next = next;
		this.prev = prev;
	}

	public T getData() {
		return Data;
	}

	public void setData(T data) {
		Data = data;
	}

	public Node getNext() {
		return next;
	}

	public void setNext(Node next) {
		this.next = next;
	}

	public Node getPrev() {
		return prev;
	}

	public void setPrev(Node prev) {
		this.prev = prev;
	}

	@Override
	public String toString() {
		return "Node [Data=" + Data + ", next=" + next + ", prev=" + prev + "]";
	}

}