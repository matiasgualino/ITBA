package PrimerParcial;

public interface CircularList<T> {
	/**
	 * Agrega un elemento al final de la lista.
	 */
	public void addLast(T elem);

	/**
	 * Permite iterar por los elementos de la lista. La primera vez que se lo
	 * invoca retorna el primer elemento, luego el segundo y así sucesivamente.
	 * Luego de retornar el último, la próxima invocación vuelve a retornar el
	 * primero. Lanza NoSuchElementException únicamente cuando la lista está
	 * vacía.
	 */
	public T getNext();

	/**
	 * Hace que la próxima invocación a getNext retorne el primer elemento de la
	 * lista.
	 */
	public void reset();

	/**
	 * Corta la lista en la posición actual. Esta lista (this) mantiene todos
	 * los elementos desde el primero hasta el último retornado por getNext().
	 * Los elementos restantes son almacenados en una nueva lista, que es
	 * retornada por este método. Lanza IllegalStateException si nunca se invocó
	 * a getNext antes de llamar a este método.
	 */
	public CircularList<T> split();
}
