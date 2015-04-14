package game.backend.level;

import game.backend.GameState;
/**
 * Clase que permite instanciar el state de cualquier nivel.
 */
public class LevelState extends GameState {
	public LevelState(long requiredScore, int maxMoves) {
		setRequiredScore(requiredScore);
		setMaxMoves(maxMoves);
	}
}
