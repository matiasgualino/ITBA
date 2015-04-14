package TP5;

import java.util.Comparator;

public class BinaryTreeRoot<T> {
	private BinaryTree<T> root;

	public BinaryTreeRoot() {
		this.root = null;
	}

	public BinaryTree<T> getRoot() {
		return root;
	}

	public void add(T data, Comparator<? super T> cmp) {
		if (data != null) {
			root = addRecursive(data, root, cmp);
		}
	}

	private BinaryTree<T> addRecursive(T data, BinaryTree<T> tree,
			Comparator<? super T> cmp) {
		if (tree == null)
			return new BinaryTree<T>(data);
		if (cmp.compare(tree.getData(), data) > 0) {
			tree.setLeft(addRecursive(data, tree.getLeft(), cmp));
		}
		if (cmp.compare(tree.getData(), data) < 0) {
			tree.setRight(addRecursive(data, tree.getRight(), cmp));
		}
		return tree;
	}
}
