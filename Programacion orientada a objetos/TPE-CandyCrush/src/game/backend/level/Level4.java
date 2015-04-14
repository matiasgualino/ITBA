package game.backend.level;

import game.backend.GameState;
import game.backend.cell.Cage;

public class Level4 extends Level {

	private static int REQUIRED_SCORE = 5000;
	private static int MAX_MOVES = 20;

	@Override
	protected GameState newState() {
		return new LevelState(REQUIRED_SCORE, MAX_MOVES);
	}

	@Override
	protected void levelDiffs() {
		for (int i = 0; i < SIZE ; i++) {
			for (int j = 0; j < SIZE ; j++) {
				super.setCell(4, j, new Cage(this));
			}
		}
	}
}
