package TP5;

public class BinaryTree<T> {
	private T data;
	private BinaryTree<T> left;
	private BinaryTree<T> right;

	public BinaryTree() {
		this.data = null;
		this.left = null;
		this.right = null;
	}

	public BinaryTree(T data) {
		this.data = data;
		this.left = null;
		this.right = null;
	}

	public BinaryTree(T data, BinaryTree<T> left, BinaryTree<T> right) {
		this.data = data;
		this.left = left;
		this.right = right;
	}

	public T getData() {
		return this.data;
	}

	public BinaryTree<T> getLeft() {
		return left;
	}

	public BinaryTree<T> getRight() {
		return right;
	}

	public void setLeft(BinaryTree<T> newLeft) {
		this.left = newLeft;
	}

	public void setRight(BinaryTree<T> newRight) {
		this.right = newRight;
	}

	public void setData(T data) {
		this.data = data;
	}
}
