package game.frontend;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;


public class BoardPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private int cellSize;
	private int rows, columns;
	private Image[][] cells;
	
	/**
	 * Instantiates a new board panel.
	 * 
	 * @param rows
	 *            the amount of rows
	 * @param columns
	 *            the amount of columns
	 * @param cellSize
	 *            the cell size
	 */
	public BoardPanel(final int rows, final int columns, final int cellSize) {
		this.rows = rows;
		this.columns = columns;
		this.cellSize = cellSize;
		this.cells = new Image[rows][columns];

		setSize(columns * cellSize + 1, rows * cellSize + 1);
		setBackground(Color.DARK_GRAY);
	}

	/**
	 * Clears image.
	 * 
	 * @param row
	 *            the row of the image
	 * @param column
	 *            the column of the image
	 */
	public void clearImage(int row, int column) {
		cells[row][column] = null;
	}
	
	/**
	 * Sets the image.
	 * 
	 * @param row
	 *            the row of the image
	 * @param column
	 *            the column of the image
	 * @param image
	 *            the image
	 */
	public void setImage(int row, int column, Image image) {
		cells[row][column] = new BufferedImage(cellSize, cellSize, BufferedImage.TYPE_INT_ARGB);
		cells[row][column].getGraphics().drawImage(image, 0, 0, null);
		repaint();
	}
	
	/**
	 * Append image.
	 * 
	 * @param row
	 *            the row of the image
	 * @param column
	 *            the column of the image
	 * @param image
	 *            the image
	 */
	public void appendImage(int row, int column, Image image) {
		if (cells[row][column] == null) {
			cells[row][column] = new BufferedImage(cellSize, cellSize, BufferedImage.TYPE_INT_ARGB);
		}
		cells[row][column].getGraphics().drawImage(image, 0, 0, null);
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				if (cells[i][j] != null) {
					g.drawImage(cells[i][j], j * cellSize, i * cellSize, null);
				}
			}
		}
	}
	
}