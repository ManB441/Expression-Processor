package application;

public class BST<T extends Comparable<T>> {
	protected BSTNode<T> root;

	public BST() {
		root = null;
	}

	public void insert(T element) {
		root = insertRec(root, element);
	}

	private BSTNode<T> insertRec(BSTNode<T> node, T element) {
		if (node == null) {
			return new BSTNode<>(element);
		}

		int cmp = element.compareTo(node.getElement());
		if (cmp < 0) {
			node.setLeft(insertRec(node.getLeft(), element));
		} else if (cmp > 0) {
			node.setRight(insertRec(node.getRight(), element));
		}
		// إذا يساوي (تكرار) - ما نضيف
		return node;
	}

	public T search(T data) {
		BSTNode<T> current = root;

		while (current != null) {
			int result = data.compareTo(current.getElement());

			if (result == 0) {
				return current.getElement();
			} else if (result < 0) {
				current = current.getLeft();
			} else {
				current = current.getRight();
			}
		}
		return null;
	}

	public BSTNode<T> getMinNode() {
		return findMin(root);
	}

	public BSTNode<T> getSuccessor(BSTNode<T> node) {
		return inordersuccessor(node);
	}

	private BSTNode<T> findMin(BSTNode<T> node) {
		if (node == null)
			return null;
		while (node.getLeft() != null)
			node = node.getLeft();
		return node;
	}

	private BSTNode<T> inordersuccessor(BSTNode<T> node) {
		if (node == null)
			return null;

		if (node.getRight() != null) {
			return findMin(node.getRight());
		}

		BSTNode<T> parent = node.getParent();
		BSTNode<T> current = node;

		while (parent != null && current == parent.getRight()) {
			current = parent;
			parent = parent.getParent();
		}

		return parent;
	}

	public BSTNode<T> getRoot() {
		return root;
	}

	
	public BSTNode<T> getInorderSuccessor(BSTNode<T> node) {
		if (node == null)
			return null;

		if (node.getRight() != null) {
			return findMin(node.getRight());
		}

		BSTNode<T> successor = null;
		BSTNode<T> current = root;

		while (current != null) {
			int cmp = node.getElement().compareTo(current.getElement());

			if (cmp < 0) {
				successor = current;
				current = current.getLeft();
			} else if (cmp > 0) {
				current = current.getRight();
			} else {
				break;
			}
		}

		return successor;
	}
}