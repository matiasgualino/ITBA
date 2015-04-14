package TP4;

public class ej_2 {
	public static void main(String[] args) {
		int[] sizes = new int[] { 50000, 100000, 200000, 400000, 800000 };
		long start, time;
		for (int size : sizes) {
			start = System.currentTimeMillis();
			quicksort(createRandomArray(size));
			time = System.currentTimeMillis() - start;
			System.out.println("Size: " + size + " Time: " + time + " ms");
		}
	}

	public static int[] createRandomArray(int size) {
		int[] ret = new int[size];
		for (int i = 0; i < size; i++) {
			ret[i] = (int) (Math.random() * 10000);
		}
		return ret;
	}

	public static void quicksort(int[] values) {
		quicksort(values, 0, values.length - 1);
	}

	private static void quicksort(int[] values, int from, int to) {
		int size = to - from + 1;
		if (size <= 1) {
			return;
		}
		int k = from;
		int pivot = (int) (Math.random() * size) + from;
		swap(values, pivot, from);
		for (int i = from + 1; i <= to; i++) {
			if (values[i] < values[from]) {
				swap(values, ++k, i);
			}
		}
		swap(values, k, from);
		quicksort(values, from, k - 1);
		quicksort(values, k + 1, to);
	}
	
	private static void swap(int[] values, int a, int b){
		int aux = values[a];
		values[a] = values[b];
		values[b] = aux;
	}
}
