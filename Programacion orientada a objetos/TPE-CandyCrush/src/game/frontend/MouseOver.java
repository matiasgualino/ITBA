package game.frontend;

import java.awt.event.*;

import javax.swing.JButton;

public class MouseOver extends MouseAdapter {
	
	private JButton b;
	
	/**
	 * Sets the button.
	 * 
	 * @param b
	 *            the new button
	 */
	public void setButton(JButton b){
		this.b = b;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseAdapter#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseAdapter#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent e) {
	}

}
