package gl2.object.instances;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import gl2.input.KeyManager;
import gl2.main.GameState;
import gl2.main.Time;
import gl2.math.Transform2D;
import gl2.math.vector.Vector2;
import gl2.object.GameObject;
import gl2.object.GameObjectType;
import gl2.object.component.ObjectComponent;

/**
 * A very simple class to allow for navigating around the scene.
 * @author Heiko Ribberink
 *
 */

public class DebugCamera extends GameObject {

	public DebugCamera(Transform2D transform, int id, GameState[] states,
			ObjectComponent... components) {
		super(transform, id, GameObjectType.DebugCamera, states, components);
		// TODO Auto-generated constructor stub
	}

	public DebugCamera(Transform2D transform, int id, GameState state,
			ObjectComponent... components) {
		super(transform, id, GameObjectType.DebugCamera, state, components);
		// TODO Auto-generated constructor stub
	}
	
	public DebugCamera(Transform2D transform, GameState...states) {
		super(transform, 0, GameObjectType.DebugCamera, states);
	}
	
	private double speed = 5d;
	
	@Override
	public void tick() {
		

	}

	@Override
	public void render(Graphics2D g) {
		if(KeyManager.pressed[KeyEvent.VK_L]) {
			transform.getPosition().add(new Vector2(speed * Time.frameDeltaTime * transform.getSize().getX(), 0));
		}
		if(KeyManager.pressed[KeyEvent.VK_J]) {
			transform.getPosition().add(new Vector2(-speed * Time.frameDeltaTime * transform.getSize().getX(), 0));
		}
		if(KeyManager.pressed[KeyEvent.VK_I]) {
			transform.getPosition().add(new Vector2(0, speed * Time.frameDeltaTime * transform.getSize().getY()));
		}
		if(KeyManager.pressed[KeyEvent.VK_K]) {
			transform.getPosition().add(new Vector2(0, -speed * Time.frameDeltaTime * transform.getSize().getY()));
		}
		if(KeyManager.pressed[KeyEvent.VK_U]) {
			transform.getSize().multiply(Math.pow(2 * speed, Time.frameDeltaTime));
		}
		if(KeyManager.pressed[KeyEvent.VK_O]) {
			transform.getSize().multiply(Math.pow(.5 / speed, Time.frameDeltaTime));
		}

	}

}
