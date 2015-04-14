package game.frontend;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScorePanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JLabel scoreLabel;

	/**
	 * Instantiates a new score panel.
	 * 
	 * @param width
	 *            the width of the panel
	 * @param height
	 *            the height of the panel
	 */
	public ScorePanel(int width, int height) {
		setSize(width, height);
		setBackground(Color.DARK_GRAY);
		scoreLabel = new JLabel("Puntaje: " + "0");
		scoreLabel.setBounds(1, 1, width, height);
		scoreLabel.setBackground(Color.DARK_GRAY);
		scoreLabel.setForeground(Color.GREEN);
		add(scoreLabel);
	}

	/**
	 * Updates the score.
	 * 
	 * @param text
	 *            the text
	 */
	public void updateScore(String text) {
		scoreLabel.setText("Puntaje: " + text);
	}

}