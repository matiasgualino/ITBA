package game.frontend;
import java.io.File;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class FinalGame extends CenteredFrame {

	private static final long serialVersionUID = 1L;
	static private final int panelHeight = 250;
	static private final int panelWidth = 400;
	private CandyFrame gameframe;
	private JPanel mainPanel;
	
	public FinalGame(CandyFrame gameframe, String title,String img) {
		super(panelWidth,panelHeight,title);  
		this.gameframe=gameframe;
		
		//Background Panel
		mainPanel = new ImagePanel(panelWidth, panelHeight, "resources"+File.separator+"images"+File.separator+ img);
		add(mainPanel);
				
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        closeFrames();
		        }
		    });	
	}
	
	private void closeFrames(){
		gameframe.dispose();
		this.dispose();
	}
}
