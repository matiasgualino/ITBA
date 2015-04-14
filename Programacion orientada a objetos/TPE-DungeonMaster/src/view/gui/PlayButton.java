package view.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.MouseAdapter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JButton;
import javax.swing.JOptionPane;

public class PlayButton extends JButton {

	private static final long serialVersionUID = 1L;

	PlayButton(){
        setBorder(null);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
        setFocusPainted(false);
		setBounds(205, 180, 100, 50);
		setText("Jugar");
		
		try {
			InputStream is = new FileInputStream("resources/goticaBastard.ttf");
			Font GoticaBastard = Font.createFont(Font.TRUETYPE_FONT, is);
			GoticaBastard = GoticaBastard.deriveFont(32f);
			setForeground(Color.WHITE);
		    setFont(GoticaBastard);
			
		} catch (FontFormatException | IOException e) {
			JOptionPane.showMessageDialog(null, "Error al cargar datos graficos", "Error", JOptionPane.ERROR_MESSAGE);
		}
		
		addMouseListener(new MouseAdapter() { 
	          
	          public void mouseEntered(java.awt.event.MouseEvent evt) {
	        	  setForeground(Color.GRAY);
	          		}
	          
	          public void mouseExited(java.awt.event.MouseEvent evt) {
	        	  setForeground(Color.WHITE);
	        	  }
	          
		        });
	}

}