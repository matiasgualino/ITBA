package game.frontend;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.*;

public abstract class CenteredFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private Toolkit toolkit;
	
	public CenteredFrame(int panelWidth, int panelHeight, String s) {

		super(s);  
		
		//Frame creation
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		setResizable(false);
		setSize(panelWidth,panelHeight); 
		
		//Sets the frame in the middle of the screen
		toolkit = getToolkit();
		Dimension size = toolkit.getScreenSize(); 
		setLocation(size.width/2 - getWidth()/2, size.height/2 - getHeight()/2);
		
		try {
			Image icon= ImageUtils.loadImage("resources"+File.separator+"images"+File.separator+"icon.png");
			setIconImage(icon);
			} catch (Exception e) {		
			showErrorMessage(e.getMessage(), "Error");
		}
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
