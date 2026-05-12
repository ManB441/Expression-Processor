package application;

public class AVL<T extends Comparable<T>> extends BST<T> {

	@Override
	public void insert(T data) {
		root = insertAVL(root, data);
	}

	private BSTNode<T> insertAVL(BSTNode<T> node, T data) {
		if (node == null) {
			return new BSTNode<>(data);
		}

		int cmp = data.compareTo(node.getElement());

		if (cmp < 0) {
			node.setLeft(insertAVL(node.getLeft(), data));
		} else if (cmp > 0) {
			node.setRight(insertAVL(node.getRight(), data));
		} else {
			return node;
		}
// لما نضيف نود جديد لازم يتعدل االارتفاع 
		// ولما نضيف لازم نتاكد اذا بلنس او لا 
		updateHeight(node);
		return rebalance(node);
	}

	public void delete(T data) {
		root = deleteAVL(root, data);
	}

	private BSTNode<T> deleteAVL(BSTNode<T> node, T data) {
		if (node == null) {
			return null;
		}

		int cmp = data.compareTo(node.getElement());

		if (cmp < 0) {
			node.setLeft(deleteAVL(node.getLeft(), data));
		} else if (cmp > 0) {
			node.setRight(deleteAVL(node.getRight(), data));
		} else {
			if (node.getLeft() == null || node.getRight() == null) {
				BSTNode<T> temp;
				if (node.getLeft() != null) {
					temp = node.getLeft();
				} else {
					temp = node.getRight();
				}

				if (temp == null) {
					node = null;
				} else {
					node = temp;
				}
			} else {
				BSTNode<T> successor = getSuccessor(node);
				node.setElement(successor.getElement());
				node.setRight(deleteAVL(node.getRight(), successor.getElement()));
			}
		}

		if (node == null) {
			return null;
		}
		// لما نحذف نود جديد لازم يتعدل االارتفاع 
				// ولما نحذف  لازم نتاكد اذا بلنس او لا 
		updateHeight(node);
		return rebalance(node);
	}

	private void updateHeight(BSTNode<T> node) {
		int leftH = height(node.getLeft());
		int rightH = height(node.getRight());
		node.setHeight(1 + Math.max(leftH, rightH));
	}

	private int height(BSTNode<T> node) {
		if (node == null) {
			return -1;
		} else {
			return node.getHeight();
		}
	}

	private int getbalance(BSTNode<T> node) {
		if (node == null) {
			return 0;
		} else {
			return height(node.getLeft()) - height(node.getRight());
		}
	}

	private BSTNode<T> rebalance(BSTNode<T> node) {
		int bf = getbalance(node);

		if (bf > 1) {
			if (getbalance(node.getLeft()) < 0) {
				node.setLeft(rotateLeft(node.getLeft()));
			}
			return rotateRight(node);
		}

		if (bf < -1) {
			if (getbalance(node.getRight()) > 0) {
				node.setRight(rotateRight(node.getRight()));
			}
			return rotateLeft(node);
		}

		return node;
	}

	private BSTNode<T> rotateRight(BSTNode<T> y) {
		BSTNode<T> x = y.getLeft();
		BSTNode<T> T2 = x.getRight();

		x.setRight(y);
		y.setLeft(T2);

		updateHeight(y);
		updateHeight(x);

		return x;
	}

	private BSTNode<T> rotateLeft(BSTNode<T> x) {
		BSTNode<T> y = x.getRight();
		BSTNode<T> T2 = y.getLeft();

		y.setLeft(x);
		x.setRight(T2);

		updateHeight(x);
		updateHeight(y);

		return y;
	}

	public int getHeight() {
		if (root == null) {
			return 0;
		} else {
			return root.getHeight();
		}
	}

	public BSTNode<T> getSuccessor(BSTNode<T> node) {
		BSTNode<T> current = node.getRight();
		while (current.getLeft() != null) {
			current = current.getLeft();
		}
		return current;
	}
}
