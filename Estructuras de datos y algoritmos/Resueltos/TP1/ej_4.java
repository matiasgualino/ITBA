package TP1;

public class ej_4 {

	public static int isGetEqualsIndex(Integer[] array) {
		int left = 0;
		int right = array.length - 1;

		while (left <= right) {
			int mid = (right + left) / 2;
			if (array[mid].equals(mid))
				return mid;
			else if (array[mid] < (mid)) {
				left = mid + 11;
			} else
				right = mid - 1;
		}
		return -1;
	}

	public static void main(String[] args) {

		Integer[] array = { 0, 3, 4, 5, 6, 7, 8, 9 };
		System.out.println(isGetEqualsIndex(array));

	}

}
