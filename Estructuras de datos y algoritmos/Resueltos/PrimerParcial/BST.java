package PrimerParcial;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BST<T> implements BinarySearchTree<T> {
	private Node root;
	private Comparator<? super T> cmp;
	private int size;

	public BST(Comparator<? super T> cmp) {
		this.root = null;
		this.cmp = cmp;
		this.size = 0;
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

	private Node add(Node node, T value) {
		if (node == null) {
			size++;
			return new Node(value);
		}
		int aux = cmp.compare(node.value, value);
		if (aux > 0) {
			node.left = add(node.left, value);
			return node;
		}
		if (aux < 0) {
			node.right = add(node.right, value);
			return node;
		} else {
			return node;
		}
	}

	private boolean contains(Node node, T value) {
		if (node == null) {
			return false;
		}
		int aux = cmp.compare(node.value, value);
		if (aux == 0) {
			return true;
		}
		if (aux > 0) {
			return contains(node.left, value);
		} else {
			return contains(node.right, value);
		}
	}

	private Node remove(Node node, T value) {
		if (node == null) {
			return null;
		}
		int aux = cmp.compare(node.value, value);
		if (aux > 0) {
			node.left = remove(node.left, value);
			return node;
		}
		if (aux < 0) {
			node.right = remove(node.right, value);
			return node;
		} else {
			size--;
			if (node.right == null && node.left == null) {
				return null;
			}
			if (node.right != null && node.left != null) {
				T leftMax = findMax(node.left);
				node.value = leftMax;
				node.left = remove(node.left, leftMax);
				return node;
			} else {
				if (node.right != null) {
					return node.right;
				} else {
					return node.left;
				}
			}
		}
	}

	private T findMax(Node root) {
		if (root == null) {
			return null;
		}
		if (root.right == null) {
			return root.value;
		}
		return findMax(root.right);
	}

	private class Node {
		T value;
		Node left;
		Node right;

		Node(T value) {
			this.value = value;
		}
	}

	public boolean isPBalanced(double p) {
		if (p < 0) {
			return false;
		}
		if (root == null) {
			return true;
		}
		int aux = isPBalanced(root, p);
		return aux != -1;
	}

	private int isPBalanced(Node root, double p) {
		if (root == null) {
			return 0;
		}
		if (root.left == null && root.right == null) {
			return 1;
		}
		int left = isPBalanced(root.left, p);
		if (left == -1) {
			return -1;
		}
		int right = isPBalanced(root.right, p);
		if (right == -1) {
			return -1;
		}
		int weight = left + right;
		if (left <= p * weight && right <= p * weight) {
			return weight;
		} else {
			return -1;
		}
	}

	@Override
	public int size() {
		return size;
	}

	public void BFSPrint() {
		if (root == null) {
			return;
		}
		Queue<Node> q = new LinkedList<Node>();
		q.offer(root);
		while (!q.isEmpty()) {
			Node current = q.poll();
			if (current.left != null) {
				q.offer(current.left);
			}
			if (current.right != null) {
				q.offer(current.right);
			}
			System.out.print(current.value + ", ");
		}
		return;
	}

	public List<Integer> getInOrder(int inf, int sup) {
		List<Integer> answer = new ArrayList<Integer>();
		if (inf <= 0 || sup <= 0 || inf > sup) {
			return answer;
		}
		getInOrder(root, 1, inf, sup, answer);
		return answer;
	}

	private int getInOrder(Node root, int current, int inf, int sup,
			List<Integer> answer) {
		if (root == null || current > sup) {
			return current;
		}
		current = getInOrder(root.left, current, inf, sup, answer);
		if (current > sup) {
			return current;
		}
		if (current >= inf) {
			answer.add((Integer) root.value);
		}
		current++;
		return getInOrder(root.right, current, inf, sup, answer);
	}

}