package backend;

/*
 * This class represents the maximizer node in the minimax algorithm, 
 * and it's methods are intended to act accordingly.
 * */
public class MaxState extends GameState {

	public MaxState(Board board, int player1, int player2) {
		super(board, player1, player2, Integer.MIN_VALUE);
	}

	/*
	 * Because MaxState represents the player 2 of the game by design, this
	 * method sums the points made by the movement to the player 2.
	 */
	@Override
	protected int sumScores(int movePoints) {
		return getPlayer2CurrentScore() + movePoints;
	}

	/*
	 * This method is called from calculatePossibleStates in GameState, which
	 * returns the state that corresponds to the next iteration of minimax.
	 */
	@Override
	protected GameState newPossibleState(Board board, int playerNewScore) {
		return (new MinState(board, getPlayer1CurrentScore(), playerNewScore));
	}

	/*
	 * Since this class represents the player 2 by design and will always be
	 * controlled by the computer, this method should never be called.
	 */
	@Override
	public GameState move(int x, int y) {
		System.out.println("Humans can't move me");
		return this;
	}

	/*
	 * Returns true if the first parameter is better than the second one
	 */
	@Override
	protected boolean betterChild(GameState state1, GameState state2) {
		return state1.getHeuristic() >= state2.getHeuristic();
	}

	/*
	 * Updates the beta value for the iterations of minimax with prunning
	 * according to the behavior of the maximizer.
	 */
	@Override
	protected int changeBeta(int heuristicValue, int beta) {
		return beta;
	}

	/*
	 * Updates the alpha value for the iterations of minimax with prunning
	 * according to the behavior of the maximizer.
	 */
	@Override
	protected int changeAlpha(int heuristicValue, int alpha) {
		return Math.max(heuristicValue, alpha);
	}

	/*
	 * This method tells minimax if it can prune according to the behavior of
	 * the maximizer.
	 */
	@Override
	protected boolean canPrune(int alpha, int beta) {
		return alpha > beta;
	}

	/*
	 * Replaces the heuristic value with the original heuristic value of a
	 * maximizer node, which is -infinity, in this case the biggest possible
	 * value for an Integer.
	 */
	@Override
	protected void restartHeuristic() {
		this.heuristicValue = Integer.MIN_VALUE;
	}

	@Override
	protected String nodeToString() {
		return "[shape = box," + super.nodeToString() + "]";
	}

}