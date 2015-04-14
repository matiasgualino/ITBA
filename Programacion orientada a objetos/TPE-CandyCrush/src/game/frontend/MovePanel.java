package game.frontend;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MovePanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JLabel moveLabel;

	/**
	 * Instantiates the main panel for the game.
	 * 
	 * @param width
	 *            the width of the panel
	 * @param height
	 *            the height of the panel
	 */
	public MovePanel(int width, int height) {
		setSize(width, height);
		setBackground(Color.DARK_GRAY);
		moveLabel = new JLabel("Movimientos: " + "0");
		moveLabel.setBounds(1, 1, width, height);
		moveLabel.setBackground(Color.DARK_GRAY);
		moveLabel.setForeground(Color.GREEN);
		add(moveLabel);
	}

	/**
	 * Update moves.
	 * 
	 * @param moves
	 *            the move
	 */
	public void updateMoves(int moves) {
		moveLabel.setText("Movimientos: " + moves);
	}

}