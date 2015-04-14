package game.backend;

import game.backend.cell.Cell;
import game.backend.element.Candy;
import game.backend.element.CandyColor;
import game.backend.element.Element;
import game.backend.move.Direction;
import game.backend.move.Move;
import game.backend.move.MoveMaker;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Grid {

	public static final int SIZE = 9;
	/**
	 * El delay en milisegundos.
	 */
	public int DELAY_MS = 50;
	private Cell[][] g = new Cell[SIZE][SIZE];
	private Map<Cell, Point> gMap = new HashMap<Cell, Point>();
	private GameState state;
	private List<GameListener> listeners = new ArrayList<GameListener>();
	private MoveMaker moveMaker;
	private FigureDetector figureDetector;
	protected abstract GameState newState();

	/**
	 * Este metodo al implementarse debera instanciar
	 * una Cell para cada posicion del tablero.
	 */
	protected abstract void fillCells();

	/**
	 * Cambia la velocidad del juego.
	 */
	public void changeSpeed(int speed){
		DELAY_MS = speed;
	}

	/**
	 * Este metodo hace los cambios del nivel
	 * a partir del nivel basico (todo lleno
	 * de caramelos)
	 */
	protected abstract void levelDiffs();

	/**
	 * Getter del grid.
	 */
	protected Cell[][] g() {
		return g;
	}

	/**
	 * Sets one specified Cell
	 */
	public void setCell(int i, int j, Cell cell) {
		Cell old = g[i][j];
		gMap.remove(old);
		g[i][j] = cell;
		gMap.put(g[i][j], new Point(i, j));
	}

	/**
	 * Getter de state
	 */
	public GameState state() {
		return state;
	}

	/**
	 * Inicializa el juego
	 */
	public void initialize() {
		moveMaker = new MoveMaker(this);
		figureDetector = new FigureDetector(this);
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				g[i][j] = new Cell(this);
				gMap.put(g[i][j], new Point(i, j));
			}
		}
		levelDiffs();
		fillCells();
		fallElements();
	}

	/**
	 * Gets the element in a specified position
	 */
	public Element get(int i, int j) {
		return g[i][j].getContent();
	}

	/**
	 * Gets the cell in a specified position
	 */
	public Cell getCell(int i, int j) {
		return g[i][j];
	}

	/**
	 * Metodo que hace bajar los elementos.
	 */
	public void fallElements() {
		boolean flag = false;
		int i = SIZE - 1;
		while (i >= 0) {
			int j = 0;
			while (j < SIZE) {
				if (g()[i][j].isEmpty()) {
					if (g()[i][j].fallUpperContent()) {
						flag = false;
						i = SIZE;
						j = -1;
						break;
					}
					flag = true;
				}
				j++;
			}
			i--;
		}
		/*Si no hay un elemento para bajar arriba de la celda baja una de sus diagonales superiores*/
		if (flag) {
			for (i = SIZE - 1; i >= 0; i--) {
				for (int j = 0; j < SIZE; j++) {
					if (g()[i][j].isEmpty()) {
						Cell left = g()[i][j].getAround()[Direction.LEFT.ordinal()];
						Cell right = g()[i][j].getAround()[Direction.RIGHT.ordinal()];
						if (isInBoard(left) && g()[i][j].fallUpperContent(left))
							fallElements();
						else if (isInBoard(right) && g()[i][j].fallUpperContent(right))
							fallElements();

					}
				}
			}

		}
	}


	/**
	 * Clears the content in a specified cell.
	 */
	public void clearContent(int i, int j) {
		g[i][j].clearContent();
	}

	/**
	 * Sets the content in a specified cell.
	 */
	public void setContent(int i, int j, Element e) {
		g[i][j].setContent(e);
	}

	/**
	 * Try move.
	 */
	public boolean tryMove(int i1, int j1, int i2, int j2) {
		if (!g[i1][j1].isMovable() || !g[i2][j2].isMovable()) {
			return false;
		}
		Move move = moveMaker.getMove(i1, j1, i2, j2);
		swapContent(i1, j1, i2, j2);
		if (move.isValid()) {
			move.removeElements();
			fallElements();
			return true;
		} else {
			swapContent(i1, j1, i2, j2);
			return false;
		}
	}

	/**
	 * Try remove.
	 */
	public Figure tryRemove(Cell cell) {
		if (gMap.containsKey(cell)) {
			Point p = gMap.get(cell);
			Figure f = figureDetector.checkFigure(p.x, p.y);
			if (f != null) {
				removeFigure(p.x, p.y, f);
			}
			return f;
		}
		return null;
	}

	/**
	 * Removes the figure.
	 */
	private void removeFigure(int i, int j, Figure f) {
		CandyColor color = ((Candy) get(i, j)).getColor();
		if (f.hasReplacement()) {
			setContent(i, j, f.generateReplacement(color));
		} else {
			clearContent(i, j);
		}
		for (Point p : f.getPoints()) {
			clearContent(i + p.x, j + p.y);
		}
	}

	/**
	 * Swap content.
	 */
	public void swapContent(int i1, int j1, int i2, int j2) {
		Element e = g[i1][j1].getContent();
		g[i1][j1].setContent(g[i2][j2].getContent());
		g[i2][j2].setContent(e);
		wasUpdated();
	}

	/**
	 * Creates the state.
	 */
	public GameState createState() {
		this.state = newState();
		return this.state;
	}

	/**
	 * Adds the listener.
	 */
	public void addListener(GameListener listener) {
		listeners.add(listener);
	}

	/**
	 * Was updated.
	 */
	public void wasUpdated() {
		if (listeners.size() > 0) {
			for (GameListener gl : listeners) {
				gl.gridUpdated();
			}
			delay(DELAY_MS);
		}
	}

	/**
	 * Cell explosion.
	 */
	public void cellExplosion(Element e) {
		for (GameListener gl : listeners) {
			gl.cellExplosion(e);
		}
	}

	/**
	 * Delay.
	 */
	public void delay(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException ie) {
		}
	}

	/**
	 * Checks if a cell is in the board.
	 */
	private boolean isInBoard(Cell cell){
		return !cell.getContent().isWall();
	}
}
