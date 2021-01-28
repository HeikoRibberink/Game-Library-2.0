package gl2.object.component.camera;

import java.awt.Graphics2D;

import gl2.object.component.ObjectComponent;

/**
 * Class used in {@link gl2.rendering.SceneRenderer SceneRenderer} for rendering a scene view. Use this class and its underlying object to specify the position, rotation and size of a scene view.
 * @author Heiko Ribberink
 *
 */
public class Camera extends ObjectComponent {
	
	private double pixelsPerUnit = 100;

	public Camera() {
		super();
	}
	
	@Override
	public void tick() {
		
	}

	@Override
	public void render(Graphics2D g) {
		
	}

	public double getPixelsPerUnit() {
		return pixelsPerUnit;
	}

	public void setPixelsPerUnit(double pixelsPerUnit) {
		this.pixelsPerUnit = pixelsPerUnit;
	}

	
	
}
