package game.backend.move;

import game.backend.Grid;
import game.backend.element.Element;

public abstract class Move {

	private Grid grid;
	protected int i1, j1, i2, j2;
	
	/**
	 * Instantiates a new move.
	 */
	public Move(Grid grid) {
		this.grid = grid;
	}
	
	/**
	 * Sets the coords.
	 */
	public void setCoords(int i1, int j1, int i2, int j2){
		this.i1 = i1;
		this.j1 = j1;
		this.i2 = i2;
		this.j2 = j2;
	}
	
	/**
	 * Checks if the movement is valid.
	 */
	public boolean isValid() {
		if ( (i1 == i2 && Math.abs(j1-j2) == 1) || (j1 == j2 && Math.abs(i1-i2) == 1)) {
				return internalValidation();
		}
		return false;
	}
	
	/**
	 * Internal validation.
	 */
	protected boolean internalValidation() {
		return true;
	}
	
	/**
	 * Gets the element in a specified position.
	 */
	protected Element get(int i, int j) {
		return grid.get(i, j);
	}
	
	/**
	 * Clears the content in a specified position.
	 */
	protected void clearContent(int i, int j) {
		grid.clearContent(i, j);
	}
	
	/**
	 * Sets the content in a specified position.
	 */
	protected void setContent(int i, int j, Element e){
		grid.setContent(i, j, e);
	}
	
	/**
	 * Was updated.
	 */
	protected void wasUpdated(){
		grid.wasUpdated();
	}
	
	/**
	 * Removes the elements.
	 */
	public abstract void removeElements();
}
