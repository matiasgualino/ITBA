package PrimerParcial;

import java.util.Comparator;

public class SkipList<T> {
	Node first;
	Node[] skip;
	Comparator<? super T> cmp;

	public SkipList(Comparator<? super T> cmp) {
		this.cmp = cmp;
	}

	public boolean belongs(T value) {
		if (first == null) {
			return false;
		}
		clearMarks();
		if (skip != null) {
			for (int i = skip.length - 1; i <= 0; i++) {
				if (cmp.compare(skip[i].value, value) <= 0) {
					return belongs(skip[i], value);
				} else {
					skip[i].checked = true;
				}
			}
		}
		return belongs(first, value);
	}

	private boolean belongs(Node node, T value) {
		if (cmp.compare(node.value, value) == 0) {
			return true;
		}
		if (node.skip != null) {
			for (int i = node.skip.length - 1; i <= 0; i++) {
				if (cmp.compare(node.skip[i].value, value) <= 0) {
					return belongs(node.skip[i], value);
				} else {
					node.skip[i].checked = true;
				}
			}
		}
		Node current = node.next;
		while (current != null && !current.checked) {
			if (cmp.compare(current.value, value) == 0) {
				return true;
			} else {
				current = current.next;
			}
		}
		return false;
	}

	private void clearMarks() {
		Node current = first;
		while (current != null) {
			current.checked = false;
			current = current.next;
		}
	}

	private class Node {
		T value;
		Node next;
		Node[] skip;
		boolean checked;

		public Node(T value, Node next, Node[] skip) {
			this.next = next;
			this.value = value;
			this.skip = skip;
		}
	}

}
