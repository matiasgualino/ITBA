package game.backend.cell;

import game.backend.Grid;
import game.backend.element.Candy;
import game.backend.element.CandyColor;

public class Cage extends Cell {

	private boolean isCage;

	/**
	 * Instantiates a new cage.
	 * 
	 * @param grid
	 *            the grid
	 */
	public Cage(Grid grid) {
		super(grid);
		this.isCage = true;
		setContent(new Candy(CandyColor.values()[(int)(Math.random() * CandyColor.values().length)]));
	}

	public void clearContent() {
		if (isCage) {
			this.isCage = false;
		}
		super.clearContent();
	}

	public boolean isMovable() {
		if (isCage)
			return false;
		else
			return super.isMovable();
	}

	public String getKey() {
		if (isCage)
			return "CAGE";
		else
			return super.getKey();
	}

}
