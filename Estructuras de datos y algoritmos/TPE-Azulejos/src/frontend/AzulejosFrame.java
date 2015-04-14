package frontend;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JFrame;

import backend.Board;
import backend.Cell;
import backend.GameState;
import backend.InvalidMovementException;

public class AzulejosFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final int CELL_SIZE = 65;
	private static final int SCORE_HEIGHT = 80;
	private BoardPanel bp;
	private ScorePanel scorep1;
	private ScorePanel scorep2;
	private ImageManager images;
	private GameListener listener;
	private AzulejosGame game;
	private Toolkit toolkit;

	public AzulejosFrame(final AzulejosGame game) {
		this.game = game;

		final Board board = game.getBoard();
		images = new ImageManager(board);

		setLayout(null);
		setSize(game.getState().getBoard().getCols() * CELL_SIZE, game
				.getState().getBoard().getRows()
				* CELL_SIZE + SCORE_HEIGHT);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// Sets the frame in the middle of the screen
		toolkit = getToolkit();
		Dimension size = toolkit.getScreenSize();
		setLocation(size.width / 2 - getWidth() / 2, size.height / 2
				- getHeight() / 2);

		bp = new BoardPanel(game.getState().getBoard().getRows(), game
				.getBoard().getCols(), CELL_SIZE);
		add(bp);

		scorep1 = new ScorePanel(bp.getWidth() / 2, SCORE_HEIGHT);
		scorep1.setLocation(1, bp.getHeight());
		add(scorep1);

		scorep2 = new ScorePanel(bp.getWidth() / 2, SCORE_HEIGHT);
		scorep2.setLocation(bp.getWidth() / 2, bp.getHeight());
		add(scorep2);

		// Initialize the game
		// game.initGame();

		game.addGameListener(listener = new GameListener() {
			@Override
			public void gridUpdated() {
				for (int i = 0; i < game().getState().getBoard().getRows(); i++) {
					for (int j = 0; j < game().getState().getBoard().getCols(); j++) {
						Cell cell = AzulejosFrame.this.game.get(i, j);
						bp.clearImage(i, j);
						Image image = images.getImage(cell);
						bp.setImage(i, j, image);
					}
				}
				bp.paintImmediately(bp.getBounds());
			}

			@Override
			public void cellExplosion(Cell e) {
			}
		});

		listener.gridUpdated();

		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				Point auxPoint = translateCoords(event.getX(), event.getY());
				try {
					GameState aux = game().getState().move(auxPoint.x,
							auxPoint.y);

					game().changeState(aux);

					String newScore1 = (new Integer(game().getState()
							.getPlayer1CurrentScore())).toString();
					String newScore2 = (new Integer(game().getState()
							.getPlayer2CurrentScore())).toString();
					scorep1.updateScore(newScore1);
					scorep2.updateScore(newScore2);
					game().gridUpdated();
					// turno de la maquina.
					game().changeState(game().getState().minimax());
					scorep1.updateScore((new Integer(game().getState()
							.getPlayer1CurrentScore())).toString());
					scorep2.updateScore((new Integer(game().getState()
							.getPlayer2CurrentScore())).toString());
					game().gridUpdated();

					if (game().isFinished()) {
						if (game().playerWon()) {
							won();
						} else {
							lost();
						}
						return;
					}
				} catch (InvalidMovementException | IOException e) {
					System.out.println("Movimiento prohibido.");
				}
			}
		});
	}

	private void lost() {
		JFrame frame = new FinalGame(this, "You lost", "lost.png");
		frame.setVisible(true);
	}

	private void won() {
		JFrame frame = new FinalGame(this, "You won!!!", "won.png");
		frame.setVisible(true);
	}

	/**
	 * Game.
	 * 
	 * @return the candy game
	 */
	private AzulejosGame game() {
		return game;
	}

	/**
	 * Translate coords.
	 * 
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @return the point
	 */
	private Point translateCoords(int x, int y) {
		int i = x / CELL_SIZE;
		int j = y / CELL_SIZE;
		return (i >= 0 && i < game.getState().getBoard().getCols() && j >= 0 && j < game
				.getState().getBoard().getRows()) ? new Point(j, i) : null;
	}
}