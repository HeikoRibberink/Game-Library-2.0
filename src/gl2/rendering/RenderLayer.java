package gl2.rendering;

import java.util.HashMap;

import gl2.object.component.texture.ObjectTextureComponent;

/**
 * Class used for efficient layering of objects.
 * @author Heiko Ribberink
 *
 */

public class RenderLayer {
	
	protected HashMap<ObjectTextureComponent, ObjectTextureComponent> textures = new HashMap<ObjectTextureComponent, ObjectTextureComponent>();
	protected boolean enabled = true;

	public RenderLayer() {
		// TODO Auto-generated constructor stub
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	

	

}
