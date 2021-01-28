package gl2.object.component;

import java.awt.Graphics2D;

import gl2.object.GameObject;

/**
 * Base class for all {@link gl2.object.GameObject GameObject} components.
 * @author Heiko Ribberink
 *
 */

public abstract class ObjectComponent implements Cloneable {

	protected GameObject parent;
	protected boolean enabled = true;

	public ObjectComponent() {
		
	}

	// Abstract functions

	public abstract void tick();

	public abstract void render(Graphics2D g);
	
	public void onAddedToComponents() {
		
	}
	
	public void onRemovedFromComponents() {
		
	}
	
	public void onEnabled() {
		
	}
	
	public void onDisabled() {
		
	}

	// Getters and setters

	public GameObject getParent() {
		return parent;
	}

	public void setParent(GameObject parent) {
		this.parent = parent;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		if(this.enabled == enabled) return;
		if(enabled) onEnabled();
		else onDisabled();
		this.enabled = enabled;
	}
}
