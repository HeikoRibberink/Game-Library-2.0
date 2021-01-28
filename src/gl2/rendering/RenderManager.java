package gl2.rendering;

import java.awt.Graphics2D;

public interface RenderManager extends Comparable<RenderManager> {
	
	public abstract void manageRender(Graphics2D g);
	public abstract int getRenderPriority();
	
}
