package PrimerParcial;

public interface CustomMap<K, V> {

	public V get(K key);
	
	public void put(K key, V value);
	
	public K getMostAccessed();
	
	public void removeLeastAccessed();
	
}
