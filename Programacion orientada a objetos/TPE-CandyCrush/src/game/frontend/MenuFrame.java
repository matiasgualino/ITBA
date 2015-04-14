package game.frontend;

import game.backend.CandyGame;
import game.backend.level.*;

import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JPanel;


public class MenuFrame extends CenteredFrame {

	private static final long serialVersionUID = 1L;
	static private final int panelHeight = 500;
	static private final int panelWidth = 800;

	private JPanel mainPanel,levelPanel;
	Class<?> level;


	/**
	 * Instantiates a new frame for the menu.
	 * 
	 * @param s
	 *            the s
	 */
	public MenuFrame(String s) {
		super(panelWidth,panelHeight,s);  
		menuScreen();
	}

	/**
	 * Menu screen.
	 */
	public void menuScreen() {
		
		//Background Panel
		mainPanel = new ImagePanel(panelWidth, panelHeight, "resources"+File.separator+"images"+File.separator+"menubackground.png");
		add(mainPanel);

		//Levels Button
		String aux = new String("resources" + File.separator+"images"+ File.separator+"play");
		MenuButton levelsButton= new MenuButton(aux+".png",aux+"Over.png", 150,340);
		MouseOver levelsAction= new MouseOver() {
			@Override
			public void mouseClicked(MouseEvent e) {
				levelsScreen();
				setVisible(false);
				remove(mainPanel);
				levelsScreen();
			}
		};
		levelsAction.setButton(levelsButton);
		levelsButton.addMouseListener(levelsAction);
		mainPanel.add(levelsButton);
	}
	
	/**
	 * Levels screen.
	 */
	public void levelsScreen() {
		levelPanel = new ImagePanel(panelWidth, panelHeight, "resources"+File.separator+"images"+File.separator+"levelsmenubackground.png");
		add(levelPanel);
		setVisible(true);
		
		
		//Level1 Button
		String aux = new String("resources" + File.separator+"images"+ File.separator+"level1");
		MenuButton level1Button= new MenuButton(aux+".png",aux+"Over.png", 500,75);
		MouseOver level1Action= new MouseOver() {
			@Override
			public void mouseClicked(MouseEvent e) {
				level = Level1.class;
				initGame();
			}
		};
		level1Action.setButton(level1Button);
		level1Button.addMouseListener(level1Action);
		levelPanel.add(level1Button);
		
		//Level2 Button
		aux = new String("resources" + File.separator+"images"+ File.separator+"level2");
		MenuButton level2Button= new MenuButton(aux+".png",aux+"Over.png", 100,300);
		MouseOver level2Action= new MouseOver() {
			@Override
			public void mouseClicked(MouseEvent e) {
				level = Level2.class;
				initGame();
			}
		};
		level2Action.setButton(level2Button);
		level2Button.addMouseListener(level2Action);
		levelPanel.add(level2Button);
		
		//Level3 Button
		aux = new String("resources" + File.separator+"images"+ File.separator+"level3");
		MenuButton level3Button= new MenuButton(aux+".png",aux+"Over.png", 300, 300);
		MouseOver level3Action= new MouseOver() {
			@Override
			public void mouseClicked(MouseEvent e) {
				level = Level3.class;
				initGame();
			}
		};
		level3Action.setButton(level3Button);
		level3Button.addMouseListener(level3Action);
		levelPanel.add(level3Button);
		
		//Level4 Button
		aux = new String("resources" + File.separator+"images"+ File.separator+"level4");
		MenuButton level4Button= new MenuButton(aux+".png",aux+"Over.png", 500,300);
		MouseOver level4Action= new MouseOver() {
			@Override
			public void mouseClicked(MouseEvent e) {
				level = Level4.class;
				initGame();
			}
		};
		level4Action.setButton(level4Button);
		level4Button.addMouseListener(level4Action);
		levelPanel.add(level4Button);
		
	}
	
	/**
	 * Inits the game.
	 */
	private void initGame() {
		CandyGame game = new CandyGame(level);
		CandyFrame frame = new CandyFrame(game);
		frame.setVisible(true);
	}
	

}
