package backend;

import java.util.Set;

import utilities.PrimeNumbers;

public class Board implements Comparable<Board> {

	private Cell[][] grid;
	private GameData gameData;
	private int x;
	private int y;

	/* Intended only to the initial board of the game */
	public Board(GameData gameData) {
		this.gameData = gameData;
		this.grid = new Cell[gameData.getRows()][gameData.getCols()];
		instantiateCells(gameData.getBoardData());
		setAroundOfCells();
	}

	public Board(GameData gameData, String[] boardData, int x, int y) {
		this.gameData = gameData;
		this.grid = new Cell[gameData.getRows()][gameData.getCols()];
		this.x = x;
		this.y = y;
		instantiateCells(boardData);
		setAroundOfCells();
	}

	public Board(int rows, int cols, Cell[][] grid, int x, int y) {
		this.grid = grid;
		this.x = x;
		this.y = y;
	}

	public GameData getGameData() {
		return this.gameData;
	}

	public int getRows() {
		return gameData.getRows();
	}

	public int getCols() {
		return gameData.getCols();
	}

	public int getX() {
		return x;
	}

	public void changeX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void changeY(int y) {
		this.y = y;
	}

	public Cell[][] getGrid() {
		return this.grid;
	}

	/*
	 * Instantiate every cell in the grid with the values from text file
	 * 
	 * @param data An array of the lines in the text
	 */
	private void instantiateCells(String[] data) {
		for (int i = 0; i < gameData.getRows(); i++) {
			for (int j = 0; j < gameData.getCols(); j++) {
				int number;
				char aux = data[i].charAt(j);
				if (aux == 32)/* 32 is the ASCII code of space */{
					number = 0;
				} else {
					number = Integer.parseInt(String.valueOf(aux));
				}
				getGrid()[i][j] = new Cell(number);
			}
		}
	}

	/*
	 * Tells each cell of the grid to save a pointer to the neighbours cells.
	 */
	private void setAroundOfCells() {
		// corners
		getGrid()[0][0].setAround(null, getGrid()[1][0], null, getGrid()[0][1]);
		getGrid()[0][gameData.getCols() - 1].setAround(null,
				getGrid()[1][gameData.getCols() - 1],
				getGrid()[0][gameData.getCols() - 2], null);
		getGrid()[gameData.getRows() - 1][0].setAround(
				getGrid()[gameData.getRows() - 2][0], null, null,
				getGrid()[gameData.getRows() - 1][1]);
		getGrid()[gameData.getRows() - 1][gameData.getCols() - 1]
				.setAround(
						getGrid()[gameData.getRows() - 2][gameData.getCols() - 1],
						null,
						getGrid()[gameData.getRows() - 1][gameData.getCols() - 2],
						null);

		// upper line cells
		for (int j = 1; j < gameData.getCols() - 1; j++) {
			getGrid()[0][j].setAround(null, getGrid()[1][j],
					getGrid()[0][j - 1], getGrid()[0][j + 1]);
		}
		// bottom line cells
		for (int j = 1; j < gameData.getCols() - 1; j++) {
			getGrid()[gameData.getRows() - 1][j].setAround(
					getGrid()[gameData.getRows() - 2][j], null,
					getGrid()[gameData.getRows() - 1][j - 1],
					getGrid()[gameData.getRows() - 1][j + 1]);
		}
		// left line cells
		for (int i = 1; i < gameData.getRows() - 1; i++) {
			getGrid()[i][0].setAround(getGrid()[i - 1][0], getGrid()[i + 1][0],
					null, getGrid()[i][1]);
		}
		// right line cells
		for (int i = 1; i < gameData.getRows() - 1; i++) {
			getGrid()[i][gameData.getCols() - 1].setAround(
					getGrid()[i - 1][gameData.getCols() - 1],
					getGrid()[i + 1][gameData.getCols() - 1],
					getGrid()[i][gameData.getCols() - 2], null);
		}
		// central cells
		for (int i = 1; i < gameData.getRows() - 1; i++) {
			for (int j = 1; j < gameData.getCols() - 1; j++) {
				getGrid()[i][j].setAround(getGrid()[i - 1][j],
						getGrid()[i + 1][j], getGrid()[i][j - 1],
						getGrid()[i][j + 1]);
			}
		}
	}

	/*
	 * Used after a movement to acomodate the content of the board where it
	 * should be.
	 */
	public void gravity() {
		for (int k = 0; k < gameData.getRows() - 1; k++) {
			for (int i = 0; i < gameData.getRows(); i++) {
				for (int j = 0; j < gameData.getCols(); j++) {
					grid[i][j].fall();
				}
			}
		}
		lateralMove();
		if (grid[gameData.getRows() - 1][0].getValue() == 0) {
			lateralMove();
		}
	}

	private void lateralMove() {
		for (int k = 0; k < gameData.getCols() - 1; k++) {
			for (int j = 0; j < gameData.getCols(); j++) {
				if (grid[gameData.getRows() - 1][j].getValue() == 0) {
					for (int i = 0; i < gameData.getRows(); i++) {
						grid[i][j].getRightAndSet();
					}
				}
			}
		}
	}

