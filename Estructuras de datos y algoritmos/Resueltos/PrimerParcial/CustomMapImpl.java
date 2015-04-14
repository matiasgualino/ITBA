package PrimerParcial;

public class CustomMapImpl<K, V> implements CustomMap<K, V> {
	Node first;
	Node last;

	@Override
	public V get(K key) {
		Node current = first;
		while (current != null) {
			if (current.key.equals(key)) {
				current.accessed++;
				if (current.equals(first)) {
					return current.value;
				} else {
					current.previous.next = current.next;
					current.next.previous = current.previous;
					add(current);
					return current.value;
				}
			} else {
				current = current.next;
			}
		}
		return null;
	}

	@Override
	public void put(K key, V value) {
		if (first == null) {
			first = new Node(key, value, null, null);
		} else {
			Node current = first;
			Node previous = null;
			while (current != null) {
				if (current.key.equals(key)) {
					current.value = value;
					current.accessed++;
					current.previous.next = current.next;
					if (current.next != null) {
						current.next.previous = current.previous;
					}
					add(current);
					return;
				} else {
					previous = current;
					current = current.next;
				}
			}
			previous.next = new Node(key, value, null, previous);
		}
	}

	private void add(Node node) {
		Node current = first;
		while (current != null) {
			if (current.accessed <= node.accessed) {
				if (current.equals(first)) {
					first = node;
					node.previous = null;
					node.next = current;
					current.previous = node;
					return;
				} else {
					node.previous = current.previous;
					node.next = current;
					current.previous.next = node;
					current.previous = node;
					return;
				}
			} else {
				current = current.next;
			}
		}
		return;
	}

	@Override
	public K getMostAccessed() {
		if (first != null) {
			return first.key;
		} else {
			return null;
		}
	}

	@Override
	public void removeLeastAccessed() {
		if (last != null) {
			if (last.previous == null) {
				first = last = null;
			}
			last = last.previous;
		}
	}

	private class Node {
		K key;
		V value;
		int accessed;
		Node next;
		Node previous;

		public Node(K key, V value, Node next, Node previous) {
			this.accessed = 0;
			this.key = key;
			this.value = value;
			this.next = next;
			this.previous = previous;
		}
	}

}
