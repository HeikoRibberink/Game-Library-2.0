package gl2.main;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

/**
 * Use this class to load resources from files.
 * @author Heiko Ribberink
 *
 */

public class ResourceLoader {
	/**
	 * Use this function to load an Image from a file using the specified filename. <br> 
	 * It is preferable to use the functions provided by {@link gl2.rendering.Sprite Sprite} for loading images that will be used more than once, since they keep track of duplicate Images, thus saving storage bandwidth, and allowing for significantly faster load times.
	 * @param name
	 * @return the {@link java.awt.Image Image} retrieved from the specified file using the filename.
	 * @throws IOException
	 */
	public static Image loadImage(String name) throws IOException {
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream input = classLoader.getResourceAsStream(name);
			Image logo = ImageIO.read(input);
			return logo;
		} catch (Exception e) {
			File imgFile = new File(name);
			Image img;
			img = ImageIO.read(imgFile);
			return img;
		}
	}
}
