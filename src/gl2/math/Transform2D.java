package gl2.math;

import gl2.math.vector.Vector2;

/**
 * Base representation of the location of a GameObject. Includes position, size and rotation.
 * 
 * <br> Memory usage per object: 32 or 40 bytes (Header: 12, Variables: 16 or 24, Alignment: 4) (excludes all objects referenced)
 * @author Heiko Ribberink
 *
 */

public class Transform2D implements Cloneable {
	
	protected Vector2 position, size;
	protected double rotation;

	public Transform2D(double x, double y, double width, double height, double rotation) {
		position = new Vector2(x, y);
		size = new Vector2(width, height);
		this.rotation = rotation;
	}
	
	public Transform2D(Vector2 position, Vector2 size, double rotation) {
		this.position = position;
		this.size = size;
		this.rotation = rotation;
	}
	
	public Transform2D(double x, double y) {
		position = new Vector2(x, y);
		size = new Vector2(1, 1);
		rotation = 0;
	}
	
	public Transform2D() {
		position = new Vector2();
		size = new Vector2(1, 1);
		rotation = 0;
	}
	
	//Geometrical functions
	
	/**
	 * Transforms the given {@link gl2.math.vector.Vector2 Vector2} from local space to world space.
	 * @param v
	 * @return the given {@link gl2.math.vector.Vector2 Vector2} after it has been transformed from local space into world space.
	 */
	
	public Vector2 toWorld(Vector2 v) {
		v.scaleUp(size);
		v.add(position);
		v.rotate(rotation);
		return v;
	}
	
	/**
	 * Transforms the given {@link gl2.math.vector.Vector2 Vector2} from world space to local space.
	 * @param v
	 * @return the given {@link gl2.math.vector.Vector2 Vector2} after it has been transformed from world space into local space.
	 */
	
	public Vector2 toLocal(Vector2 v) {
		v.rotate(-rotation);
		v.subtract(position);
		v.scaleDown(size);
		return v;
	}
	
	public double rotate(double theta) {
		rotation += theta;
		return rotation;
	}

	//Standard functions

	@Override
	public Transform2D clone() {
		return new Transform2D(position.clone(), size.clone(), rotation);
	}
	
	@Override
	public String toString() {
		return "Transform2D: [x=" + position.getX() + ", y=" + position.getY() + ", width=" + size.getX() + ", height=" + size.getY() + ", rotation=" + rotation + "];";
	}
	
	//Getters and setters
	
	public Transform2D set(Transform2D tr) {
		position.set(tr.position);
		size.set(tr.size);
		rotation = tr.rotation;
		return this;
	}
	
	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public Vector2 getSize() {
		return size;
	}

	public void setSize(Vector2 size) {
		this.size = size;
	}

	public double getRotation() {
		return rotation;
	}

	public void setRotation(double rotation) {
		this.rotation = rotation;
	}

}
