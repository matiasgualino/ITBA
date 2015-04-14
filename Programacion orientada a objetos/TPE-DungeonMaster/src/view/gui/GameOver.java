package view.gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class GameOver extends JFrame {

	private static final long serialVersionUID = 1L;


	public GameOver(){
		super("Game Over");
	    Toolkit toolkit = getToolkit();
	    Dimension size = toolkit.getScreenSize();
	    this.setSize(198, 98);
	    this.setLocation(size.width/2 - getWidth()/2, size.height/2 - getHeight()/2);
	    this.setResizable(false);
	    this.setAlwaysOnTop(true);
	    
	    setContentPane(new JLabel(new ImageIcon("resources/gameover.png")));
	    
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
