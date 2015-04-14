package PrimerParcial;

public interface Bag<T> {
	/** Agrega un elemento a la colección. */
	public void add(T elem);

	/** Elimina un elemento de la colección. */
	public void remove(T elem);

	/**
	 * Imprime los elementos sin repetir, indicando la cantidad de veces que
	 * aparece cada uno, y ordenado descendentemente por dicha cantidad.
	 */
	public void print();
}
