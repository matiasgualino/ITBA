package TP2;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class CustomSet<T> implements Set<T> {

	private Node<T> first;
	private int size;

	public CustomSet() {
		this.size = 0;
		this.first = null;
	}

	@Override
	public int size() {
		return this.size;
	}

	@Override
	public boolean isEmpty() {
		return this.first == null;
	}

	@Override
	public boolean contains(Object o) {
		Node<T> aux = first;
		while (aux.tail != null) {
			if (aux.head == o)
				return true;
			aux = aux.tail;
		}
		return false;
	}

	@Override
	public Iterator<T> iterator() {
		CustomSetIterator<T> it = new CustomSetIterator<T>();
		return it;
	}

	private class CustomSetIterator<T> implements Iterator<T> {

		int operations;
		Node<T> current;
		Node<T> previous;
		Node<T> previous2;

		public CustomSetIterator() {
			this.current = null;
			this.previous = null;
			this.previous2 = null;
			this.operations = 0;
		}

		@Override
		public boolean hasNext() {
			if (operations == 0) {
				current = (Node<T>) first;
				return current != null;
			} else
				return current.tail != null;
		}

		@Override
		public T next() {
			if (hasNext()) {
				operations++;
				previous2 = previous;
				previous = current;
				current = current.tail;
				return previous.head;
			} else
				return null;
		}

		@Override
		public void remove() {
			previous2.tail = current;
			operations++;
		}

	}

	@Override
	public Object[] toArray() {
		Object[] aux = new Object[size];
		Node<T> current = first;
		int idx = 0;
		while (current != null) {
			aux[idx++] = current.head;
			current = current.tail;
		}
		return aux;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		int idx = 0;
		Node<T> current = (Node<T>) first;
		if (a.length < size)
			return (T[]) toArray();
		else if (a.length > size) {
			a[size] = null;
		}
		while (current != null) {
			a[idx++] = current.head;
			current = current.tail;
		}
		return a;
	}

	@Override
	public boolean add(T e) {
		this.first = new Node<T>(e, this.first);
		this.size++;
		return true;
	}

	@Override
	public boolean remove(Object o) {
		Node<T> current = first;
		Node<T> previous = null;
		while (current != null) {
			if (current.head == o) {
				previous.tail = current.tail;
				current.tail = null;
				this.size--;
				return true;
			}
			previous = current;
			current = current.tail;
		}
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		boolean flag = true;
		for (Object o : c) {
			if (!contains(o)) {
				flag = false;
				break;
			}
		}
		return flag;
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		for (T elem : c) {
			add(elem);
		}
		return true;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		Node<T> aux = null;
		for (Object elem : c) {
			if (contains(elem))
				aux = new Node<T>((T) c, aux);
		}
		this.first = aux;
		return true;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		for (Object elem : c) {
			remove(elem);
		}
		return true;
	}

	@Override
	public void clear() {
		this.first = null;
	}

	private static class Node<T> {
		T head;
		Node<T> tail;

		public Node(T head, Node<T> tail) {
			this.head = head;
			this.tail = tail;
		}
	}

}
