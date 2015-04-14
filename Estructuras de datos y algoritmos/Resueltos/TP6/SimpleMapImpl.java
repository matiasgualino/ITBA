package TP6;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class SimpleMapImpl<K, V> implements SimpleMap<K, V> {

	private Node<K, V>[] buckets;
	private float loadFactor;
	private int size;

	@SuppressWarnings("unchecked")
	public SimpleMapImpl(int initialCapacity, float loadFactor) {
		if (initialCapacity <= 0 || loadFactor <= 0) {
			throw new IllegalArgumentException("Invalid parameters.");
		}
		this.buckets = new Node[initialCapacity];
		this.loadFactor = loadFactor;
		this.size = 0;
	}

	@Override
	public void put(K key, V value) {
		int bucket = getBucketIndex(key);
		Node<K, V> node = buckets[bucket];
		while (node != null) {
			if (node.key.equals(key)) {
				node.value = value;
				return;
			}
			node = node.next;
		}
		buckets[bucket] = new Node<K, V>(key, value, buckets[bucket]);
		size++;
		if (size / (double) buckets.length > loadFactor) {
			resize(buckets.length * 2);
		}
	}

	@Override
	public V get(K key) {
		int bucket = getBucketIndex(key);
		Node<K, V> node = buckets[bucket];
		while (node != null) {
			if (node.key.equals(key)) {
				return node.value;
			}
			node = node.next;
		}
		return null;
	}

	@Override
	public void remove(K key) {
		int bucket = getBucketIndex(key);
		Node<K, V> current = buckets[bucket];
		Node<K, V> previous = null;
		while (current != null) {
			if (current.key.equals(key)) {
				if (previous == null) {
					buckets[bucket] = current.next;
				} else {
					previous.next = current.next;
				}
				size--;
				return;
			}
			previous = current;
			current = current.next;
		}
		return;
	}

	@Override
	public Set<K> keySet() {
		Set<K> aux = new HashSet<K>();
		for (Node<K, V> node : buckets) {
			while (node != null) {
				aux.add(node.key);
				node = node.next;
			}
		}
		return aux;
	}

	@Override
	public Collection<V> values() {
		ArrayList<V> aux = new ArrayList<V>();
		for (Node<K, V> node : buckets) {
			while (node != null) {
				aux.add(node.value);
				node = node.next;
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
		Node<K, V>[] oldBuckets = this.buckets;
		this.buckets = new Node[newCapacity];
		this.size = 0;
		for (Node<K, V> node : oldBuckets) {
			while (node != null) {
				put(node.key, node.value);
				node = node.next;
			}
		}
	}

	private int getBucketIndex(K key) {
		if (key == null) {
			throw new IllegalArgumentException("Null key not allowed.");
		}
		int hash = Math.abs(key.hashCode());
		return hash % buckets.length;
	}

	private static class Node<K, V> {
		K key;
		V value;
		Node<K, V> next;

		Node(K key, V value, Node<K, V> next) {
			this.key = key;
			this.value = value;
			this.next = next;
		}
	}

}
