package game.backend.cell;

import game.backend.Grid;
import game.backend.element.Element;
import game.backend.element.Nothing;
import game.backend.move.Direction;

public class Cell {

	private Grid grid;
	private Cell[] around = new Cell[Direction.values().length];
	private Element content;

	/**
	 * Instantiates a new cell.
	 */
	public Cell(Grid grid) {
		this.grid = grid;
		this.content = new Nothing();
	}

	/**
	 * Sets the around.
	 */
	public void setAround(Cell up, Cell down, Cell left, Cell right) {
		this.around[Direction.UP.ordinal()] = up;
		this.around[Direction.DOWN.ordinal()] = down;
		this.around[Direction.LEFT.ordinal()] = left;
		this.around[Direction.RIGHT.ordinal()] = right;
	}

	/**
	 * Checks for floor.
	 */
	public boolean hasFloor() {
		return !around[Direction.DOWN.ordinal()].isEmpty();
	}

	/**
	 * Checks if is movable.
	 */
	public boolean isMovable() {
		return content.isMovable();
	}

	/**
	 * Checks if is empty.
	 */
	public boolean isEmpty() {
		return !content.isSolid();
	}

	/**
	 * Gets the content.
	 */
	public Element getContent() {
		return content;
	}

	/**
	 * Clear content.
	 */
	public void clearContent() {
		if (content.isMovable()) {
			Direction[] explosionCascade = content.explode();
			grid.cellExplosion(content);
			this.content = new Nothing();
			if (explosionCascade != null) {
				expandExplosion(explosionCascade);
			}
		}
	}

	/**
	 * Expand explosion.
	 */
	protected void expandExplosion(Direction[] explosion) {
		for (Direction d : explosion) {
			this.around[d.ordinal()].explode(d);
		}
	}

	/**
	 * Explode.
	 */
	protected void explode(Direction d) {
		clearContent();
		if (this.around[d.ordinal()] != null)
			this.around[d.ordinal()].explode(d);
	}

	/**
	 * Gets and clears the content.
	 */
	public Element getAndClearContent() {
		if (content.isMovable()) {
			Element ret = content;
			this.content = new Nothing();
			return ret;
		}
		return null;
	}

	/**
	 * Fall upper content.
	 */
	public boolean fallUpperContent() {
		Cell up = around[Direction.UP.ordinal()];
		if (this.isEmpty() && !up.isEmpty() && up.isMovable()) {
			this.content = up.getAndClearContent();
			grid.wasUpdated();
			if (this.hasFloor()) {
				grid.tryRemove(this);
				return true;
			} else {
				Cell down = around[Direction.DOWN.ordinal()];
				return down.fallUpperContent();
			}
		}
		return false;
	}

	/**
	 * Fall upper content.
	 */
	public boolean fallUpperContent(Cell to) {
		Cell up = to.getAround()[Direction.UP.ordinal()];
		if (this.isEmpty() && !up.isEmpty() && up.isMovable()) {
			this.setContent(up.getAndClearContent());
			grid.wasUpdated();
			if (this.hasFloor()) {
				grid.tryRemove(this);
				return true;
			} else {
				Cell down = around[Direction.DOWN.ordinal()];
				return down.fallUpperContent(this);
			}
		}
		return false;
	}

	/**
	 * Sets the content.
	 */
	public void setContent(Element content) {
		this.content = content;
	}

	/**
	 * Gets the key.
	 */
	public String getKey() {
		return "CELL";
	}

	/**
	 * Gets the grid.
	 */
	public Grid getGrid() {
		return grid;
	}

	/**
	 * Gets the around.
	 */
	public Cell[] getAround() {
		return around;
	}
	
	/**
	 * Checks if is wall.
	 */
	public boolean isWall(){
		return content.isWall();
	}
}
