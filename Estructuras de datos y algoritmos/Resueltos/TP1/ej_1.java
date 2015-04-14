package TP1;

import utilities.Chronometer;
import utilities.ArraySorting;

public class ej_1 {

	public static void main(String[] args) {
		int n = Integer.decode(args[0]);
		Double[] list = new Double[n];
		Chronometer ch = new Chronometer();
		for (int i = 0; i < n; i++) {
			list[i] = Math.random();
		}
		for(Double d : list)
			System.out.print(d + " - ");
		System.out.println("");
		ch.start();
		ArraySorting.bubbleSort(list);
		ch.stop();
		for(Double d : list)
			System.out.print(d + " - ");
		System.out.println("");
		System.out.println(ch.getSeconds());
	}
	/*
	 * La complejidad para la creacion del array es: n, n(n-1) = n^2-n para el
	 * bubble sort --> La complejidad es n^2, el orden es O(n^2)
	 */
}
