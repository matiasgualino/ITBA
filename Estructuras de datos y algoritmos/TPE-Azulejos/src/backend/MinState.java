package backend;

/*
 * This class represents the minimizer node in the minimax algorithm, 
 * and it's methods are intended to act accordingly.
 * */
public class MinState extends GameState {

	public MinState(Board board, int player1, int player2) {
		super(board, player1, player2, Integer.MAX_VALUE);
	}

	/*
	 * Because MaxState represents the player 1 of the game by design, this
	 * method sums the points made by the movement to the player 1.
	 */
	@Override
	protected int sumScores(int movePoints) {
		return getPlayer1CurrentScore() + movePoints;
	}

	/*
	 * This method is called from calculatePossibleStates in GameState, which
	 * returns the state that corresponds to the next iteration of minimax.
	 */
	@Override
	protected GameState newPossibleState(Board board, int playerNewScore) {
		return (new MaxState(board, playerNewScore, getPlayer2CurrentScore()));
	}

	/*
	 * This method tries to make a move in the (x,y) position of the board.
	 */
	@Override
	public GameState move(int x, int y) {
		if (!getBoard().moveIsValid(x, y)) {
			throw new InvalidMovementException();
		}
		int exploded = getBoard().explode(x, y, null);
		getBoard().gravity();
		int points = getPlayer1CurrentScore();
		points += calculatePoints(exploded);
		if (getBoard().isEmpty())
			points *= 1.3;
		player1CurrentScore = points;
		GameState newState = new MaxState(getBoard(), points,
				getPlayer2CurrentScore());
		return newState;
	}

	/*
	 * Returns true if the first parameter is better than the second one
	 */
	@Override
	protected boolean betterChild(GameState state1, GameState state2) {
		return state1.getHeuristic() <= state2.getHeuristic();
	}
	
	/*
	 * Updates the beta value for the iterations of minimax with prunning according to the behavior of the minimizer.
	 */
	@Override
	protected int changeBeta(int heuristicValue, int beta) {
		return Math.min(heuristicValue, beta);
	}
	
	/*
	 * Updates the alpha value for the iterations of minimax with prunning according to the behavior of the minimizer.
	 */
	@Override
	protected int changeAlpha(int heuristicValue, int alpha) {
		return alpha;
	}
	
	/*
	 * This method tells minimax if it can prune according to the behavior of the minimizer.
	 * */
	@Override
	protected boolean canPrune(int alpha, int beta) {
		return beta < alpha;
	}
	
	/*
	 * Replaces the heuristic value with the original heuristic value of a
	 * minimizer node, which is +infinity, in this case the biggest possible
	 * value for an Integer.
	 */
	@Override
	protected void restartHeuristic() {
		this.heuristicValue = Integer.MAX_VALUE;
	}

	protected String nodeToString() {
		return "[" + super.nodeToString() + "]";
	}

}