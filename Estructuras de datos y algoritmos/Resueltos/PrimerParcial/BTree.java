package PrimerParcial;

import java.util.ArrayList;
import java.util.List;

public class BTree<T extends Comparable<? super T>> {
	private Node first;
	private int m;

	public BTree(int m) {
		this.first = new Node();
		this.m = m;
	}

	public List<T> range(T low, T upp) {
		List<T> answer = new ArrayList<T>();
		for (T value : first.elements) {
			if (value.compareTo(low) >= 0 && value.compareTo(upp) < 0) {
				answer.add(value);
				addAll(this.first.children, value, answer);
			}
			if(value.compareTo(upp) == 0)
				answer.add(value);
		}
		return answer;
	}

	private void addAll(List<Node> children, T value, List<T> answer) {
		// TODO Auto-generated method stub
		
	}

	private class Node {
		List<T> elements = new ArrayList<T>(m);
		List<Node> children = new ArrayList<Node>(m + 1);

		/*
		 * Otros metodos utiles
		 */

	}

}
