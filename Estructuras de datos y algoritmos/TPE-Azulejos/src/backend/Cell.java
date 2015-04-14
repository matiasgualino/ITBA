package backend;

public class Cell {
	private Cell[] around = new Cell[Direction.values().length];
	private int value;

	public Cell(int value) {
		this.value = value;
	}

	public void receive(int value) {
		this.value = value;
	}

	/*
	 * Used in the gravity method to put the content of this cell in the cell
	 * below.
	 */
	public void fall() {
		Cell down = around[Direction.DOWN.ordinal()];
		if (down != null) {
			if (this.value != 0 && down.getValue() == 0) {
				down.setValue(this.value);
				this.value = 0;
			}
		}
	}

	/*
	 * Saves pointers to the cells around.
	 */
	public void setAround(Cell up, Cell down, Cell left, Cell right) {
		this.around[Direction.UP.ordinal()] = up;
		this.around[Direction.DOWN.ordinal()] = down;
		this.around[Direction.LEFT.ordinal()] = left;
		this.around[Direction.RIGHT.ordinal()] = right;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public Cell[] getAround() {
		return this.around;
	}

	/*
	 * Used for lateral moves in the gravity method. It takes the content of the
	 * cell to the right.
	 */
	public void getRightAndSet() {
		Cell right = around[Direction.RIGHT.ordinal()];
		if (right != null) {
			this.value = right.getValue();
			right.setValue(0);
		}
	}

}
