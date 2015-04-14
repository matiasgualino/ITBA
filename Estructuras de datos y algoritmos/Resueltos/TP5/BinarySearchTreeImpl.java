package TP5;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class BinarySearchTreeImpl<T> implements BinarySearchTree<T>,
		Iterable<T> {

	private Node<T> root;
	private Comparator<? super T> cmp;

	public BinarySearchTreeImpl(Comparator<? super T> cmp) {
		this.root = null;
		this.cmp = cmp;
	}

	public void add(T value) {
		root = add(root, value);
	}

	public boolean contains(T value) {
		return contains(root, value);
	}

	public void remove(T value) {
		root = remove(root, value);
	}

	public int size() {
		return size(root);
	}

	private int size(Node<T> node) {
		if (node == null) {
			return 0;
		} else {
			return 1 + size(node.left) + size(node.right);
		}
	}

	@Override
	public int hashCode() {
		return hashCode(root);
	}

	private int hashCode(Node<T> root) {
		int aux;
		if (root == null)
			return 0;
		aux = (root.value.hashCode()) * 2;
		if (root.left != null)
			aux += hashCode(root.left) * 3;
		if (root.right != null)
			aux += hashCode(root.right) * 5;
		return aux;
	}

	public Node<T> getRoot() {
		return root;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj.getClass() != this.getClass())
			return false;
		return equals(root, ((BinarySearchTreeImpl<T>) obj).getRoot());
	}

	private boolean equals(Node<T> root, Node<T> root2) {
		if (root == null && root2 == null)
			return true;
		if ((root != null && root2 == null) || (root == null && root2 != null))
			return false;
		else
			return root.value.equals(root.value)
					&& equals(root.left, root2.left)
					&& equals(root.right, root2.right);
	}

	public int levelOf(T value) {
		if (root == null)
			return -1;
		return levelOfRec(root, value, cmp, 0);
	}

	private int levelOfRec(Node<T> root, T value, Comparator<? super T> cmp,
			int level) {
		int aux = cmp.compare(root.value, value);
		if (aux == 0) {
			return level;
		}
		if (aux < 0) {
			if (root.right == null) {
				return -1;
			}
			return levelOfRec(root.right, value, cmp, level + 1);
		} else {
			if (root.left == null) {
				return -1;
			}
			return levelOfRec(root.left, value, cmp, level + 1);
		}
	}

	public int leaves() {
		return leavesRec(root);
	}

	private int leavesRec(Node<T> root) {
		if (root == null)
			return 0;
		if (root.left == null && root.right == null)
			return 1;
		return leavesRec(root.left) + leavesRec(root.right);
	}

	public T getMax() {
		if (root == null) {
			return null;
		}
		Node<T> current = root;
		Node<T> previous = null;
		while (current != null) {
			previous = current;
			current = current.right;
		}
		return previous.value;
	}

	public void printAntecesor(Node<T> node) {
		int a = printAntecesorRec(node.value, root, cmp);
		if (a == 1)
			System.out.println(root.value);
	}

	private int printAntecesorRec(T value, Node<T> root,
			Comparator<? super T> cmp) {
		if (root == null) {
			return 0;
		}
		int aux = cmp.compare(root.value, value);
		if (aux == 0) {
			return 1;
		}
		if (aux < 0) {
			int right = printAntecesorRec(value, root.right, cmp);
			if (right == 1) {
				System.out.println(root.value);
				return 1;
			} else
				return 0;
		}
		if (aux > 0) {
			int left = printAntecesorRec(value, root.left, cmp);
			if (left == 1) {
				System.out.println(root.value);
				return 1;
			} else
				return 0;
		}
		return 0;
	}

	public void printDescendant(Node<T> node) {
		printDescendantRec(node.value, root, cmp);
	}

	private void printDescendantRec(T value, Node<T> root,
			Comparator<? super T> cmp) {
		if (root == null)
			return;
		int aux = cmp.compare(root.value, value);
		if (aux == 0) {
			print(root.left);
			print(root.right);
		}
		if (aux < 0) {
			printDescendantRec(value, root.right, cmp);
			return;
		} else {
			printDescendantRec(value, root.left, cmp);
			return;
		}
	}

	public void print(Node<T> root) {
		if (root == null)
			return;
		System.out.println(root.value);
		print(root.left);
		print(root.right);
		return;
	}

	private Node<T> add(Node<T> node, T value) {
		if (node == null) {
			return new Node<T>(value);
		} else {
			int aux = cmp.compare(node.value, value);
			if (aux > 0) {
				node.left = add(node.left, value);
			} else if (aux < 0) {
				node.right = add(node.right, value);
			}
			return node;
		}
	}

	private boolean contains(Node<T> node, T value) {
		if (node == null) {
			return false;
		} else {
			int aux = cmp.compare(node.value, value);
			if (aux == 0) {
				return true;
			} else if (aux > 0) {
				return contains(node.left, value);
			} else {
				return contains(node.right, value);
			}
		}
	}

	private Node<T> remove(Node<T> node, T value) {
		if (node == null) {
			return null;
		}
		int aux = cmp.compare(node.value, value);
		if (aux == 0) {
			if (node.left == null) {
				return node.right;
			} else if (node.right == null) {
				return node.left;
			} else {
				node.left = replaceWithInorderPredecessor(node.left, node);
				return node;
			}
		} else if (aux > 0) {
			node.left = remove(node.left, value);
		} else {
			node.right = remove(node.right, value);
		}
		return node;
	}

	private Node<T> replaceWithInorderPredecessor(Node<T> node, Node<T> toRemove) {
		if (node.right == null) {
			toRemove.value = node.value;
			return node.left;
		}
		node.right = replaceWithInorderPredecessor(node.right, toRemove);
		return node;
	}

	private static class Node<T> {
		T value;
		Node<T> left;
		Node<T> right;

		Node(T value) {
			this.value = value;
		}
	}

	@Override
	public Iterator<T> iterator() {
		return new PreOrderIterator(this);
	}

	public Iterator<T> inOrderIterator() {
		return new InOrderIterator(this);
	}

	public Iterator<T> postOrderIterator() {
		return new PostOrderIterator(this);
	}

	private class PreOrderIterator implements Iterator<T> {

		Queue<Node<T>> queue;
		BinarySearchTreeImpl<T> tree;
		Node<T> last;
		boolean remove;

		public PreOrderIterator(BinarySearchTreeImpl<T> tree) {
			this.tree = tree;
			this.remove = false;
			queue = new LinkedList<Node<T>>();
			addQueue(queue, tree.root);
		}

		private void addQueue(Queue<Node<T>> queue, Node<T> root) {
			if (root == null)
				return;
			queue.add(root);
			addQueue(queue, root.left);
			addQueue(queue, root.right);
			return;
		}

		@Override
		public boolean hasNext() {
			return !queue.isEmpty();
		}

		@Override
		public T next() {
			last = queue.poll();
			remove = true;
			return last.value;
		}

		@Override
		public void remove() {
			if (remove) {
				tree.remove(last.value);
				remove = false;
			}
		}

	}

	private class InOrderIterator implements Iterator<T> {

		Queue<Node<T>> queue;
		BinarySearchTreeImpl<T> tree;
		Node<T> last;
		boolean remove;

		public InOrderIterator(BinarySearchTreeImpl<T> tree) {
			this.tree = tree;
			this.remove = false;
			queue = new LinkedList<Node<T>>();
			addQueue(queue, tree.root);
		}

		private void addQueue(Queue<Node<T>> queue, Node<T> root) {
			if (root == null)
				return;
			addQueue(queue, root.left);
			queue.add(root);
			addQueue(queue, root.right);
			return;
		}

		@Override
		public boolean hasNext() {
			return !queue.isEmpty();
		}

		@Override
		public T next() {
			last = queue.poll();
			remove = true;
			return last.value;
		}

		@Override
		public void remove() {
			if (remove) {
				tree.remove(last.value);
				remove = false;
			}
		}

	}

	private class PostOrderIterator implements Iterator<T> {

		Queue<Node<T>> queue;
		BinarySearchTreeImpl<T> tree;
		Node<T> last;
		boolean remove;

		public PostOrderIterator(BinarySearchTreeImpl<T> tree) {
			this.tree = tree;
			this.remove = false;
			queue = new LinkedList<Node<T>>();
			addQueue(queue, tree.root);
		}

		private void addQueue(Queue<Node<T>> queue, Node<T> root) {
			if (root == null)
				return;
			addQueue(queue, root.left);
			addQueue(queue, root.right);
			queue.add(root);
			return;
		}

		@Override
		public boolean hasNext() {
			return !queue.isEmpty();
		}

		@Override
		public T next() {
			last = queue.poll();
			remove = true;
			return last.value;
		}

		@Override
		public void remove() {
			if (remove) {
				tree.remove(last.value);
				remove = false;
			}
		}

	}
}