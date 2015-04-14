package TP2;

import utilities.Chronometer;

public class RoundRobin {

	public static final Double QUANTUM = 0.0001;
	private Node first = null;
	private int size = 0;
	private int process = 0;
	private Node planified = null;

	public void add(Double elem) {
		Node aux = new Node(elem, process++);
		if (first != null) {
			aux.next = first;
			aux.previous = first.previous;
			aux.previous.next = aux;
			first.previous = aux;
		} else {
			aux.next = aux.previous = aux;
		}
		first = aux;
		size++;
	}

	public void remove(Node node) {
		if (size == 1) {
			first = null;
			size--;
		} else {
			node.next.previous = node.previous;
			node.previous.next = node.next;
			if (node.equals(first))
				first = node.next;
			size--;
		}
	}

	public void planifier() {
		if (planified == null) {
			planified = first;
		}
		System.out.println("Node: " + planified.process + "Time: " + planified.value);
		if (planified.value <= QUANTUM) {
			planified = planified.next;
			remove(planified.previous);
		} else {
			planified.value = planified.value - QUANTUM;
			planified = planified.next;
		}
	}

	private class Node {

		Double value;
		int process;
		Node next;
		Node previous;

		public Node(Double value, int process) {
			this.value = value;
			this.process = process;
		}

	}

	public static void main(String[] args) {

		Double totalTime = Double.parseDouble(args[0]);
		Double newWorkTime = Double.parseDouble(args[1]);
		RoundRobin robin = new RoundRobin();
		Chronometer ch = new Chronometer();
		ch.start();
		while (newWorkTime <= ch.getSeconds()) {
			robin.add(Math.random());
		}
		ch.start();
		while (totalTime < ch.getSeconds()) {
			robin.planifier();
		}
	}

}
