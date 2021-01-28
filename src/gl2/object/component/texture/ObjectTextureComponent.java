package gl2.object.component.texture;

import java.awt.Graphics2D;

import gl2.math.Transform2D;
import gl2.object.component.ObjectComponent;
import gl2.rendering.SceneRenderer;
import gl2.rendering.Sprite;

public abstract class ObjectTextureComponent extends ObjectComponent {

	/**
	 * Tells the Renderer what layer this texture is in. The larger the number, the
	 * later it will be renderered, thus the "higher" it is. <br>
	 * Be careful with very large layer numbers, for each preceding layer index a
	 * layer will be created, which may result in large memory usage.
	 */

	protected int layerIndex;

	public ObjectTextureComponent(int layerIndex) {
		this.layerIndex = layerIndex;
	}
	
	@Override
	public void onAddedToComponents() {
		onEnabled();
	}
	@Override
	public void onRemovedFromComponents() {
		onDisabled();
	}
	@Override
	public void onDisabled() {
		SceneRenderer.removeTexture(this);
		onInvisible();
	}
	@Override
	public void onEnabled() {
		SceneRenderer.addTexture(this);
		onVisible();
	}

	@Override
	public void render(Graphics2D g) {
		// TODO Auto-generated method stub

	}

	// Abstract functions

	public abstract void tick();

	public abstract Transform2D getTextureTransform();

	public abstract Sprite getTexture();

	public abstract void onVisible();

	public abstract void onInvisible();

	// Getters and setters

	public int getLayerIndex() {
		return layerIndex;
	}

	public void setLayerIndex(int layerIndex) {
		this.layerIndex = layerIndex;
	}

}
