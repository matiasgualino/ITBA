package TP6;

public class BagImpl<T> implements Bag<T> {

	private SimpleMap<T, Integer> map;
	private int size;

	public BagImpl() {
		this.map = new SimpleMapImpl<T, Integer>(100, 2);
		this.size = 0;
	}

	@Override
	public void add(T value) {
		Integer cant = map.get(value);
		if (cant == null) {
			cant = 0;
		}
		map.put(value, cant + 1);
		size++;
	}

	@Override
	public int occurencesOf(T value) {
		return map.get(value) == null ? 0 : map.get(value);
	}

	@Override
	public void remove(T value) {
		Integer cant = map.get(value);
		if (cant == null) {
			return;
		}
		if (cant == 1) {
			map.remove(value);
		} else {
			map.put(value, cant - 1);
		}
	}

}
