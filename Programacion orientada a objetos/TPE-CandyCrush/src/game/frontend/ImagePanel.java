package game.frontend;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private Image background;

	/**
	 * Instantiates a new image panel.
	 * 
	 * @param panelWidth
	 *            the panel width
	 * @param panelHeight
	 *            the panel height
	 * @param imagePath
	 *            the background image path
	 */
	public ImagePanel(int panelWidth, int panelHeight, String imagePath) {
		super();
		setLayout(null);
		setBounds(0, 0, panelWidth, panelHeight);
		try {
			background= ImageUtils.loadImage(imagePath);
		} catch(IOException e) {
			showErrorMessage(e.getMessage(), "System error");
		}
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
	}

	/**
	 * Show error message.
	 * 
	 * @param error
	 *            the error
	 * @param title
	 *            the title
	 */
	private void showErrorMessage(String error, String title) {
		JLabel errorMessage = new JLabel(error);
		errorMessage.setSize(errorMessage.getText().length() * 7, 80);
		errorMessage.setVisible(true);
		JFrame popup = new JFrame();
		Dimension size = popup.getToolkit().getScreenSize();
		popup.setTitle(title);
		popup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		popup.setSize(errorMessage.getSize());
		JPanel panel = new JPanel();
		panel.setSize(popup.getSize());
		popup.setLocation(size.width / 2 - popup.getWidth() / 2, size.height / 2 - popup.getHeight() / 2);
		panel.setBackground(new Color(255, 255, 255));
		panel.add(errorMessage);
		panel.setVisible(true);
		popup.add(panel);
		popup.setVisible(true);
		popup.setResizable(false);
	}
}
