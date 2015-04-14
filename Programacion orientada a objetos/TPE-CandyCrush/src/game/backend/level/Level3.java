package game.backend.level;

import game.backend.GameState;
import game.backend.cell.WeirdWall;

public class Level3 extends Level {

	private static int REQUIRED_SCORE = 5000;
	private static int MAX_MOVES = 20;

	@Override
	protected GameState newState() {
		return new LevelState(REQUIRED_SCORE, MAX_MOVES);
	}

	@Override
	protected void levelDiffs() {
		for (int i = 3; i < SIZE - 3; i++) {
			for (int j = 3; j < SIZE - 3; j++) {
				super.setCell(i, j, new WeirdWall(this));
			}
		}
	}
}
