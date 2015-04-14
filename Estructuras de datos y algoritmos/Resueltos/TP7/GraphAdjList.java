package TP7;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * 
 * Clase abstracta para Grafos (no multigrafos). No soporta multigrafos ni lazos
 * 
 * @param <V>
 * @param <E>
 */
public abstract class GraphAdjList<V, E> {

	private class Node {
		public V info;
		public boolean visited;
		public List<Arc> adj;

		public Node(V info) {
			this.info = info;
			this.visited = false;
			this.adj = new ArrayList<Arc>();
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((info == null) ? 0 : info.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final Node other = (Node) obj;
			if (info == null) {
				if (other.info != null)
					return false;
			} else if (!info.equals(other.info))
				return false;
			return true;
		}

	}

	private class Arc {
		public E info;
		public Node neighbor;

		public Arc(E info, Node neighbor) {
			super();
			this.info = info;
			this.neighbor = neighbor;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((neighbor == null) ? 0 : neighbor.hashCode());
			return result;
		}

		// Considero que son iguales si "apuntan" al mismo nodo (para no agregar
		// dos ejes al mismo nodo)
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final Arc other = (Arc) obj;
			if (neighbor == null) {
				if (other.neighbor != null)
					return false;
			} else if (!neighbor.equals(other.neighbor))
				return false;
			return true;
		}

	}

	private HashMap<V, Node> nodes;

	protected abstract boolean isDirected();

	public GraphAdjList() {
		this.nodes = new HashMap<V, Node>();
	}

	public boolean isEmpty() {
		return nodes.size() == 0;
	}

	public void addVertex(V vertex) {
		if (!nodes.containsKey(vertex)) {
			nodes.put(vertex, new Node(vertex));
		}
	}

	public void addArc(V v, V w, E e) {
		Node origin = nodes.get(v);
		Node dest = nodes.get(w);
		if (origin != null && dest != null && !origin.equals(dest)) {
			Arc arc = new Arc(e, dest);
			if (!origin.adj.contains(arc)) {
				origin.adj.add(arc);
				if (!isDirected()) {
					dest.adj.add(new Arc(e, origin));
				}
			}
		}
	}

	public int arcCount() {
		int count = 0;
		for (Node n : getNodes())
			count += n.adj.size();
		if (!isDirected())
			count /= 2;
		return count;
	}

	public void removeArc(V v, V w) {
		Node origin = nodes.get(v);
		if (origin == null)
			return;
		Node dest = nodes.get(w);
		if (dest == null)
			return;
		origin.adj.remove(new Arc(null, dest));
		if (!isDirected())
			dest.adj.remove(new Arc(null, origin));
	}

	public E isArc(V v, V w) {
		Node origin = nodes.get(v);
		if (origin == null)
			return null;

		for (Arc e : origin.adj) {
			if (e.neighbor.info.equals(w)) {
				return e.info;
			}
		}
		return null;

	}

	public int outDegree(V v) {
		Node node = nodes.get(v);
		if (node != null) {
			return node.adj.size();
		}
		return 0;
	}

	public int inDegree(V v) {
		if (!isDirected())
			return outDegree(v);
		int count = 0;
		Node node = nodes.get(v);
		for (Node n : getNodes()) { // Recorremos lista de nodos
			if (!n.equals(node)) {
				for (Arc adj : n.adj)
					// Recorremos lista de adyacencia
					if (adj.neighbor.equals(node))
						count++;
			}
		}
		return count;
	}

	public List<V> neighbors(V v) {
		Node node = nodes.get(v);
		if (node == null)
			return null;

		List<V> l = new ArrayList<V>(node.adj.size());
		for (Arc e : node.adj) {
			l.add(e.neighbor.info);
		}
		return l;
	}

	public void removeVertex(V v) {
		Node node = nodes.get(v);
		if (node == null)
			return;

		// Primero removerlo de la lista de adyacencia de sus vecinos
		Arc e = new Arc(null, node);
		for (Node n : getNodes()) {
			if (!n.equals(node))
				n.adj.remove(e);
		}

		// Eliminar el nodo
		nodes.remove(v);
	}

	public int vertexCount() {
		return nodes.size();
	}

	private List<Node> getNodes() {
		List<Node> l = new ArrayList<Node>(vertexCount());
		Iterator<V> it = nodes.keySet().iterator();
		while (it.hasNext()) {
			l.add(nodes.get(it.next()));
		}
		return l;
	}

	public List<V> DFS(V origin) {
		Node node = nodes.get(origin);
		if (node == null)
			return null;
		clearMarks();
		List<V> l = new ArrayList<V>();
		this.DFS(node, l);
		return l;
	}

	protected void clearMarks() {
		for (Node n : getNodes()) {
			n.visited = false;
		}

	}

	protected void DFS(Node origin, List<V> l) {
		if (origin.visited)
			return;
		l.add(origin.info);
		origin.visited = true;
		for (Arc e : origin.adj)
			DFS(e.neighbor, l);
	}

	public void doDFS(V origin, DoIt<V> toDo) {
		Node n = nodes.get(origin);
		if (n == null)
			return;
		clearMarks();
		doDFS(n, toDo);
		return;
	}

