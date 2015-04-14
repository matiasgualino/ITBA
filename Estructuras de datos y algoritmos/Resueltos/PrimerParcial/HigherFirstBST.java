package PrimerParcial;

import java.util.Comparator;

public class HigherFirstBST<T> {
	private Node root;
	private Comparator<? super T> cmp;
	private int size;

	public HigherFirstBST(Comparator<? super T> cmp) {
		this.root = null;
		this.cmp = cmp;
		this.size = 0;
	}

	public T remove() {
		if (root == null) {
			return null;
		}
		T answer = root.value;
		root = remove(root);
		size--;
		return answer;
	}

	private Node remove(Node root) {
		if (root == null || (root.left == null && root.right == null)) {
			return null;
		}
		if (root.left != null && root.right != null) {
			if (cmp.compare(root.left.value, root.right.value) > 0) {
				root.value = root.left.value;
				root.left = remove(root.left);
				return root;
			} else {
				root.value = root.right.value;
				root.right = remove(root.right);
				return root;
			}
		} else {
			if (root.right != null) {
				return root.right;
			} else {
				return root.left;
			}
		}
	}

	private class Node {
		T value;
		Node left;
		Node right;

		Node(T value) {
			this.value = value;
		}
	}

}
