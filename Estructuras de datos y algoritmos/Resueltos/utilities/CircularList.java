package utilities;

public class CircularList<T> {

	private Node<T> first;
	private int size;

	public CircularList() {
		this.size = 0;
	}

	public void add(T elem) {
		Node<T> aux = new Node<T>(elem);
		if (first != null) {
			aux.next = first;
			aux.previous = first.previous;
			aux.previous.next = aux;
			first.previous = aux;
		} else {
			aux.next = aux.previous = aux;
		}
		first = aux;
		size++;
	}

	private class Node<T> {

		T value;
		Node<T> next;
		Node<T> previous;

		public Node(T value) {
			this.value = value;
		}

		public Node(T value, Node<T> next, Node<T> previous) {
			this.value = value;
			this.next = next;
			this.previous = previous;
		}

	}

}
