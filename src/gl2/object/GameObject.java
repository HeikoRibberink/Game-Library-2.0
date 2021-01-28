package gl2.object;

import java.awt.Graphics2D;

import gl2.main.GameState;
import gl2.math.Transform2D;
import gl2.object.component.ObjectComponent;
import gl2.useful.MultiHashMap;

/**
 * Base class for all objects in scene.
 * <br>
 * Memory usage per object: 32 or 48 (Header: 12, Values: 18 or 30, Alignment: 2
 * or 6) (excludes all objects referenced)
 * 
 * @author Heiko Ribberink
 *
 */

public abstract class GameObject {

	protected static Handler handler;
	private int objectIndex;

	protected Transform2D transform;
	protected int id;
	protected GameObjectType type;
	protected GameState[] states;
	private boolean enabled = true;
	protected MultiHashMap<Class<?>, ObjectComponent> components = new MultiHashMap<Class<?>, ObjectComponent>();
	private boolean initialized = false;

	public GameObject(Transform2D transform, int id, GameObjectType type, GameState[] states, ObjectComponent... components) {
		this.transform = transform;
		this.id = id;
		this.type = type;
		this.states = states;
		for(ObjectComponent component : components) {
			addComponents(component);
		}
	}

	public GameObject(Transform2D transform, int id, GameObjectType type, GameState state, ObjectComponent... components) {
		this.transform = transform;
		this.id = id;
		this.type = type;
		this.states = new GameState[] { state };
		for(ObjectComponent component : components) {
			addComponents(component);
		}
	}

	public final void manageTick() {
		if(!initialized) initialize();
		for(ObjectComponent c : components) {
			if(c.isEnabled()) c.tick();
		}
		tick();
	}

	public final void manageRender(Graphics2D g) {
		for(ObjectComponent c : components) {
			if(c.isEnabled()) c.render(g);
		}
		render(g);
	}

	protected final void onAddObjectToEnabled(int arrayIndex) {
		objectIndex = arrayIndex;
	}
	

	public final void addComponents(ObjectComponent...cs) {
		for(ObjectComponent c : cs) {
			components.put(c.getClass(), c);
			c.setParent(this);
			c.onAddedToComponents();
		}
	}
	
	private void initialize() {
		for(ObjectComponent c : components) {
			c.onAddedToComponents();
		}
		initialized = true;
	}
	
	protected void onEnabled() {
		
	}
	
	protected void onDisabled() {
		
	}

	// Abstract functions

	/**
	 * Is called every tick. <br>
	 * Ticks per second can be adjusted in the Time enum
	 * 
	 * @see gl2.main.Time
	 */
	public abstract void tick();

	/**
	 * Is called every frame.
	 * 
	 * @param g
	 */
	public abstract void render(Graphics2D g);

	// Getters and setters

	public GameState[] getStates() {
		return states;
	}

	public void setStates(GameState[] states) {
		this.states = states;
	}

	public Transform2D getTransform() {
		return transform;
	}

	public void setTransform(Transform2D transform) {
		this.transform = transform;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public GameObjectType getType() {
		return type;
	}

	public void setType(GameObjectType type) {
		this.type = type;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		if (enabled && !this.enabled) {
			handler.addGameObjectToEnabled(this);
			for(ObjectComponent c : components) {
				c.setEnabled(true);
			}
			onEnabled();
		} else if(!enabled && this.enabled) {
			handler.enabledGameObjects.remove(objectIndex);
			onDisabled();
			for(ObjectComponent c : components) {
				c.setEnabled(false);
			}
		}
		this.enabled = enabled;
	}

	public MultiHashMap<Class<?>, ObjectComponent> getComponents() {
		return components;
	}

	public void setComponents(MultiHashMap<Class<?>, ObjectComponent> components) {
		this.components = components;
	}

}
