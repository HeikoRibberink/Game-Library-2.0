package gl2.rendering;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import gl2.math.Transform2D;
import gl2.object.component.camera.Camera;
import gl2.object.component.texture.ObjectTextureComponent;

/**
 * Class used for rendering scene view to a graphics object.
 * 
 * @author Heiko Ribberink
 * @see java.awt.Graphics2D
 *
 */

public class SceneRenderer implements Comparable<SceneRenderer> {

	private int renderPriority;
	private Camera camera;
	private boolean enabled = true;
	private static ArrayList<RenderLayer> layers = new ArrayList<RenderLayer>();

	public SceneRenderer(Camera camera, int renderPriority) {
		this.camera = camera;
		this.renderPriority = renderPriority;
	}

	/**
	 * Renders the current scene as viewed from the corresponding Camera to the
	 * given Graphics2D object.
	 * 
	 * @param g
	 * @see java.awt.Graphics2D
	 */

	public void render(Graphics2D g) {
		// Prepare some often used values
		AffineTransform afTr = g.getTransform();
		Transform2D camTr = camera.getParent().getTransform();

		// Transform graphics to camera transform
		g.rotate(camTr.getRotation());
		double xScale = camera.getPixelsPerUnit() / camTr.getSize().getX();
		double yScale = camera.getPixelsPerUnit() / camTr.getSize().getY();
		g.scale(xScale, yScale);
		g.translate(-camTr.getPosition().getX(), -camTr.getPosition().getY());

		// Render for each layer
		for (RenderLayer layer : layers) {
			if (layer.enabled) {
				// Render for each texture. This is not in any particular order
				for (ObjectTextureComponent otc : layer.textures.values()) {
					// Prepare some values
					AffineTransform tempAfTr = g.getTransform();
					Transform2D otcTr = otc.getTextureTransform();
					Sprite sprite = otc.getTexture();
					
					g.translate(otcTr.getPosition().getX(), otcTr.getPosition().getY());
					g.rotate(otcTr.getRotation());
					g.scale(otcTr.getSize().getX(), otcTr.getSize().getY());
					g.translate(-0.5, -0.5);
					g.drawImage(sprite.getImage(), 0, 0, 1, 1, null);
					g.setTransform(tempAfTr);
				}
			}
		}
		g.setTransform(afTr);
	}

	public static void addTexture(ObjectTextureComponent otc) {
		int layerIndex = otc.getLayerIndex();
		if (layers.size() <= layerIndex)
			addNewLayer(layerIndex);
		layers.get(layerIndex).textures.put(otc, otc);
	}

	public static void removeTexture(ObjectTextureComponent otc) {
		int layerIndex = otc.getLayerIndex();
		layers.get(layerIndex).textures.remove(otc);
	}

	private static RenderLayer addNewLayer(int layerIndex) {
		for (int i = layers.size(); i <= layerIndex; i++) {
			layers.add(new RenderLayer());
		}
		return layers.get(layerIndex);
	}

	// Basic functions

	@Override
	public int compareTo(SceneRenderer o) {
		if (o.renderPriority == renderPriority)
			return 0;
		if (o.renderPriority < renderPriority)
			return 1;
		return -1;
	}

	// Getters and setters

	public int getRenderPriority() {
		return renderPriority;
	}

	public void setRenderPriority(int renderPriority) {
		this.renderPriority = renderPriority;
	}

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
