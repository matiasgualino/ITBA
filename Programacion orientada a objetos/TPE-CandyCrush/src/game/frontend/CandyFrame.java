package game.frontend;

import game.backend.*;
import game.backend.cell.*;
import game.backend.element.*;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;


public class CandyFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private static final int CELL_SIZE = 65;
	private static final int SCORE_HEIGHT = 80;
	private BoardPanel bp;
	private ScorePanel sp;
	private MovePanel mp;
	private ImageManager images;
	private GameListener listener;
	private Point lastPoint;
	private CandyGame game;
	private Toolkit toolkit;

	/**
	 * Instantiates a new candy frame.
	 * 
	 * @param game
	 *            the game
	 */
	public CandyFrame(final CandyGame game) {
		this.game = game;

		final Grid grid = game.getGrid();
		images = new ImageManager(grid);

		setLayout(null);
		setSize(game.getSize() * CELL_SIZE, game.getSize() * CELL_SIZE + SCORE_HEIGHT);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// Sets the frame in the middle of the screen
		toolkit = getToolkit();
		Dimension size = toolkit.getScreenSize();
		setLocation(size.width / 2 - getWidth() / 2, size.height / 2
				- getHeight() / 2);

		bp = new BoardPanel(game.getSize(), game.getSize(), CELL_SIZE);
		add(bp);

		mp = new MovePanel(bp.getWidth() / 2, SCORE_HEIGHT);
		mp.setLocation(1, bp.getHeight());
		add(mp);

		sp = new ScorePanel(bp.getWidth() / 2, SCORE_HEIGHT);
		sp.setLocation(bp.getWidth() / 2, bp.getHeight());
		add(sp);

		// Sets the menu bar
		JMenuBar menuBar = new JMenuBar();
		JMenu speed = new JMenu("Speed");
		speed.setMnemonic(KeyEvent.VK_L);
		
		JMenuItem slow = new JMenuItem("Slow");
		slow.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				game.getGrid().changeSpeed(200);
			}
		});
		JMenuItem normal = new JMenuItem("Normal");
		normal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				game.getGrid().changeSpeed(100);

			}
		});
		JMenuItem fast = new JMenuItem("Fast");
		fast.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				game.getGrid().changeSpeed(50);
			}
		});
		
		speed.add(slow);
		speed.add(normal);
		speed.add(fast);
		menuBar.add(speed);
		setJMenuBar(menuBar);	
		
		//Initialize the game
		game.initGame(); 
		
		mp.updateMoves(game().getState().getMaxMoves());
		
		game.addGameListener(listener = new GameListener() {
			@Override
			public void gridUpdated() {
				for (int i = 0; i < game().getSize(); i++) {
					for (int j = 0; j < game().getSize(); j++) {
						Cell cell = CandyFrame.this.game.get(i, j);
						Element element = cell.getContent();
						bp.clearImage(i, j);
						Image image = images.getImage(element);
						Image image2 = images.getImage(cell);
						bp.setImage(i, j, image);
						bp.appendImage(i, j, image2);
					}
				}
				bp.paintImmediately(bp.getBounds());
			}

			@Override
			public void cellExplosion(Element e) {
			}
		});

		listener.gridUpdated();

		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				Point auxPoint = translateCoords(event.getX(), event.getY()-50);
				
				if (lastPoint == null) {
		
					// Highlight the selected candy
					Image img = null;
					try {
						img = ImageUtils.loadImage("resources/images/selected.png");
					} catch (IOException e) {
						e.printStackTrace();
					}
					bp.clearImage(auxPoint.x,auxPoint.y);
					bp.setImage(auxPoint.x, auxPoint.y, images.getImage(game().get(auxPoint.x, auxPoint.y).getContent()));
					bp.appendImage(auxPoint.x, auxPoint.y,images.getImage(game().get(auxPoint.x, auxPoint.y)));
					bp.appendImage(auxPoint.x, auxPoint.y, img);

					
					lastPoint = auxPoint;
					System.out.println("Get first = " + lastPoint);
				} else {
					Point newPoint = auxPoint;
					if (newPoint != null) {
						System.out.println("Get second = " + newPoint);
						game().tryMove(lastPoint.x, lastPoint.y, newPoint.x,
								newPoint.y);

						
						// Removes Highlight of the first candy
						bp.clearImage(lastPoint.x,lastPoint.y);
						bp.setImage(lastPoint.x, lastPoint.y, images.getImage(game().get(lastPoint.x, lastPoint.y).getContent()));
						bp.appendImage(lastPoint.x, lastPoint.y,images.getImage(game().get(lastPoint.x, lastPoint.y)));						
						
						
						String message = ((Long) game().getScore()).toString();
						if (game().isFinished()) {
							if (game().playerWon()) {
								won();
							} else {
								lost();
							}
						}
						sp.updateScore(message);
						mp.updateMoves(game().getState().getMaxMoves()-game().getMoves());
						lastPoint = null;
					}
				}
			}
		});

	}

	private void lost(){
		JFrame frame = new FinalGame(this,"You lost","lost.png");
		frame.setVisible(true);
	}
	
	private void won(){
		JFrame frame = new FinalGame(this,"You won!!!","won.png");
		frame.setVisible(true);
	}
	/**
	 * Game.
	 * 
	 * @return the candy game
	 */
	private CandyGame game() {
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
		return (i >= 0 && i < game.getSize() && j >= 0 && j < game.getSize()) ? new Point(
				j, i) : null;
	}
}
