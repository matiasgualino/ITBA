package game.backend.level;

import game.backend.GameState;

public class Level1 extends Level {
	
	private static int REQUIRED_SCORE = 5000; 
	private static int MAX_MOVES = 20; 
	
	@Override
	protected GameState newState() {
		return new LevelState(REQUIRED_SCORE, MAX_MOVES);
	}

	@Override
	protected void levelDiffs() {
	}
}
