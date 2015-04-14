package TP5;

import java.util.Comparator;

public class ej_3 {
	public static void main(String[] args) {
		Integer[] data = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 };
		BinaryTreeRoot<Integer> tree = new BinaryTreeRoot<>();
		Comparator<Integer> cmp = new Comparator<Integer>() {
			public int compare(Integer o1, Integer o2) {
				return o1 - o2;
			}
		};
		addRecursive(data,0,data.length-1,tree,cmp);
		
		print(tree);
	}

	private static void addRecursive(Integer[] data, int from, int to, BinaryTreeRoot<Integer> tree, Comparator<Integer> cmp) {
		if(from > to)
			return;
		if(from==to)
			tree.add(data[from], cmp);
		
		int mid = from+((to-from)/2);
		tree.add(data[mid], cmp);
		addRecursive(data, from, mid-1, tree, cmp);
		addRecursive(data, mid+1, to, tree, cmp);
	}
	
	private static void print(BinaryTreeRoot<Integer> tree){
		if(tree==null)
			return;
		printRecursive(tree.getRoot());
	}

	private static void printRecursive(BinaryTree<Integer> tree) {
		if(tree==null)
			return;
		System.out.println(tree.getData());
		printRecursive(tree.getLeft());
		printRecursive(tree.getRight());
	}
}
