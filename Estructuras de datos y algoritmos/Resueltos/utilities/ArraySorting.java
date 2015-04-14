package utilities;

import java.util.Arrays;

public abstract class ArraySorting<T extends Comparable<? super T>> {

	public static <T extends Comparable<? super T>> void bubbleSort(T[] list) {
		for (int i = 0; i < list.length - 1; i++) {
			for (int j = 0; j < list.length - 1; j++) {
				if (list[j].compareTo(list[j + 1]) > 0) {
					T aux = list[j];
					list[j] = list[j + 1];
					list[j + 1] = aux;
				}
			}
		}
	}

	public static <T extends Comparable<? super T>> void selectionSort(T[] list) {
		T min;
		int idx;
		for (int i = 0; i < list.length - 1; i++) {
			min = list[i];
			idx = i;
			for (int j = i + 1; j < list.length; j++) {
				if (list[j].compareTo(min) < 0) {
					min = list[j];
					idx = j;
				}
			}
			swap(list, i, idx);
		}
	}

	public static <T extends Comparable<? super T>> void selectionSort2(T[] list) {
		T min;
		T max;
		int minIdx;
		int maxIdx;
		int i = 0, k = list.length - 1;
		while (k - i > 0) {
			min = list[i];
			minIdx = i;
			max = list[k];
			maxIdx = k;
			for (int j = i; j <= k; j++) {
				if (list[j].compareTo(min) < 0) {
					swap(list,j,minIdx);
				} else if (list[j].compareTo(max) > 0) {
					swap(list,j,maxIdx);
				}
			}
			i++;
			k--;
		}
	}

	public static <T extends Comparable<? super T>> void mergeSort(T[] list) {
		T[] aux = Arrays.copyOfRange(list, 0, list.length);
		mergeAux(list, aux, 0, list.length - 1);
	}

	private static <T extends Comparable<? super T>> void mergeAux(T[] list,
			T[] aux, int left, int right) {
		int size = right - left + 1;
		if (size <= 1) {
			return;
		}
		int n = left + size / 2;
		mergeAux(list, aux, left, n - 1);
		mergeAux(list, aux, n, right);
		int i1 = left, i2 = n, i = left;
		while (i1 <= n - 1 || i2 <= right) {
			if (i2 > right
					|| (i1 <= n - 1 && list[i1].compareTo(list[i2]) <= 0)) {
				aux[i++] = list[i1++];
			} else {
				aux[i++] = list[i2++];
			}
		}
		for (int k = left; k <= right; k++) {
			list[k] = aux[k];
		}
	}

	public static <T extends Comparable<? super T>> void quickSort(T[] values) {
		quicksortAux(values, 0, values.length - 1);
	}

	private static <T extends Comparable<? super T>> void quicksortAux(
			T[] values, int from, int to) {
		int size = to - from + 1;
		if (size <= 1) {
			return;
		}
		int k = from;
		int pivot = (int) (Math.random() * size) + from;
		swap(values, pivot, from);
		for (int i = from + 1; i <= to; i++) {
			if (values[i].compareTo(values[from]) < 0) {
				swap(values, ++k, i);
			}
		}
		swap(values, k, from);
		quicksortAux(values, from, k - 1);
		quicksortAux(values, k + 1, to);
	}

	private static <T extends Comparable<? super T>> void swap(T[] values,
			int a, int b) {
		T aux = values[a];
		values[a] = values[b];
		values[b] = aux;
	}
}
