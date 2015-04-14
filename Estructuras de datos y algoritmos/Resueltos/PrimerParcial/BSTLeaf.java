package PrimerParcial;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BSTLeaf<T> {
	Comparator<? super T> cmp;
	Node first;
	int leafData;

	public BSTLeaf(int leafData, Comparator<? super T> cmp) {
		this.leafData = leafData;
		this.cmp = cmp;
	}

	public boolean belongs(T value) {
		return belongs(first, value);
	}

	@SuppressWarnings("unchecked")
	private boolean belongs(Node current, T value) {
		if (current.isData()) {
			for (T elem : ((DataNode) current).data) {
				if (cmp.compare(elem, value) == 0) {
					return true;
				}
			}
			return false;
		} else {
			if (cmp.compare(current.midValue, value) >= 0) {
				return belongs(current.left, value);
			} else {
				return belongs(current.right, value);
			}
		}
	}

	private class Node {
		T midValue;
		Node left;
		Node right;

		public Node(T midValue, Node left, Node right) {
			this.midValue = midValue;
			this.left = left;
			this.right = right;
		}

		public boolean isData() {
			return false;
		}
	}

	private class DataNode extends Node {
		List<T> data = new ArrayList<T>(leafData);
		int size;

		public DataNode() {
			super(null, null, null);
			this.size = 0;
		}

		public boolean isData() {
			return true;
		}
	}

}