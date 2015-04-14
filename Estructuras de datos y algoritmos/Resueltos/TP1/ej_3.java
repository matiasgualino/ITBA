package TP1;

import utilities.Chronometer;

public class ej_3<T> {

	public static <T> boolean hasDoubles(T[] array) {
		/*
		 * La complejidad es: n!. El orden es O(n!)
		 */
		int len = array.length;
		for (int i = 0; i < len; i++) {
			for (int j = i + 1; j < len; j++) {
				if (array[i].equals(array[j])) {
					return true;
				}
			}
		}
		return false;
	}

	public static <T extends Comparable<? super T>> boolean hasDoubles2(
			T[] array) {
		/*
		 * Version para arrays ordenados La complejidad es: n-1. El orden es
		 * O(n)
		 */
		int len = array.length;
		for (int i = 0; i < len - 1; i++) {
			if (array[i].compareTo(array[i + 1]) == 0)
				return true;
		}
		return false;
	}

	public static void main(String[] args) {
		Chronometer ch = new Chronometer();
		Double[] array = { 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0,
				11.0 };
		ch.start();
		System.out.println(hasDoubles(array));
		ch.stop();
		System.out.println(ch.getSeconds());
		ch.start();
		System.out.println(hasDoubles2(array));
		ch.stop();
		System.out.println(ch.getSeconds());
	}

}
