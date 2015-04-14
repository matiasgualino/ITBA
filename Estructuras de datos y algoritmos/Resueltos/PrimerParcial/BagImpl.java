package PrimerParcial;

public class BagImpl<T> implements Bag<T> {
	private Node first;

	@Override
	public void add(T elem) {
		if (first == null) {
			first = new Node(elem, null);
			return;
		}
		Node current = first;
		Node previous = null;
		while (current != null) {
			if (current.value.equals(elem)) {
				current.cant = current.cant + 1;
				if (previous != null) {
					previous.next = current.next;
					add(current);
				}
				return;
			} else {
				previous = current;
				current = current.next;
			}
		}
		previous.next = new Node(elem, null);
		return;
	}

	private void add(Node node) {
		Node current = first;
		Node previous = null;
		while (current != null) {
			if (current.cant <= node.cant) {
				node.next = current;
				if (previous == null) {
					first = node;
				} else {
					previous.next = node;
				}
				return;
			} else {
				previous = current;
				current = current.next;
			}
		}
		previous.next = node;
		node.next = null;
		return;
	}

	@Override
	public void remove(T elem) {
		Node current = first;
		Node previous = null;
		while (current != null) {
			if (current.value.equals(elem)) {
				if (current.cant == 1) {
					if (previous == null) {
						first = current.next;
					} else {
						previous.next = current.next;
					}
					return;
				} else {
					current.cant = current.cant - 1;
					if (previous == null) {
						first = current.next;
					} else {
						previous.next = current.next;
					}
					add(current);
					return;
				}
			} else {
				previous = current;
				current = current.next;
			}
		}
		return;
	}

	@Override
	public void print() {
		Node current = first;
		while (current != null) {
			System.out.print(current.value + " (" + current.cant + ") ");
			current = current.next;
		}
		System.out.println("");
	}

	private class Node {
		int cant;
		T value;
		Node next;

		public Node(T value, Node next) {
			this.value = value;
			this.cant = 1;
			this.next = next;
		}
	}

}
