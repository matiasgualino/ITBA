package game.backend;

import game.backend.cell.Cell;
import game.backend.element.Element;

public class CandyGame implements GameListener {
	
	private Class<?> levelClass;
	private Grid grid;
	private GameState state;
	
	/**
	 * Constructor.
	 * 
	 */
	public CandyGame(Class<?> clazz) {
		this.levelClass = clazz;
	}
	
	/**
	 * Inicia el juego
	 */
	public void initGame() {
		try {
			grid = (Grid)levelClass.newInstance();
		} catch(IllegalAccessException e) {
			System.out.println("ERROR AL INICIAR");
		} catch(InstantiationException e) {
			System.out.println("ERROR AL INICIAR");
		}
		state = grid.createState();
		grid.initialize();
		addGameListener(this);
	}
	
	/**
	 * Getter del grid.
	 */
	public Grid getGrid() {
		return grid;
	}
	
	/**
	 * Getter del tamaño de filas y columnas
	 */
	public int getSize() {
		return Grid.SIZE;
	}
	
	/**
	 * Try move.
	 */
	public boolean tryMove(int i1, int j1, int i2, int j2){
		return grid.tryMove(i1, j1, i2, j2);
	}
	
	/**
	 * Getter de una Cell en fila i columna j.
	 */
	public Cell get(int i, int j){
		return grid.getCell(i, j);
	}
	
	/**
	 * Adds the game listener.
	 */
	public void addGameListener(GameListener listener) {
		grid.addListener(listener);
	}
	
	/**
	 * Getter del score actual.
	 */
	public long getScore() {
		return state.getScore();
	}
	
	/**
	 * Getter de los movimientos hechos.
	 */
	public int getMoves(){
		return state.getMoves();
	}
	
	/**
	 * Getter del state.
	 */
	public GameState getState(){
		return this.state;
	}
	
	/**
	 * Checks if is finished.
	 */
	public boolean isFinished() {
		return state.gameOver();
	}
	
	/**
	 * Chequea si el jugador gano.
	 */
	public boolean playerWon() {
		return state.playerWon();
	}
	
	/**
	 * Suma el puntaje de explotar un caramelo.
	 */
	@Override
	public void cellExplosion(Element e) {
		state.addScore(e.getScore());
	}

	@Override
	public void gridUpdated() {
	}
}
