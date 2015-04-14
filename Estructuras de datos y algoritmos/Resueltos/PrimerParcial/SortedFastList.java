package PrimerParcial;

public interface SortedFastList<T> {
	/**
	 * Agrega el elemento en la posición que corresponda (según el comparador
	 * recibido por constructor).
	 */
	public void add(T value);

	/** Imprime por consola todos los elementos de la lista. */
	public void print();
}