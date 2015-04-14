package PrimerParcial;

public class Stack<T> {
	private Node first;

	public Stack() {
		this.first = null;
	}

	public void push(T value) {
		Node aux = new Node(value, first);
		first = aux;
	}

	public T pop() {
		if (first == null) {
			return null;
		}
		Node aux = first;
		first = aux.next;
		return aux.value;
	}

	public boolean isEmpty() {
		return first == null;
	}

	private class Node {
		T value;
		Node next;

		public Node(T value, Node next) {
			this.value = value;
			this.next = next;
		}
	}

}
