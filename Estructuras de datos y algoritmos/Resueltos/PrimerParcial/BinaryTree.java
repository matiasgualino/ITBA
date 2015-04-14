package PrimerParcial;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BinaryTree<T> {
	private T value;
	private BinaryTree<T> left;
	private BinaryTree<T> right;

	public BinaryTree(T value, BinaryTree<T> left, BinaryTree<T> right) {
		this.value = value;
		this.left = left;
		this.right = right;
	}

	public T getValue() {
		return value;
	}

	public BinaryTree<T> getLeft() {
		return left;
	}

	public BinaryTree<T> getRight() {
		return right;
	}

	public void setLeft(BinaryTree<T> left) {
		this.left = left;
	}

	public void setRight(BinaryTree<T> right) {
		this.right = right;
	}

	public static <T> BinaryTree<T> buildFromList(List<T> values) {
		if (values.size() == 0 || values == null) {
			return null;
		}
		Iterator<T> it = values.iterator();
		BinaryTree<T> answer = new BinaryTree<T>(it.next(), null, null);
		Queue<BinaryTree<T>> q = new LinkedList<BinaryTree<T>>();
		q.offer(answer);
		while (it.hasNext()) {
			BinaryTree<T> current = q.poll();
			BinaryTree<T> left = new BinaryTree<T>(it.next(), null, null);
			current.setLeft(left);
			q.offer(left);
			if (it.hasNext()) {
				BinaryTree<T> right = new BinaryTree<T>(it.next(), null, null);
				q.offer(right);
				current.setRight(right);
			}
		}
		return answer;
	}

	@SuppressWarnings("unchecked")
	public boolean hasRoad(int n) {
		return hasRoad((BinaryTree<Integer>) this, n);
	}

	@SuppressWarnings("unchecked")
	private boolean hasRoad(BinaryTree<Integer> current, int left) {
		int aux = left - current.value;
		if (aux == 0) {
			return true;
		}
		if (aux < 0) {
			return false;
		} else {
			boolean aux2 = (hasRoad((BinaryTree<Integer>) this.left, aux) || hasRoad(
					(BinaryTree<Integer>) this.right, aux));
			return aux2;
		}
	}

	public static <T> boolean checkPostorder(BinaryTree<T> tree, List<T> values) {
		if (tree == null || values == null) {
			return false;
		}
		return checkPostorder(tree, values.iterator());
	}

	private static <T> boolean checkPostorder(BinaryTree<T> tree, Iterator<T> it) {
		if (it.hasNext()) {
			if (tree.left != null) {
				boolean left = checkPostorder(tree.left, it);
				if (!left) {
					return false;
				}
			}
			if (tree.right != null) {
				boolean right = checkPostorder(tree.right, it);
				if (!right) {
					return false;
				}
			}
			if (!tree.value.equals(it.next())) {
				return false;
			}
			return true;
		} else {
			return false;
		}
	}

	public static <T> boolean checkPreorder(BinaryTree<T> tree, List<T> values) {
		if (tree == null || values == null) {
			return false;
		}
		return checkPreorder(tree, values.iterator());
	}

	private static <T> boolean checkPreorder(BinaryTree<T> tree, Iterator<T> it) {
		if (it.hasNext()) {
			if (!tree.value.equals(it.next())) {
				return false;
			}
			if (tree.left != null) {
				boolean left = checkPreorder(tree.left, it);
				if (!left) {
					return false;
				}
			}
			if (tree.right != null) {
				boolean right = checkPreorder(tree.right, it);
				if (!right) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}

	public static <T> boolean checkInorder(BinaryTree<T> tree, List<T> values) {
		if (tree == null || values == null) {
			return false;
		}
		return checkInorder(tree, values.iterator());
	}

	private static <T> boolean checkInorder(BinaryTree<T> tree, Iterator<T> it) {
		if (it.hasNext()) {
			if (tree.left != null) {
				boolean left = checkInorder(tree.left, it);
				if (!left) {
					return false;
				}
			}
			if (!tree.value.equals(it.next())) {
				return false;
			}
			if (tree.right != null) {
				boolean right = checkInorder(tree.right, it);
				if (!right) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}

	public static <T> BinaryTree<T> spanning(BinaryTree<T> tree, T value) {
		if (tree == null) {
			return null;
		}
		BinaryTree<T> answer = new BinaryTree<T>(tree.value, spanning(
				tree.left, value), spanning(tree.right, value));
		if (answer.left == null && answer.right == null) {
			if (answer.value.equals(value)) {
				return answer;
			} else {
				return null;
			}
		}
		return answer;
	}

	public static <T> boolean isPostOrderSorted(BinaryTree<T> tree,
			Comparator<? super T> cmp) {
		if (tree == null) {
			return true;
		}
		return isPostOrderSortedRec(tree, cmp, null);
	}

	private static <T> boolean isPostOrderSortedRec(BinaryTree<T> tree,
			Comparator<? super T> cmp, T value) {
		if (tree.left == null && tree.right == null) {
			if (value == null) {
				return true;
			} else {
				return cmp.compare(tree.value, value) >= 0;
			}
		} else {
			if (tree.left != null) {
				boolean left = isPostOrderSortedRec(tree.left, cmp, value);
				if (!left) {
					return false;
				}
				if (tree.right == null) {
					return true;
				} else {
					boolean right = isPostOrderSortedRec(tree.right, cmp,
							tree.left.value);
					if (!right) {
						return false;
					}
					return cmp.compare(tree.value, tree.right.value) >= 0;
				}
			} else {
				return isPostOrderSortedRec(tree.right, cmp, value);
			}
		}
	}

	public static <T> boolean isHeap(BinaryTree<T> tree,
			Comparator<? super T> cmp) {
		Queue<BinaryTree<T>> q = new LinkedList<BinaryTree<T>>();
		boolean aux = false;
		q.offer(tree);
		while (!q.isEmpty()) {
			BinaryTree<T> current = q.poll();
			if (aux) {
				if (current.left != null || current.right != null) {
					return false;
				}
			} else {
				if (current.left != null) {
					if (cmp.compare(current.value, current.left.value) > 0) {
						return false;
					}
					q.offer(current.left);
					if (current.right != null) {
						if (cmp.compare(current.value, current.right.value) > 0) {
							return false;
						}
					} else {
						aux = true;
					}
				} else {
					aux = true;
					if (current.right != null) {
						return false;
					}
				}
			}
		}
		return true;
	}

	public static <T> T commonAncestor(BinaryTree<T> tree, T value1, T value2) {
		if (tree == null) {
			return null;
		}
		if (tree.value.equals(value1) || tree.value.equals(value2)) {
			return tree.value;
		}
		T left = commonAncestor(tree.left, value1, value2);
		T right = commonAncestor(tree.right, value1, value2);
		if (left != null) {
			if (right != null) {
				return tree.value;
			} else {
				return left;
			}
		} else {
			if (right != null) {
				return right;
			} else {
				return null;
			}
		}
	}

}
