package PrimerParcial;

import java.util.Comparator;

public class SortedListImpl<T> implements SortedList<T> {
	Node first;
	Node lastAdded;
	Comparator<? super T> cmp;

	public SortedListImpl(Comparator<? super T> cmp) {
		this.cmp = cmp;
		this.first = null;
		this.lastAdded = null;
	}

	@Override
	public void add(T elem) {
		if (first == null) {
			first = lastAdded = new Node(elem, null, null, null);
			return;
		}
		Node current = first;
		Node previous = null;
		while (current != null) {
			if (cmp.compare(current.value, elem) <= 0) {
				previous = current;
				current = current.next;
			} else {
				if (previous == null) {
					Node aux = new Node(elem, first, null, lastAdded);
					first = lastAdded = aux;
					return;
				} else {
					Node aux = new Node(elem, current, previous, lastAdded);
					previous.next = aux;
					lastAdded = aux;
					return;
				}
			}
		}
		Node aux = new Node(elem, null, previous, lastAdded);
		previous.next = aux;
		lastAdded = aux;
		return;
	}

	@Override
	public void undo() {
		if (first != null) {
			if (lastAdded == null) {
				first = null;
				return;
			}
			Node aux = lastAdded;
			lastAdded = aux.lastAdded;
			if (aux.previous == null){
				first = aux.next;
			}else{
				aux.previous.next = aux.next;
			}
			if(aux.next != null){
				aux.next.previous = aux.previous;
			}
		}
	}

	@Override
	public void print() {
		Node current = first;
		while (current != null) {
			System.out.print(current.value + " ");
			current = current.next;
		}
		System.out.println("");
	}

	private class Node {
		T value;
		Node next;
		Node previous;
		Node lastAdded;

		public Node(T value, Node next, Node previous, Node lastAdded) {
			this.next = next;
			this.previous = previous;
			this.value = value;
			this.lastAdded = lastAdded;
		}

	}
}
