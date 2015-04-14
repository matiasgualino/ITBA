package TP5;

import java.util.Comparator;

public class ej_2 {
	public static void main(String[] args) {
		BinaryTreeRoot<Integer> tree1 = new BinaryTreeRoot<Integer>();
		BinaryTreeRoot<Integer> tree2 = new BinaryTreeRoot<Integer>();
		Comparator<Integer> cmp = new Comparator<Integer>() {
			public int compare(Integer o1, Integer o2) {
				return o1 - o2;
			}
		};

		tree1.add(10, cmp);
		tree1.add(5, cmp);
		tree1.add(4, cmp);
		tree1.add(8, cmp);
		tree1.add(7, cmp);
		tree1.add(9, cmp);
		tree1.add(15, cmp);

		tree2.add(10, cmp);
		tree2.add(5, cmp);
		tree2.add(20, cmp);
		tree2.add(30, cmp);
		tree2.add(15, cmp);
		tree2.add(12, cmp);
		tree2.add(17, cmp);

		System.out.println(mirrorTrees(tree1, tree2));
	}

	private static boolean mirrorTrees(BinaryTreeRoot<Integer> tree1,
			BinaryTreeRoot<Integer> tree2) {
		return mirrorTreesRecursive(tree1.getRoot(), tree2.getRoot());
	}

	private static boolean mirrorTreesRecursive(BinaryTree<Integer> tree1,
			BinaryTree<Integer> tree2) {
		if (tree1 == null && tree2 == null)
			return true;
		if ((tree1 == null && tree2 != null)
				|| (tree1 != null && tree2 == null))
			return false;
		else
			return mirrorTreesRecursive(tree1.getLeft(), tree2.getRight())
					&& mirrorTreesRecursive(tree1.getRight(), tree2.getLeft());
	}
}
