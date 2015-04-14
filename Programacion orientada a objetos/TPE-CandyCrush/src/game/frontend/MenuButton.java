package game.frontend;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class MenuButton extends JButton {

	private static final long serialVersionUID = 1L;
	private static final int width=159;
	private static final int height=118;
	

	/**
	 * Instantiates a new menu button.
	 * 
	 * @param fileimg1
	 *            the original image
	 * @param fileimg2
	 *            the rollover image
	 * @param x
	 *            the x location
	 * @param y
	 *            the y location
	 */
	public MenuButton(final String fileimg1, final String fileimg2, int x, int y) {
		super(new ImageIcon(fileimg1));

		setBorder(BorderFactory.createEmptyBorder());

        addMouseListener(new MouseOver() {
        	
			@Override
			public void mouseEntered(MouseEvent e) {
				setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				setRolloverIcon(new ImageIcon(fileimg2));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				setRolloverIcon(new ImageIcon(fileimg1));
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
     
		setBounds(x, y, width, height);
	}
}
