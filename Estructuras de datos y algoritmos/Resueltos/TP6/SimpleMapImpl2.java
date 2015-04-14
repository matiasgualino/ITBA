package TP6;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SimpleMapImpl2<K, V> implements SimpleMap<K, V> {

	private Node<K, V>[] array;
	private int size;
	private float loadFactor;

	@SuppressWarnings("unchecked")
	public SimpleMapImpl2(int initialCapacity, float loadFactor) {
		if (initialCapacity <= 0 || loadFactor <= 0) {
			throw new IllegalArgumentException("Invalid parameters.");
		}
		this.array = new Node[initialCapacity];
		this.size = 0;
		this.loadFactor = loadFactor;
	}

	@Override
	public void put(K key, V value) {
		int position = getIndex(key);
		int position2 = position;
		Node<K,V> aux = new Node<K,V>(key,value);
		Node<K, V> current = array[position];
		while (current != null) {
			if (current.key.equals(key)) {
				current.value=value;
				return;
			}
			position2++;
			if (position2 == position) {
				resize(array.length*2);
				put(key,value);
				return;
			}
			if (position2 == array.length) {
				position2 = 0;
				current = array[position2];
			} else {
				current = array[position2];
			}
		}
		array[position2] = aux;
	}

	@Override
	public V get(K key) {
		int position = getIndex(key);
		int position2 = position;
		Node<K, V> current = array[position];
		while (current != null) {
			if (current.key.equals(key)) {
				return current.value;
			}
			position2++;
			if (position2 == position) {
				return null;
			}
			if (position2 == array.length) {
				position2 = 0;
				current = array[position2];
			} else {
				current = array[position2];
			}
		}
		return null;
	}

	@Override
	public void remove(K key) {
		int position = getIndex(key);
		int position2 = position;
		Node<K, V> current = array[position];
		while (current != null) {
			if (current.key.equals(key)) {
				array[position2] = null;
				return;
			}
			position2++;
			if (position2 == position) {
				return;
			}
			if (position2 == array.length) {
				position2 = 0;
				current = array[position2];
			} else {
				current = array[position2];
			}
		}
	}

	@Override
	public Set<K> keySet() {
		Set<K> aux = new HashSet<K>();
		for (Node<K, V> node : array) {
			if (node != null) {
				aux.add(node.key);
			}
		}
		return aux;
	}

	@Override
	public Collection<V> values() {
		List<V> aux = new ArrayList<V>();
		for (Node<K, V> node : array) {
			if (node != null) {
				aux.add(node.value);
			}
		}
		return aux;
	}

	@Override
	public int size() {
		return size;
	}

	@SuppressWarnings("unchecked")
	private void resize(int newCapacity) {
		Node<K, V>[] oldArray = this.array;
		this.array = new Node[newCapacity];
		this.size = 0;
		for (Node<K, V> node : oldArray) {
			if (node != null) {
				put(node.key, node.value);
			}
		}
	}

	private int getIndex(K key) {
		if (key == null) {
			throw new IllegalArgumentException("Null key not allowed.");
		}
		int hash = Math.abs(key.hashCode());
		return hash % array.length;
	}

	private static class Node<K, V> {
		K key;
		V value;

		Node(K key, V value) {
			this.key = key;
			this.value = value;
		}
	}

}
