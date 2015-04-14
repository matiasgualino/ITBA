package frontend;

import java.util.ArrayList;
import java.util.List;

import backend.Board;
import backend.Cell;
import backend.GameState;

/**
 * Esto es backend! Hay que cambiarlo despues.
 * 
 * 
 */
public class AzulejosGame implements GameListener {

	public int DELAY_MS = 500;
	private GameState state;
	private List<GameListener> listeners = new ArrayList<GameListener>();

	public AzulejosGame(GameState state) {
		this.state = state;
	}

	public void addGameListener(GameListener listener) {
		listeners.add(listener);
	}

	@Override
	public void gridUpdated() {
		for (GameListener e : listeners)
			e.gridUpdated();
	}

	public Board getBoard() {
		return state.getBoard();
	}

	public Cell get(int i, int j) {
		return state.getBoard().getGrid()[i][j];
	}

	public GameState getState() {
		return state;
	}

	public void changeSpeed(int speed) {
		DELAY_MS = speed;
	}

	public boolean isFinished() {
		return state.isOver();
	}

	@Override
	public void cellExplosion(Cell e) {

	}

	public boolean playerWon() {
		return (state.getPlayer1CurrentScore() - state.getPlayer2CurrentScore()) > 0;
	}

	public void changeState(GameState state) {
		this.state = state;
	}

	private void delay(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException ie) {
		}
	}

	public void delay() {
		delay(DELAY_MS);
	}

}
