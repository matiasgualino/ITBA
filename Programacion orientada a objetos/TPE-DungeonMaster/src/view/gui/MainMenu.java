package view.gui;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import model.Game;
import model.board.level.GameLevelX;

import sound.OggClip;

import view.Main;

public class MainMenu extends JFrame {

	private OggClip ogg;

	private static final long serialVersionUID = 1L;

	public MainMenu(){
		super("Desktop Dungeon");
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setResizable(false);
	    Toolkit toolkit = getToolkit();
	    Dimension size = toolkit.getScreenSize();
	    this.setSize(512, 393);
	    this.setLocation(size.width/2 - getWidth()/2, size.height/2 - getHeight()/2);

	    setContentPane(new JLabel(new ImageIcon("resources/mainMenu.png")));

	    File sourceimage = new File("resources/icon.png");
		try {
			Image image = ImageIO.read(sourceimage);
			this.setIconImage(image);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    
	    
	    
	    PlayButton playButton = new PlayButton();
	    
	    playButton.addMouseListener(new MouseAdapter() { 
	          public void mousePressed(MouseEvent me) { 
	        	  MainMenu.this.setVisible(false);
	        	  MainMenu.this.ogg.stop();
	        	  
	        	  try {
	        		  Main mainWindow = new Main(new Game(GameLevelX.class));
	        		  mainWindow.setVisible(true);
	        	  } catch (InstantiationException | IllegalAccessException e) {
	        		  JOptionPane.showMessageDialog(null, "Error al cargar datos del nivel", "Error", JOptionPane.ERROR_MESSAGE);
	        	  }
	      		  
	          }
	    });
	    
	    LevelButton levelButton = new LevelButton();

	    levelButton.addMouseListener(new MouseAdapter() { 
	          public void mousePressed(MouseEvent me) { 
	        	  MainMenu.this.setVisible(false);
	        	  MainMenu.this.ogg.stop();
	        	  
	        	  LevelMenu levelWindow = new LevelMenu();
	        	  levelWindow.setVisible(true);
	        	  
	          }
	    });
	    
	    
	    
	    add(playButton);
	    add(levelButton);
		
	    try {
			this.ogg = new OggClip(new FileInputStream("resources/intro.ogg"));
			this.ogg.loop();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error al cargar audio", "Error", JOptionPane.ERROR_MESSAGE);
		}
	    
	}
	
	public static void main(String[] args) {
		MainMenu mainMenu = new MainMenu();
		mainMenu.setVisible(true);
	}

	
	
}
