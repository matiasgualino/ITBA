package PrimerParcial;

import java.util.NoSuchElementException;

public class CircularListImpl<T> implements CircularList<T> {
	Node first;
	Node last;
	Node current;
	Node previous;
	boolean canSplit;

	public CircularListImpl() {
		this.first = null;
		this.last = null;
		this.current = null;
		this.previous = null;
		this.canSplit = false;
	}

	private CircularListImpl(Node first, Node last) {
		this.first = first;
		this.current = first;
		this.previous = null;
		this.last = last;
		this.canSplit = false;
	}

	@Override
	public void addLast(T elem) {
		Node aux = new Node(elem, null);
		if (first == null) {
			first = current = last = aux;
			aux.next = aux;
			return;
		} else {
			aux.next = last.next;
			last.next = aux;
			last = aux;
			return;
		}
	}

	@Override
	public T getNext() {
		if (first == null) {
			throw new NoSuchElementException();
		}
		T answer = current.value;
		if (current.next.equals(first)) {
			canSplit = false;
		} else {
			canSplit = true;
		}
		previous = current;
		current = current.next;
		return answer;
	}

	@Override
	public void reset() {
		current = first;
		canSplit = false;
	}

	@Override
	public CircularList<T> split() {
		if (canSplit) {
			Node newLast = previous;
			Node first2 = current;
			Node last2 = last;
			newLast.next = first;
			last2.next = first2;
			last = newLast;
			current = first;
			canSplit = false;
			return new CircularListImpl<T>(first2, last2);
		} else {
			return null;
		}
	}

	private class Node {
		Node next;
		T value;

		public Node(T value, Node next) {
			this.value = value;
			this.next = next;
		}
	}

}
