package backend;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import utilities.Chronometer;
import utilities.PrimeNumbers;

public abstract class GameState implements Comparable<GameState> {
	Board board;
	int player1CurrentScore;
	int player2CurrentScore;
	private boolean selected = false;
	int heuristicValue;
	boolean prune = false;

	public GameState(Board board, int player1, int player2, int heuristicValue) {
		this.board = board;
		this.player1CurrentScore = player1;
		this.player2CurrentScore = player2;
		this.heuristicValue = heuristicValue;
		if (board.getGameData().isPruneValidated()) {
			prune = true;
		}
	}

	public GameData getGameData() {
		return this.board.getGameData();
	}

	public Board getBoard() {
		return board;
	}

	public int getPlayer1CurrentScore() {
		return player1CurrentScore;
	}

	public int getPlayer2CurrentScore() {
		return player2CurrentScore;
	}

	public int getDepth() {
		return board.getGameData().getDepth();
	}

	public boolean isOver() {
		return board.isOver();
	}

	public long getTimeCap() {
		return board.getGameData().getTimeCap();
	}

	public int getX() {
		return board.getX();
	}

	public int getY() {
		return board.getY();
	}

	protected int getHeuristic() {
		return heuristicValue;
	}

	public boolean isPruneValidated() {
		return board.getGameData().isPruneValidated();
	}

	public boolean isForDepth() {
		return board.getGameData().isForDepth();
	}

	public boolean isForTime() {
		return board.getGameData().isForTime();
	}

	public boolean isConsole() {
		return board.getGameData().isConsole();
	}

	public boolean isTreeValidated() {
		return getGameData().isTreeValidated();
	}

	protected abstract int sumScores(int movePoints);

	protected abstract GameState newPossibleState(Board board,
			int playerNewScore);

	public abstract GameState move(int x, int y);

	protected abstract int changeBeta(int heuristicValue, int beta);

	protected abstract int changeAlpha(int heuristicValue, int alpha);

	protected abstract boolean canPrune(int alpha, int beta);

	protected abstract boolean betterChild(GameState state1, GameState state2);

	protected abstract void restartHeuristic();

	/*
	 * This method calculates every possible state from any valid movement. The
	 * possible states calculated here should respond to the minimax algorithm
	 * logic, this is every maximizer node has minimizer childs and vice versa.
	 */
	public Set<GameState> calculatePosibleStates() {
		Set<GameState> possibles = new HashSet<GameState>();
		getBoard().calculatePossibleBoards(this, possibles);
		return possibles;
	}

	/*
	 * This method called by Board adds to the set given a new state according
	 * to the minimax algorithm logic.
	 */
	public void addChildState(Board aux, int exploded, Set<GameState> set) {
		int movePoints = calculatePoints(exploded);
		int playerNewScore = sumScores(movePoints);
		if (aux.isEmpty())
			playerNewScore *= 1.3;
		GameState newState = newPossibleState(aux, playerNewScore);
		set.add(newState);
		return;
	}

	/*
	 * This is the minimax method used for the AI. Since the heuristic value of
	 * the leaves is: player 2 score - player 1 score, the maximizer nodes
	 * (MaxState) want what is best for the player 2 and the minimizer nodes
	 * (MinState) want what is best for the player 1. The root of the tree for
	 * minimax will depend if we are calculating the best movement for player 1
	 * (console mode) or player 2 (visual mode). The implementation of minimax
	 * uses a DFS algorithm to allow alpha-beta prunning.
	 */
	public GameState minimax() throws IOException {
		Chronometer ch = new Chronometer(getTimeCap());
		ch.start();
		BufferedWriter file = startTree();
		GameState aux = minimaxRec(this, getDepth(), ch, Integer.MIN_VALUE,
				Integer.MAX_VALUE, file);
		ch.stop();
		if (isForTime() && isConsole()) {
			System.out.println("Time cap was: " + getTimeCap() / 1000);
			System.out.println("Finished in: " + ch.getFinalSeconds());
		}
		if (isTreeValidated()) {
			notPrune();
			file.newLine();
			file.append(getKey() + "[" + nodeToString("Start") + "]");
			file.newLine();
			file.append("}");
		}
		file.close();
		if (aux == null) {
			/*
			 * The only way to get here is if the time cap given is so small
			 * that minimax couldn't make any calculations
			 */
			System.out.println("Tiempo insuficiente");
			return this;
		}
		return aux;
	}

