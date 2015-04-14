package PrimerParcial;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Test {

	public static void main(String[] args) {

		// CustomMapImpl<String, String> m = new CustomMapImpl<String,
		// String>();
		// System.out.println(m.get("k1")); // imprime null
		// m.put("k1", "v1");
		// m.put("k2", "v2");
		// m.put("k3", "v3");
		// System.out.println(m.get("k2")); // imprime v2
		// System.out.println(m.getMostAccessed()); // imprime k2
		// System.out.println(m.get("k1")); // imprime v1
		// System.out.println(m.get("k2")); // imprime v2
		// m.removeLeastAccessed();
		// m.removeLeastAccessed();
		// m.put("k4", "v4");
		// m.put("k4", "v5");
		// m.put("k4", "v6");
		// System.out.println(m.get("k4")); // imprime v6
		// System.out.println(m.getMostAccessed()); // imprime k4
		// m.removeLeastAccessed();
		// m.removeLeastAccessed();

		// Bag<String> bag = new BagImpl<String>();
		// bag.print(); // No imprime nada.
		// bag.add("A"); bag.add("B"); bag.add("C"); bag.add("B");
		// bag.print(); // Imprime B (2) C (1) A (1)
		// bag.add("A"); bag.add("C"); bag.add("C");
		// bag.print(); // Imprime C (3) A (2) B (2)
		// bag.remove("C"); bag.add("D");
		// bag.print(); // Imprime C (2) A (2) B (2) D (1)
		// bag.remove("C");
		// bag.print(); // Imprime A (2) B (2) C (1) D (1)
		// bag.remove("C");
		// bag.print(); // Imprime A (2) B (2) D (1)

		// CircularList<String> list1 = new CircularListImpl<String>();
		// list1.addLast("A");
		// list1.addLast("B");
		// list1.addLast("C");
		// print(list1); // imprime "A B C A B C A B C A "
		// list1.addLast("D");
		// list1.addLast("E");
		// list1.addLast("F");
		// print(list1); // imprime "B C D E F A B C D E "
		// list1.reset();
		// list1.getNext(); // A
		// list1.getNext(); // B
		// list1.getNext(); // C
		// CircularList<String> list2 = list1.split();
		// print(list1); // imprime "A B C A B C A B C A "
		// print(list2); // imprime "D E F D E F D E F D "
		// list2.reset();
		// list2.getNext();
		// list2.getNext();
		// list2.getNext();
		// CircularList<String> list3 = list2.split();
		// print(list2); // imprime "D E F D E F D E F D "
		// print(list3); // Lanza NoSuchElementException porque la lista está
		// vacía

		// BST<Integer> bst = new BST<Integer>(new Comparator<Integer>() {
		// public int compare(Integer o1, Integer o2) {
		// return o1.compareTo(o2);
		// }
		// });
		// bst.add(100);
		// bst.add(50);
		// bst.add(250);
		// bst.add(30);
		// bst.add(70);
		// bst.add(10);
		// bst.add(40);
		// bst.add(270);
		// bst.add(260);
		// for (Integer i : bst.getInOrder(50, 60)) {
		// System.out.print(i + " ");
		// }

		// BinaryTree<String> f = new BinaryTree<String>("F", null, null);
		// BinaryTree<String> c = new BinaryTree<String>("C", null, f);
		// BinaryTree<String> e = new BinaryTree<String>("E", null, null);
		// BinaryTree<String> d = new BinaryTree<String>("D", null, null);
		// BinaryTree<String> b = new BinaryTree<String>("B", d, e);
		// BinaryTree<String> a = new BinaryTree<String>("A", b, c);
		// List<String> list = new ArrayList<String>();
		// list.add("D");
		// list.add("B");
		// list.add("E");
		// list.add("A");
		// list.add("C");
		// list.add("F");
		// System.out.println(BinaryTree.checkInorder(a, list));

		// BST<Integer> bst = new BST<Integer>(new Comparator<Integer>() {
		// public int compare(Integer o1, Integer o2) {
		// return o1.compareTo(o2);
		// }
		// });
		// bst.add(5);
		// bst.add(3);
		// bst.add(4);
		// bst.add(2);
		// bst.add(7);
		// bst.remove(7);
		// bst.BFSPrint();

		// SortedList<String> list = new SortedListImpl<String>(
		// new Comparator<String>() {
		// public int compare(String o1, String o2) {
		// return o1.compareTo(o2);
		// }
		// });
		// list.add("C");
		// list.add("A");
		// list.add("D");
		// list.add("B");
		// list.print(); // imprime "A B C D"
		// list.undo();
		// list.print(); // imprime "A C D"
		// list.undo();
		// list.undo();
		// list.print(); // imprime "C"
	}

	private static <T> void print(CircularList<T> list) {
		for (int i = 0; i < 10; i++) {
			System.out.print(list.getNext() + " ");
		}
		System.out.println();
	}

}
