package PrimerParcial;

import java.util.HashMap;
import java.util.Map;

public class EntityMap<T, S> {
	private Map<String, Node> map;

	public EntityMap() {
		this.map = new HashMap<String, Node>();
	}

	public void addEntity(String name, String father) {
		if (!map.containsKey(name)) {
			map.put(name, new Node(map.get(father)));
		}
	}

	public void addProperty(String name, T key, S value) {
		if (map.containsKey(name)) {
			map.get(name).addPair(key, value);
		}
	}

	public Map<T, S> getProperties(String entity) {
		if (!map.containsKey(entity)) {
			return null;
		}
		Node current = map.get(entity);
		Map<T, S> answer = new HashMap<T, S>();
		while (current != null) {
			for (T key : current.map.keySet()) {
				if (!answer.containsKey(key)) {
					answer.put(key, current.map.get(key));
				}
			}
			current = current.father;
		}
		return answer;
	}

	private class Node {
		Map<T, S> map = new HashMap<T, S>();
		Node father;

		public Node(Node father) {
			this.father = father;
		}

		public void addPair(T key, S value) {
			this.map.put(key, value);
		}
	}

}
