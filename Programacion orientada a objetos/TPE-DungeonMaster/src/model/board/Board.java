package model.board;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

import model.fighter.Fighter;
import model.fighter.Hero;

public abstract class Board {

	public static final int SIZE = 12;

	private Cell[][] g = new Cell[SIZE][SIZE];
	private Point heroPosition;
	private Set<Actionable> listeners = new HashSet<Actionable>();
	private Set<Fighter> enemies = new HashSet<Fighter>();

	public Board() {
		initialize();
	}

	public void initialize() {
		for (int x = 0; x < SIZE; x++) {
			for (int y = 0; y < SIZE; y++) {
				g[y][x] = new Cell();
			}
		}

		setContents();
		heroPosition = getHeroInitPosition();
		cleanFog(heroPosition);
	}

	public void heroMove(Move move) {
		if (getHero().isAlive()) {

			if (heroPosition.x + move.getX() > heroPosition.x)
				getHero().setMovementState("down");
			else if (heroPosition.x + move.getX() < heroPosition.x)
				getHero().setMovementState("up");
			else if (heroPosition.y + move.getY() > heroPosition.y)
				getHero().setMovementState("right");
			else if (heroPosition.y + move.getY() < heroPosition.y)
				getHero().setMovementState("left");

			Point newPosition = new Point(heroPosition.x + move.getX(),
					heroPosition.y + move.getY());

			if (newPosition.x >= 0 && newPosition.y >= 0
					&& newPosition.x < SIZE && newPosition.y < SIZE) {
				if (g[newPosition.y][newPosition.x].canWalkOver()) {
					g[newPosition.y][newPosition.x].onWalk(getHero());
					g[heroPosition.y][heroPosition.x].removeContent();

					if (newPosition != heroPosition) {
						alertMove();
					}

					heroPosition = newPosition;
					cleanFog(heroPosition);

				} else if (g[newPosition.y][newPosition.x].canInteract()) {
					g[newPosition.y][newPosition.x].interact(getHero());
					removeDeadEnemies();
				}
			}
		}
	}

	public void alertMove() {
		for (Actionable fg : listeners) {
			fg.alertMove();
		}
	}

	protected Set<Actionable> getActionables() {
		return this.listeners;
	}

	protected Set<Fighter> getEnemies() {
		return enemies;
	}

	protected void removeDeadEnemies() {

		Fighter aux = null;

		for (Fighter fg : enemies) {
			if (!fg.isAlive()) {
				aux = fg;
			}
		}

		if (aux != null) {
			enemies.remove(aux);
			listeners.remove(aux);
		}
	}

	private void cleanFog(Point p) {
		for (int i = p.y - 1; i <= p.y + 1; i++) {
			for (int j = p.x - 1; j <= p.x + 1; j++) {
				if (i >= 0 && i < SIZE && j >= 0 && j < SIZE) {
					if (g[i][j].hasFog()) {
						g[i][j].removeFog();
						getHero().heal(1);
					}
				}
			}
		}
	}

	private void cleanAllFog() {
		for (int i = 0; i <= SIZE; i++)
			for (int j = 0; j <= SIZE; j++) {
				if (g[i][j].hasFog()) {
					g[i][j].removeFog();
				}
			}
	}

	public Point getHeroPosition() {
		return heroPosition;
	}

	public Cell get(int x, int y) {
		return g[y][x];
	}

	public Hero getHero() {
		return (Hero) g[heroPosition.y][heroPosition.x].getContent();
	}

	protected Cell[][] g() {
		return g;
	}

	protected abstract void setContents();

	protected abstract Point getHeroInitPosition();

	public abstract boolean gameOver();

	public abstract boolean playerWon();

}
