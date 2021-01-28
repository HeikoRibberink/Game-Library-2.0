package gl2.object.component.texture;

import java.awt.Graphics2D;

import gl2.math.Transform2D;
import gl2.rendering.Sprite;

/**
 * Component for rendering a Sprite.
 * 
 * @author Heiko Ribberink
 * @see gl2.rendering.Sprite
 */

public class SpriteTexture extends ObjectTextureComponent {

	private Sprite sprite;
	private Transform2D offset;

	public SpriteTexture(int layerIndex, Sprite sprite, Transform2D offset) {
		super(layerIndex);
		this.sprite = sprite;
		this.offset = offset;
	}

	// Supertype functions

	private Transform2D worldTransform = new Transform2D();

	@Override
	public Transform2D getTextureTransform() {
		worldTransform.set(offset);
		Transform2D parentTransform = parent.getTransform();
		parentTransform.toWorld(worldTransform.getPosition());
		worldTransform.rotate(parentTransform.getRotation());
		worldTransform.getSize().scaleUp(parentTransform.getSize());
		return worldTransform;
	}

	@Override
	public Sprite getTexture() {
		return sprite;
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(Graphics2D g) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onVisible() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onInvisible() {
		// TODO Auto-generated method stub

	}

	//Getters and setters
	
	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	public Transform2D getOffset() {
		return offset;
	}

	public void setOffset(Transform2D offset) {
		this.offset = offset;
	}

}
