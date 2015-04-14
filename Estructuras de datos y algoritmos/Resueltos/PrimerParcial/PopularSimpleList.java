package PrimerParcial;

import java.util.Comparator;

public class PopularSimpleList<S, T> {
	private Node first;
	private int size;
	private Comparator<S> cmp;

	public PopularSimpleList(Comparator<S> cmp) {
		this.cmp = cmp;
	}

	public void add(S key, T value) {
		if (first == null) {
			first = new Node(key, value, null);
			size++;
			return;
		} else {
			Node current = first;
			Node previous = null;
			while (current != null) {
				previous = current;
				current = current.next;
			}
			previous.next = new Node(key, value, null);
			size++;
		}
	}

	public T consult(S key) {
		Node current = first;
		Node previous = null;
		while (current != null) {
			if (cmp.compare(current.key, key) == 0) {
				previous.next = current.next;
				current.next = first;
				first = current;
				return current.value;
			}
			previous = current;
			current = current.next;
		}
		return null;
	}

	private class Node {
		S key;
		T value;
		Node next;

		public Node(S key, T value, Node next) {
			this.key = key;
			this.value = value;
			this.next = next;
		}
	}

}
