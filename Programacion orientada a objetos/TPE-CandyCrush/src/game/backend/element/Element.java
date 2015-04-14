package game.backend.element;

import game.backend.move.Direction;

public abstract class Element {
	
	/**
	 * Checks if is movable.
	 */
	public abstract boolean isMovable();
	
	/**
	 * Gets the key.
	 */
	public abstract String getKey();
	
	/**
	 * Gets the full key.
	 */
	public String getFullKey() {
		return getKey();
	}

	/**
	 * Checks if is solid.
	 */
	public boolean isSolid() {
		return true;
	}
	
	/**
	 * Explode.
	 * 
	 * @return the direction[]
	 */
	public Direction[] explode() {
		return null;
	}
	
	/**
	 * Gets the score.
	 */
	public long getScore() {
		return 0;
	}
	
	/**
	 * Checks if is wall.
	 */
	public abstract boolean isWall();
}
