package gl2.rendering;

import java.awt.Image;
import java.io.IOException;

import gl2.main.ResourceLoader;
import gl2.useful.DoublyLinkedHashMap;

/**
 * A container for an Image. Stores all existing sprites in a static hashmap for
 * efficient retrieving of duplicate sprites.
 * 
 * @author Heiko Ribberink
 * 
 * @see gl2.useful.DoublyLinkedHashMap
 * @see java.awt.Image
 */

public final class Sprite {

	// First key is imagePath, second key is nickname
	private static final DoublyLinkedHashMap<String, String, Sprite> allSprites = new DoublyLinkedHashMap<String, String, Sprite>();

	private final Image image;

	private Sprite(Image image) {
		this.image = image;
	}

	public Image getImage() {
		return image;
	}

	// Static functions

	/**
	 * Function for retrieving a sprite. This function allows for setting nicknames
	 * for sprites for easier retrieving.<br>
	 * If this function cannot find the sprite between the existing sprites, this
	 * function will create, add to the map of all sprites, and return a new sprite.
	 * If it is certain the sprite has already been created, use
	 * {@link #getSpriteByName(String)}
	 * 
	 * @param imagePath
	 * @param nickname
	 * @return
	 * @throws IOException
	 */

	public static Sprite loadSprite(String imagePath, String nickname) throws IOException, IllegalArgumentException {
		Sprite sprite;
		if (nickname != null) {
			sprite = allSprites.getByK2(nickname);
			if (sprite == null) {
				sprite = new Sprite(ResourceLoader.loadImage(imagePath));
				allSprites.put(imagePath, nickname, sprite);
			}
			return sprite;
		} else {
			throw new IllegalArgumentException("Nickname should always be provided.");
		}
	}

	/**
	 * Function for retrieving a sprite using a nickname. <br>
	 * This function should be used instead of {@link #getSprite(String, String)}
	 * when it is certain that the sprite has already been loaded.
	 * 
	 * @param nickname
	 * @return
	 */

	public static Sprite getSpriteByName(String nickname) {
		return allSprites.getByK2(nickname);
	}

	/**
	 * Function for retrieving a sprite by its file path. <br>
	 * This function will <em> NOT <em> load the sprite
	 * @param imagePath
	 * @return
	 * @throws IOException
	 */

	public static Sprite getSpriteByPath(String imagePath) throws IOException {
		return allSprites.getByK1(imagePath);
	}

}