	private GameState minimaxRec(GameState current, int depth, Chronometer ch,
			int alpha, int beta, BufferedWriter file) throws IOException {
		if ((isForTime() && ch.isOver()) || (!isForTime() && depth <= 0)) {
			current.heuristicValue = current.player2CurrentScore
					- current.player1CurrentScore;
			return current;
		}
		Set<GameState> childs = this.calculatePosibleStates();
		if (childs.isEmpty()) {
			current.heuristicValue = current.player2CurrentScore
					- current.player1CurrentScore;
			return current;
		}
		GameState bestChild = null;
		boolean pruned = false;
		for (GameState child : childs) {
			if (!ch.isOver()&&!pruned) {
				child.minimaxRec(child, depth - 1, ch, alpha, beta, file);
				if (bestChild == null || betterChild(child, bestChild)) {
					bestChild = child;
					this.heuristicValue = child.getHeuristic();
				}
				if (isPruneValidated()) {
					alpha = changeAlpha(bestChild.getHeuristic(), alpha);
					beta = changeBeta(bestChild.getHeuristic(), beta);
					if (canPrune(alpha, beta)) {
						bestChild.notPrune();
						bestChild.selected();
						if (!isTreeValidated()) { 
							return bestChild;
						}
						pruned = true;
					}
					child.notPrune();
				}	
			}
			if (isTreeValidated()) {
				addTree(file, child);
				file.newLine();
				file.append(getConection(child.getKey()));
			}
		}
		if (bestChild != null && isTreeValidated()) {
			bestChild.selected();
			addTree(file, bestChild);
		}
		return bestChild;
	}

	public int hashCode() {
		int hash = 0;
		int k = PrimeNumbers.size() - 1;
		hash += player1CurrentScore * PrimeNumbers.get(k--);
		hash += player2CurrentScore * PrimeNumbers.get(k--);
		hash += board.hashCode();
		return hash;
	}

	public int compareTo(GameState state) {
		if (board.equals(state.getBoard())
				&& player1CurrentScore == state.getPlayer1CurrentScore()
				&& player2CurrentScore == state.getPlayer2CurrentScore()) {
			return 0;
		} else {
			return -1;
		}
	}

	@Override
	public boolean equals(Object obj) {
		return this.compareTo((GameState) obj) == 0;
	}

	/*
	 * Calculate the points made by a given amount of cells exploded in the
	 * movement. This is calculated according to the rules of the game.
	 */
	public int calculatePoints(int exploded) {
		int points = 0;
		switch (exploded) {
		case 2:
			points = 1;
			break;
		case 3:
			points = 2;
			break;
		case 4:
			points = 4;
			break;
		case 5:
			points = 8;
			break;
		default:
			points = 2 * exploded;
			break;
		}
		return points;
	}

	/*
	 * Used for testing in console mode before frontend was added.
	 */
	public void print() {
		System.out.println("-------------------------");
		System.out.println("Puntaje Jugador 1: " + player1CurrentScore);
		System.out.println("Puntaje Jugador 2: " + player2CurrentScore);
		board.print();
	}

	protected String nodeToString() {
		String xy = "(" + board.getX() + "," + board.getY() + ")";
		return nodeToString(xy);
	}

	protected String nodeToString(String aux) {
		String heuristic = "";
		if (!prune) {
			heuristic = String.valueOf(getHeuristic());
		}
		return selectedToString() + pruneToString() + "label=\"" + aux
				+ heuristic + "\"";
	}

	private String pruneToString() {
		String answer = "";
		if (prune) {
			answer = "color=green, bgcolor=green, style=filled,";
		}
		return answer;
	}

	private void selected() {
		selected = true;
	}

	private String getKey() {
		return String.valueOf(super.hashCode());
	}

	private String getConection(String otherKey) {
		return getKey() + "->" + otherKey;
	}

	private String selectedToString() {
		String answer = "";
		if (selected) {
			answer = "color=blue, bgcolor=blue, style=filled,";
		}
		return answer;
	}

	private void notPrune() {
		this.prune = false;
	}

	private BufferedWriter startTree() throws IOException {
		BufferedWriter file = new BufferedWriter(new FileWriter("tree.dot",
				false));
		file.write("digraph{");
		return file;
	}

	private void addTree(BufferedWriter file, GameState gs) throws IOException {
			file.newLine();
			file.append(gs.getKey() + gs.nodeToString());
	}

}