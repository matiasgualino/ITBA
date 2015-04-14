package TP4;

public class ej_1 {

	public static void main(String[] args) {

		int n = 10000;
		int[] array = new int[n];
		for (int i = 0; i < n; i++) {
			array[i] = (int) (Math.random() * 10000);
		}
		for (int i : array) {
			System.out.print(i + ",");
		}
		System.out.println("");
		sort(array);
		for (int i : array) {
			System.out.print(i + ",");
		}
	}

	private static void sort(int[] values) {
		int[] aux = new int[10000];
		int k = 0;
		for (int value : values) {
			aux[value]++;
		}
		for (int i = 0; i < aux.length; i++) {
			while (aux[i] > 0) {
				values[k++] = i;
				aux[i]--;
			}
		}
	}
}
