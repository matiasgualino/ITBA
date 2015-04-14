package TP1;

public class ej_2<T extends Number> {

	public static <T extends Comparable<? super T>> boolean isSorted(T[] array) {
		/*
		 * La complejidad es: (n-1). El orden es O(n)
		 */
		int len = array.length;
		for (int i = 0; i < len - 1; i++) {
			if (array[i].compareTo(array[i + 1]) > 0) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		Double[] array = { 1.0, 2.0, 3.0, 4.0, 5.0 };
		System.out.println(isSorted(array));
	}

}
