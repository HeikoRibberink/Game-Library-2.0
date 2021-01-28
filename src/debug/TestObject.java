package debug;

import java.awt.Graphics2D;

import gl2.main.GameState;
import gl2.main.Time;
import gl2.math.Transform2D;
import gl2.object.GameObject;
import gl2.object.GameObjectType;
import gl2.object.component.ObjectComponent;
import gl2.object.component.texture.SpriteTexture;

public class TestObject extends GameObject {

	public TestObject(Transform2D transform, int id, GameState state, ObjectComponent... components) {
		super(transform, id, GameObjectType.TestObject, state, components);
		spriteTC = (SpriteTexture) super.components.get(SpriteTexture.class).get(0);
	}
	
	private SpriteTexture spriteTC;
	double rot = Math.toRadians(60);

	@Override
	public void tick() {
		spriteTC.getOffset().rotate(rot * Time.tickDeltaTime);
		getTransform().rotate(rot * 0.33d * Time.tickDeltaTime);
	}

	@Override
	public void render(Graphics2D g) {
		
	}

}
