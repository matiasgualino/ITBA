package game.backend;

public abstract class GameState {
	
	private long score = 0;
	private int moves = 0;
	private long requiredScore;
	private int maxMoves;
	private int jellies=0;

	/**
	 * Gets the required score.
	 */
	public long getRequiredScore() {
		return requiredScore;
	}

	/**
	 * Sets the required score.
	 */
	public void setRequiredScore(long requiredScore) {
		this.requiredScore = requiredScore;
	}

	/**
	 * Gets the max moves.
	 */
	public int getMaxMoves() {
		return this.maxMoves;
	}

	/**
	 * Sets the max moves.
	 */
	public void setMaxMoves(int maxMoves) {
		this.maxMoves = maxMoves;
	}

	/**
	 * Adds the score.
	 */
	public void addScore(long value) {
		this.score = this.score + value;
	}

	/**
	 * Gets the score.
	 */
	public long getScore() {
		return score;
	}

	/**
	 * Adds the move.
	 */
	public void addMove() {
		moves++;
	}

	/**
	 * Gets the moves.
	 */
	public int getMoves() {
		return moves;
	}

	/**
	 * Chequea si termino el juego.
	 */
	public boolean gameOver() {
		return playerWon() || getMoves() >= getMaxMoves();
	}

	/**
	 * Chequea si se alcanzo el puntaje requerido.
	 */
	public boolean playerWon() {
		return (getScore() > getRequiredScore()) && getJellies()==0;
	}

	/**
	 * Gets the jellies.
	 */
	public int getJellies() {
		return jellies;
	}
	
	/**
	 * Sets the jellies.
	 */
	public void setJellies(int jellies) {
		this.jellies = jellies;
	}

}
