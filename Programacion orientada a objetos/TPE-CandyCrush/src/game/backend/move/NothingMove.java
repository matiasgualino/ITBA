package game.backend.move;

import game.backend.Grid;

/**
 * Clase que define el movimiento entre un elemento "nada" y otro
 */
public class NothingMove extends Move {

	/**
	 * Instantiates a new nothing move.
	 */
	public NothingMove(Grid grid) {
		super(grid);
	}

	@Override
	public boolean internalValidation() {
		return false;
	}

	@Override
	public void removeElements() {
	}
}