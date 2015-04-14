package PrimerParcial;

import java.util.Comparator;

public class SortedFastListImpl<T> implements SortedFastList<T> {

	Node first;
	Comparator<? super T> cmp;

	public SortedFastListImpl(Comparator<? super T> cmp) {
		this.first = null;
		this.cmp = cmp;
	}

	@Override
	public void add(T value) {
		if (first == null) {
			first = new Node(value, null, null);
		}
		Node current = first;
		Node previous = null;
		while (current != null) {
			int comparision = cmp.compare(current.info, value);
			if (comparision == 0) {
				return;
			}
			if (comparision > 0) {
				if (previous == null) {
					first = new Node(value, first, first.next);
					return;
				}
				if (previous.next.equals(current)
						|| cmp.compare(previous.next.info, value) > 0) {
					Node aux = new Node(value, previous.next,
							previous.next.next);
					previous.next = aux;
					previous.second = current;
					return;
				} else {
					Node aux = new Node(value, current, current.next);
					previous.next.next = aux;
					previous.second = aux;
					previous.next.second = current;
					return;
				}
			}
			if (comparision < 0) {
				previous = current;
				if (current.second != null) {
					current = current.second;
				}
				else if (current.next != null) {
					current = current.next;
				}
				else if (current.next == null) {
					current.next = new Node(value, null, null);
					return;
				}
			}
		}
	}

	@Override
	public void print() {
		Node current = first;
		while (current != null) {
			System.out.print(current.info + ", ");
			current = current.next;
		}
	}

	private class Node {
		T info;
		Node next;
		Node second;

		public Node(T info, Node next, Node second) {
			this.info = info;
			this.next = next;
			this.second = second;
		}
	}

}
