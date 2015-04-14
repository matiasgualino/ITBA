package SegundoParcial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Automata {
	private HashMap<String, Node> nodes = new HashMap<String, Node>();
	private List<Node> nodeList = new ArrayList<Node>();
	private Node inicial;
	private boolean accept;

	public Automata(String inicial, boolean accept) {
		addState(inicial, accept);
		this.inicial = nodes.get(inicial);
	}

	public void addState(String state, boolean accept) {
		if (!nodes.containsKey(state)) {
			Node node = new Node(state, accept);
			nodes.put(state, node);
			nodeList.add(node);
		}
		if (accept) {
			this.accept = true;
		}
	}

	public void addTransition(String transition, String from, String to) {
		Node origin = nodes.get(from);
		Node dest = nodes.get(to);
		if (origin != null && dest != null) {
			for (Arc arc : origin.adj) {
				if (arc.neighbor.state.equals(to)) {
					return;
				}
			}
			origin.adj.add(new Arc(transition, dest));
		}
	}

	public boolean isAccepted(String s) {
		if (!accept || s.length() == 0) {
			return false;
		}
		return isAccepted(inicial, s, 0);
	}

	private boolean isAccepted(Node current, String s, int currentPos) {
		if (currentPos == s.length()) {
			return current.accept;
		}
		for (Arc adj : current.adj) {
			if (adj.transition.charAt(0) == s.charAt(currentPos)) {
				boolean found = isAccepted(adj.neighbor, s, currentPos + 1);
				if (found) {
					return true;
				}
			}
		}
		return false;
	}

	public void print() {
		for (Node n : nodeList) {
			boolean from = n.accept ? true : false;
			System.out.print(n.state + from + "--> ");
			for (Arc adj : n.adj) {
				boolean to = adj.neighbor.accept ? true : false;
				System.out.print(adj.neighbor.state + to + "(" + adj.transition
						+ ") ");
			}
			System.out.println("");
		}
	}

	private class Node {
		String state;
		boolean accept;
		List<Arc> adj = new ArrayList<Arc>();

		public Node(String state, boolean accept) {
			this.state = state;
			this.accept = accept;
		}

		public int hashCode() {
			return state.hashCode();
		}

		public boolean equals(Object obj) {
			if (obj == null || (obj.getClass() != getClass())) {
				return false;
			}
			return state.equals(((Node) obj).state);
		}
	}

	private class Arc {
		String transition;
		Node neighbor;

		public Arc(String transition, Node neighbor) {
			this.transition = transition;
			this.neighbor = neighbor;
		}
	}
}