	public void print() {
		for (int i = 0; i < gameData.getRows(); i++) {
			System.out.println();
			for (int j = 0; j < gameData.getCols(); j++) {
				System.out.print(grid[i][j].getValue() + " ");
			}
		}
		System.out.println();
	}

	public void setContent(int value, int fil, int col) {
		grid[fil][col].setValue(value);
	}

	/*
	 * Calculates every possible state from all the possible boards created by
	 * valid movements.
	 * 
	 * @param state The current state that has the current board of the game.
	 * 
	 * @param set The set where this method saves all the possible states.
	 * 
	 * @throws InvalidMovementException Because it will try to explode every
	 * cell even if it is not valid.
	 */
	public void calculatePossibleBoards(GameState state, Set<GameState> set) {
		boolean[][] available = new boolean[getRows()][getCols()];// consider
																	// false as
																	// true to
																	// save time
		for (int i = 0; i < getRows(); i++) {
			for (int j = 0; j < getCols(); j++) {
				try {
					if (available[i][j] == false) {
						int valid;
						Board aux = state.getBoard().clone();
						valid = aux.explode(i, j, available);
						aux.changeX(i);
						aux.changeY(j);
						aux.gravity();
						state.addChildState(aux, valid, set);
					}
				} catch (InvalidMovementException e) {
				}
			}
		}
		return;
	}

	/*
	 * Checks if a movement is valid.
	 * 
	 * @param x The X value of the movement y the Y value of the movement
	 */
	public boolean moveIsValid(int x, int y) {
		return moveIsValid(grid[x][y]);
	}

	private boolean moveIsValid(Cell cell) {
		if (cell.getValue() == 0) {
			return false;
		}
		int value = cell.getValue();
		int arounds = 0;
		for (Cell c : cell.getAround()) {
			if (c != null && c.getValue() == value)
				arounds++;
		}
		return (arounds > 0);
	}

	/*
	 * After a move is declared valid, this method explodes the content of the
	 * cells with the same value.
	 * 
	 * @param availabe Matrix of booleans intended to save time in the method
	 * calculatePossibleBoards.
	 */
	public int explode(int x, int y, boolean[][] available) {
		Cell cell = grid[x][y];
		if (moveIsValid(cell)) {
			int exploded = explodeRec(x, y, cell, available);
			return exploded;
		} else {
			throw new InvalidMovementException();
		}
	}

	private int explodeRec(int x, int y, Cell cell, boolean[][] available) {
		int value = cell.getValue();
		cell.setValue(0);
		if (available != null) {
			// The only case where available is null should be when called from
			// move(x,y) in minState
			available[x][y] = true;
		}
		int exploded = 1;
		Cell[] arounds = cell.getAround();
		int[][] moves = new int[][] { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } }; // UP,
																					// DOWN,
																					// LEFT,
																					// RIGHT
		for (int k = 0; k < moves.length; k++) {
			Cell current = arounds[k];
			int newRow = x + moves[k][0];
			int newCol = y + moves[k][1];
			if (current != null) {
				if (current.getValue() == value) {
					exploded += explodeRec(newRow, newCol, current, available);
				}
			}
		}
		return exploded;
	}

	/*
	 * This method returns 0 if both grids are equivalent and ERROR if they have
	 * a difference.
	 */
	@Override
	public int compareTo(Board board) {
		if (this.getCols() != board.getCols()
				|| this.getRows() != board.getRows()) {
			return -1;
		}
		int comparation = 0;
		for (int i = 0; i < getRows() && comparation == 0; i++) {
			for (int j = 0; j < getCols() && comparation == 0; j++) {
				if (grid[i][j].getValue() != board.getGrid()[i][j].getValue()) {
					comparation = -1;
				}
			}
		}
		return comparation;
	}

	public Board clone() {
		String[] boardData = new String[gameData.getRows()];
		int pos = 0;
		for (Cell[] r : grid) {
			StringBuffer aux = new StringBuffer();
			for (Cell c : r) {
				aux.append(c.getValue());
			}
			boardData[pos++] = aux.toString();
		}
		Board aux = new Board(gameData, boardData, x, y);
		return aux;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		int k = 0;
		hash += (gameData.getRows() * PrimeNumbers.get(k++));
		hash += (gameData.getCols() * PrimeNumbers.get(k++));
		for (int i = 0; i < getRows(); i++) {
			for (int j = 0; j < getCols(); j++) {
				hash += (grid[i][j].getValue() * PrimeNumbers.get(k++));
			}
		}
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		return (this.compareTo((Board) obj) == 0);
	}

	/*
	 * Checks if there is any possible valid movements in the board.
	 */
	public boolean isOver() {
		boolean hasMove = false;
		for (int i = gameData.getRows() - 1; i >= 0 && !hasMove; i--) {
			for (int j = 0; j < gameData.getCols() && !hasMove; j++) {
				if (moveIsValid(grid[i][j])) {
					hasMove = true;
				}
			}
		}
		return !hasMove;
	}

	/*
	 * Make sure that you have used gravity before calling this method because
	 * this method is optimized for those kinds of boards
	 */
	public boolean isEmpty() {
		boolean aux = true;
		for (int i = 0; i < gameData.getCols() && aux; i++) {
			if (grid[gameData.getRows() - 1][i].getValue() != 0)
				aux = false;
		}
		return aux;
	}

}