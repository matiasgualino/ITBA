package game.frontend;

import game.backend.Grid;
import game.backend.cell.Cage;
import game.backend.cell.Cell;
import game.backend.cell.Jelly;
import game.backend.cell.WeirdWall;
import game.backend.element.Bomb;
import game.backend.element.Candy;
import game.backend.element.CandyColor;
import game.backend.element.Element;
import game.backend.element.HorizontalStripedCandy;
import game.backend.element.Nothing;
import game.backend.element.VerticalStripedCandy;
import game.backend.element.Wall;
import game.backend.element.WrappedCandy;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class ImageManager {

	private Map<String, Image> images;

	/**
	 * Instantiates a new image manager.
	 * 
	 * @param grid
	 *            the grid
	 */
	public ImageManager(Grid grid) {
		
		WrappedCandy wc = new WrappedCandy();
		VerticalStripedCandy vc = new VerticalStripedCandy();
		HorizontalStripedCandy hc = new HorizontalStripedCandy();
		
		images = new HashMap<String, Image>();

		try {
			images.put(new Jelly(grid).getKey(), ImageIO.read(new File("resources/images/jelly.png")));
			images.put(new WeirdWall(grid).getKey(), ImageIO.read(new File("resources/images/transparent.png")));
			images.put(new Cell(grid).getKey(), ImageIO.read(new File("resources/images/transparent.png")));
			images.put(new Nothing().getKey(), ImageIO.read(new File("resources/images/nothing.png")));
			images.put(new Bomb().getKey(), ImageIO.read(new File("resources/images/bomb.png")));
			images.put(new Wall().getKey(), ImageIO.read(new File("resources/images/wall.png")));
			images.put(new Cage(grid).getKey(), ImageIO.read(new File("resources/images/cage.png")));
			
			for (CandyColor cc: CandyColor.values()) {
				images.put(new Candy(cc).getFullKey(), ImageIO.read(new File("resources/images/" + cc.toString().toLowerCase() + "Candy.png")));
			}
			
			for (CandyColor cc: CandyColor.values()) {
				wc.setColor(cc);
				images.put(wc.getFullKey(), ImageIO.read(new File("resources/images/" + cc.toString().toLowerCase() + "Wrapped.png")));
			}
			
			for (CandyColor cc: CandyColor.values()) {
				vc.setColor(cc);
				images.put(vc.getFullKey(), ImageIO.read(new File("resources/images/" + cc.toString().toLowerCase() + "VStripped.png")));
			}

			for (CandyColor cc: CandyColor.values()) {
				hc.setColor(cc);
				images.put(hc.getFullKey(), ImageIO.read(new File("resources/images/" + cc.toString().toLowerCase() + "HStripped.png")));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the image.
	 * 
	 * @param e
	 *            the element
	 * @return the image
	 */
	public Image getImage(Element e) {
		return images.get(e.getFullKey());
	}
	
	/**
	 * Gets the image.
	 * 
	 * @param cell
	 *            the cell
	 * @return the image
	 */
	public Image getImage(Cell cell) {
		return images.get(cell.getKey());
	}
}
