package game.frontend;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ImageUtils {

	/**
	 * Load image.
	 * 
	 * @param fileName
	 *            the file name
	 * @return the image
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static Image loadImage(String fileName) throws IOException {
		InputStream stream = ClassLoader.getSystemResourceAsStream(fileName);
		if (stream == null) {
			return ImageIO.read(new File(fileName));
		} else {
			return ImageIO.read(stream);
		}
	}

}
