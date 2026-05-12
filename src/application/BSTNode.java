
package application;

public class BSTNode<T extends Comparable<T>> {
	private T data;
	private BSTNode<T> left;
	private BSTNode<T> right;
	BSTNode<T> parent;
	private int height;

	public BSTNode(T data) {
		this.data = data;
		this.left = null;
		this.right = null;
		this.parent = null;
		this.height = 0;
	}

	public T getElement() {
		return data;
	}

	public void setElement(T data) {
		this.data = data;
	}

	public BSTNode<T> getLeft() {
		return left;
	}

	public void setLeft(BSTNode<T> left) {
	    this.left = left;
	    if (left != null) {
	        left.parent = this;
	    }
	}
	public BSTNode<T> getRight() {
		return right;
	}

	public void setRight(BSTNode<T> right) {
		this.right = right;
		if (right != null)
			right.parent = this;
	}

	public BSTNode<T> getParent() {
		return parent;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}