	protected void doDFS(Node n, DoIt<V> toDo) {
		if (n.visited)
			return;
		n.info = toDo.doIt(n.info);
		n.visited = true;
		for (Arc e : n.adj)
			doDFS(e.neighbor, toDo);
	}

	public List<V> iterativeDFS(V origin) {
		Node node = nodes.get(origin);
		if (node == null) {
			return null;
		}
		clearMarks();
		List<V> list = new ArrayList<V>();
		Deque<Node> stack = new LinkedList<Node>();
		node.visited = true;
		stack.push(node);
		while (!stack.isEmpty()) {
			Node aux = stack.pop();
			list.add(aux.info);
			for (Arc e : aux.adj) {
				if (!e.neighbor.visited) {
					e.neighbor.visited = true;
					stack.push(e.neighbor);
				}
			}
		}
		return list;
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

	public boolean isConnected() {
		if (isEmpty()) {
			return true;
		}
		clearMarks();
		List<Node> l = getNodes();
		List<V> laux = new ArrayList<V>();
		DFS(l.get(0), laux);
		for (Node node : l) {
			if (!node.visited) {
				return false;
			}
		}
		return true;
	}

	public int connectedComponents() {
		clearMarks();
		int count = 0;
		Node node;
		while ((node = unvisited()) != null) {
			count++;
			DFS(node, new ArrayList<V>());
		}
		return count;
	}

	private Node unvisited() {
		for (Node node : getNodes()) {
			if (!node.visited)
				return node;
		}
		return null;
	}

	public boolean hasCycles() {
		Set<Node> toVisit = new HashSet<Node>();
		toVisit.addAll(nodes.values());
		clearMarks();
		while (toVisit.size() != 0) {
			if (hasCycles(toVisit.iterator().next(), toVisit)) {
				return true;
			}
		}
		return false;
	}

	private boolean hasCycles(Node node, Set<Node> toVisit) {
		node.visited = true;
		toVisit.remove(node);
		for (Arc arc : node.adj) {
			if (arc.neighbor.visited
					|| (toVisit.contains(arc.neighbor) && hasCycles(
							arc.neighbor, toVisit))) {
				return true;
			}
		}
		node.visited = false;
		return false;
	}

	public boolean isStronglyConnected() {
		if (!isDirected()) {
			return false;
		}
		for (Node origin : nodes.values()) {
			for (Node dest : nodes.values()) {
				if (!origin.equals(dest)) {
					if (!hasConnection(origin, dest)) {
						return false;
					}
				}
			}
		}
		return true;
	}

	private boolean hasConnection(Node origin, Node dest) {
		if (BFS(origin.info).contains(dest.info)) {
			return true;
		} else {
			return false;
		}
	}

	public Graph<V, ArcGraph> minPaths(V start) {
		PriorityQueue<PQNode> pq = new PriorityQueue<PQNode>();
		Graph<V, ArcGraph> answer = new Graph<V, ArcGraph>();
		clearMarks();
		pq.offer(new PQNode(nodes.get(start), null, null, 0));
		while (!pq.isEmpty()) {
			PQNode pqnode = pq.poll();
			if (!pqnode.node.visited) {
				answer.addVertex(pqnode.node.info);
				if (pqnode.from != null) {
					answer.addArc(pqnode.from.info, pqnode.node.info,
							pqnode.arc);
				}
				//pqnode.node.tag = (int) pqnode.distance;
				pqnode.node.visited = true;
				for (Arc arc : pqnode.node.adj) {
					if (!arc.neighbor.visited) {
						//pq.offer(new PQNode(arc.neighbor, pqnode.node, arc.info, pqnode.distance + arc.info.getValue()));
					}
				}
			}
		}
		return answer;
	}

	private class PQNode implements Comparable<PQNode> {
		Node node;
		Node from;
		ArcGraph arc;
		double distance;

		public PQNode(Node node, Node from, ArcGraph arc, double distance) {
			this.node = node;
			this.from = from;
			this.arc = arc;
			this.distance = distance;
		}

		public int compareTo(PQNode o) {
			return Double.valueOf(distance).compareTo(o.distance);
		}
	}
	
	public Graph<V, ArcGraph> minGeneratorTree(){
		if(nodes.isEmpty()){
			return null;
		}
		Graph<V, ArcGraph> answer = new Graph<V, ArcGraph>();
		PriorityQueue<PQNode> pq = new PriorityQueue<PQNode>();
		for(Node node : nodes.values()){
			for(Arc adj : node.adj){
				pq.add(new PQNode(node,adj.neighbor,null,(Double)adj.info));
			}
		}
		PQNode smallest = pq.poll();
		answer.addVertex(smallest.from.info);
		answer.addVertex(smallest.node.info);
		answer.addArc(smallest.from.info, smallest.node.info, smallest.arc);
		while(!pq.isEmpty()){
			if(answer.vertexCount()==this.vertexCount()){
				return answer;
			}
			PQNode current = pq.poll();
			
		}		
		return answer;
	}

}
