package SegundoParcial;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class Graph<V> {
	private HashMap<V, Node> nodes = new HashMap<V, Node>();
	private List<Node> nodeList = new ArrayList<Node>();

	public void addVertex(V vertex) {
		if (!nodes.containsKey(vertex)) {
			Node node = new Node(vertex);
			nodes.put(vertex, node);
			nodeList.add(node);
		}
	}

	public void addArc(V v, V w, int weight) {
		Node origin = nodes.get(v);
		Node dest = nodes.get(w);
		if (origin != null && dest != null && !origin.equals(dest)) {
			for (Arc arc : origin.adj) {
				if (arc.neighbor.info.equals(w)) {
					return;
				}
			}
			origin.adj.add(new Arc(weight, dest));
			dest.adj.add(new Arc(weight, origin));
		}
	}

	public List<V> BFS(V origin) {
		Node node = nodes.get(origin);
		if (node == null)
			return null;
		clearMarks();
		List<V> l = new ArrayList<V>();
		Queue<Node> q = new LinkedList<Node>();
		q.add(node);
		node.visited = true;
		while (!q.isEmpty()) {
			node = q.poll();
			l.add(node.info);
			for (Arc e : node.adj) {
				if (!e.neighbor.visited) {
					e.neighbor.visited = true; // Para evitar volve a encolarlo
					q.add(e.neighbor);
				}
			}
		}
		return l;
	}

	public boolean isBFS(List<V> list) {
		if (list.isEmpty() || list.size() != nodeList.size()) {
			return false;
		}
		clearMarks();
		if (!nodes.containsKey(list.get(0))) {
			return false;
		}
		Queue<Node> q = new LinkedList<Node>();
		Node first = nodes.get(list.get(0));
		first.tag = 0;
		q.offer(first);
		first.visited = true;
		while (!q.isEmpty()) {
			Node current = q.poll();
			for (Arc adj : current.adj) {
				if (!adj.neighbor.visited) {
					adj.neighbor.visited = true;
					adj.neighbor.tag = current.tag + 1;
					q.offer(adj.neighbor);
				}
			}
		}
		int previousTag = 0;
		for (V elem : list) {
			if (!nodes.containsKey(elem)) {
				return false;
			}
			if (nodes.get(elem).tag < previousTag) {
				return false;
			}
			previousTag = nodes.get(elem).tag;
		}
		return true;
	}

	public boolean isDFS(List<V> list) {
		if (list.isEmpty() || list.size() != nodeList.size()) {
			return false;
		}
		clearMarks();
		if (!nodes.containsKey(list.get(0))) {
			return false;
		}
		return isDFS(nodes.get(list.get(0)), list.iterator());
	}

	private boolean isDFS(Node node, Iterator<V> it) {
		if (it.hasNext()) {
			if (!node.info.equals(it.next())) {
				return false;
			}
			for (Arc adj : node.adj) {
				if (it.hasNext()) {
					V next = it.next();
					if (!adj.neighbor.visited && adj.neighbor.info.equals(next)) {

					}
				}
			}
			if (it.hasNext()) {
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	public Set<V> VertexCover() {
		return null;
	}

	public List<V> eulerianPath() {
		for (Node n : nodeList) {
			if (n.adj.size() % 2 != 0) {
				return null;
			}
		}

		List<V> l = new ArrayList<V>();
		clearMarks();
		Node first = nodeList.get(0);
		eulerianPathRec(first, l, 0);
		return l;
	}

	private void eulerianPathRec(Node current, List<V> l, int index) {

		l.add(index, current.info);

		for (Arc arc : current.adj) {
			if (!arc.occupied) {
				arc.occupied = true;
				markOcuppy(arc.neighbor, current);
				eulerianPathRec(arc.neighbor, l, index + 1);
			}
		}
		return;
	}

	private void markOcuppy(Node neighbor, Node current) {
		for (Arc arc : neighbor.adj) {
			if (arc.neighbor.equals(current)) {
				arc.occupied = true;
				return;
			}
		}

	}

	public int countCycles(V elem) {
		Node first = nodes.get(elem);
		if (first == null) {
			return 0;
		}
		clearMarks();
		countCycles(first, elem);
		return (first.tag - first.adj.size()) / 2;
	}

	private void countCycles(Node node, V elem) {
		node.visited = true;
		for (Arc adj : node.adj) {
			if (adj.neighbor.info.equals(elem)) {
				adj.neighbor.tag++;
			} else {
				if (!adj.neighbor.visited) {
					countCycles(adj.neighbor, elem);
				}
			}
		}
		node.visited = false;
	}

	public int countCycles() {
		clearMarks();
		int aux = 0;
		for (Node n : nodeList) {
			aux += countCyclesRec(n, n, null) / 2;
			n.visited = true;
		}
		return aux;
	}

	private int countCyclesRec(Node current, Node obj, Node from) {
		int aux = 0;
		if (obj.equals(current)) {
			if (from != null)
				return 1;
		}
		if (from != null)
			current.visited = true;
		for (Arc e : current.adj) {
			if (!e.neighbor.visited && !e.neighbor.equals(from))
				aux += countCyclesRec(e.neighbor, obj, current);
		}
		if (from != null)
			current.visited = false;
		return aux;
	}

	private void clearTags() {
		for (Node n : nodeList) {
			n.tag = 0;
		}
	}

	private void clearMarks() {
		for (Node n : nodeList) {
			n.visited = false;
			n.tag = 0;
		}
	}

	public HashMap<V, V> route(V nodeOrigin) {
		Node origin = nodes.get(nodeOrigin);
		if (origin == null) {
			return null;
		}
		clearMarks();
		HashMap<V, V> answer = new HashMap<V, V>();
		PQNode2 first = new PQNode2(origin, 0, null);
		PriorityQueue<PQNode2> pq = new PriorityQueue<PQNode2>();
		pq.offer(first);
		while (!pq.isEmpty()) {
			PQNode2 current = pq.poll();
			current.node.visited = true;
			answer.put(current.node.info, current.origin);
			for (Arc adj : current.node.adj) {
				if (!adj.neighbor.visited) {
					PQNode2 aux = new PQNode2(adj.neighbor, current.weight
							+ adj.weight, current.origin);
					if (current.origin == null) {
						aux.origin = adj.neighbor.info;
					}
					pq.offer(aux);
				}
			}
		}
		return answer;
	}

	public int minColour() {
		boolean better = false;
		int neighborEval, localSolutionEval;
		Graph<V> localSolution;
		for (int times = 0; times < 5; times++) {
			localSolution = randomSolution();
			localSolutionEval = localSolution.evaluate();
			do {
				better = false;
				for (Solution neighbor : localSolution.neighbors()) {
					neighborEval = neighbor.evaluate();
					if (neighborEval > localSolutionEval) {
						localSolution = neighbor;
						localSolutionEval = neighborEval;
						better = true;
					}
				}
			} while (better);
			if (localSolutionEval > bestSolutionEval) {
				bestSolutionEval = localSolutionEval;
				bestSolution = localSolution;
			}
		}
		bestSolution.print();
	}

	private Graph<V> randomSolution() {
		clearMarks();
		for(Node n : nodeList){
			n.tag = (int)(Math.random()+1)*nodeList.size();
		}
	}

	public Graph<V> subgraph(V v, int n) {
		Node first = nodes.get(v);
		if (first == null) {
			return null;
		}
		Graph<V> answer = new Graph<V>();
		HashSet<V> answerNodes = new HashSet<V>();
		PriorityQueue<PQNode> pq = new PriorityQueue<PQNode>();
		PQNode origin = new PQNode(first, 0);
		pq.offer(origin);
		while (!pq.isEmpty()) {
			PQNode current = pq.poll();
			answerNodes.add(current.node.info);
			current.node.visited = true;
			for (Arc adj : current.node.adj) {
				if (!adj.neighbor.visited && current.weight + adj.weight <= n) {
					pq.offer(new PQNode(adj.neighbor, current.weight
							+ adj.weight));
				}
			}
		}
		for (V newNode : answerNodes) {
			answer.addVertex(newNode);
			Node realNode = nodes.get(newNode);
			for (Arc adj : realNode.adj) {
				if (answerNodes.contains(adj.neighbor.info)) {
					answer.addVertex(adj.neighbor.info);
					answer.addArc(newNode, adj.neighbor.info, adj.weight);
				}
			}
		}
		return answer;
	}

	public void print() {
		for (Node n : nodeList) {
			System.out.print(n.info + "--> ");
			for (Arc adj : n.adj) {
				System.out.print(adj.neighbor.info + "(" + adj.weight + ") ");
			}
			System.out.println("");
		}
	}

	private class PQNode2 implements Comparable<PQNode2> {
		Node node;
		int weight;
		V origin;

		public PQNode2(Node node, int weight, V origin) {
			this.node = node;
			this.weight = weight;
			this.origin = origin;
		}

		@Override
		public int compareTo(PQNode2 o) {
			return this.weight - o.weight;
		}
	}

	public int minWeight(V origin, V dest) {
		if (!nodes.containsKey(origin) || !nodes.containsKey(dest)) {
			return -1;
		}
		clearMarks();
		PriorityQueue<PQNode> pq = new PriorityQueue<PQNode>();
		Node first = nodes.get(origin);
		pq.offer(new PQNode(first, 0));
		while (!pq.isEmpty()) {
			PQNode current = pq.poll();
			if (current.node.info.equals(dest)) {
				return current.weight;
			}
			current.node.visited = true;
			for (Arc a : current.node.adj) {
				if (!a.neighbor.visited) {
					pq.offer(new PQNode(a.neighbor, current.weight + a.weight));
				}
			}
		}
		return -1;
	}

	public int maxWeight(V origin, V dest) {
		if (!nodes.containsKey(origin) || !nodes.containsKey(dest)) {
			return -1;
		}
		if (origin.equals(dest)) {
			return 0;
		}
		clearMarks();
		return maxWeight(nodes.get(origin), nodes.get(dest), 0);
	}

	private int maxWeight(Node current, Node dest, int acum) {
		int best = acum;
		current.visited = true;
		for (Arc adj : current.adj) {
			if (!adj.neighbor.visited) {
				if (adj.neighbor.info.equals(dest)) {
					if (acum + adj.weight > best) {
						best = acum + adj.weight;
					}
				} else {
					int aux = maxWeight(adj.neighbor, dest, acum + adj.weight);
					if (aux > best) {
						best = aux;
					}
				}
			}
		}
		current.visited = false;
		return best;
	}

	private class PQNode implements Comparable<PQNode> {
		Node node;
		int weight;

		public PQNode(Node node, int weight) {
			this.node = node;
			this.weight = weight;
		}

		@Override
		public int compareTo(PQNode o) {
			return this.weight - o.weight;
		}
	}

	private class Node {
		V info;
		boolean visited = false;
		int tag = 0;
		List<Arc> adj = new ArrayList<Arc>();

		public Node(V info) {
			this.info = info;
		}

		public int hashCode() {
			return info.hashCode();
		}

		public boolean equals(Object obj) {
			if (obj == null || (obj.getClass() != getClass())) {
				return false;
			}
			return info.equals(((Node) obj).info);
		}
	}

	private class Arc {
		int weight;
		boolean occupied;
		Node neighbor;

		public Arc(int weight, Node neighbor) {
			this.weight = weight;
			this.neighbor = neighbor;
		}
	}
}
