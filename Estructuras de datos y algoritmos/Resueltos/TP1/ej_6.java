package TP1;

import java.util.Arrays;

public class ej_6 {

	public static <T extends Comparable<? super T>> T[] inter(T[] vec1, T[] vec2) {
		T[] ret = Arrays.copyOf(vec1, vec1.length);
		int count = 0;
		int i1 = 0, i2 = 0;
		while (i1 < vec1.length && i2 < vec2.length) {
			int cmp = vec1[i1].compareTo(vec2[i2]);
			if (cmp < 0) {
				i1++;
			} else if (cmp > 0) {
				i2++;
			} else {
				ret[count++] = vec1[i1++];
				i2++;
			}
		}
		return Arrays.copyOf(ret, count);
	}

	public static void main(String[] args) {
		
		

	}

}
