package frontend;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import backend.Board;
import backend.Cell;

public class ImageManager {

	private Map<Integer, Image> images;

	/**
	 * Instantiates a new image manager.
	 * 
	 * @param grid
	 *            the grid
	 */
	public ImageManager(Board grid) {
		
		images = new HashMap<Integer, Image>();

		try {
			images.put(0, ImageIO.read(new File("resources/images/zero.png")));
			images.put(1, ImageIO.read(new File("resources/images/one.png")));
			images.put(2, ImageIO.read(new File("resources/images/two.png")));
			images.put(3, ImageIO.read(new File("resources/images/three.png")));
			images.put(4, ImageIO.read(new File("resources/images/four.png")));
			images.put(5, ImageIO.read(new File("resources/images/five.png")));
			images.put(6, ImageIO.read(new File("resources/images/six.png")));
			images.put(7, ImageIO.read(new File("resources/images/seven.png")));
			images.put(8, ImageIO.read(new File("resources/images/eight.png")));
			images.put(9, ImageIO.read(new File("resources/images/nine.png")));
			images.put(10, ImageIO.read(new File("resources/images/ten.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the image.
	 * 
	 * @param cell
	 *            the cell
	 * @return the image
	 */
	public Image getImage(Cell cell) {
		return images.get(cell.getValue());
	}
}
