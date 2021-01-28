package gl2.math.vector;

import gl2.math.Mathf;

/**
 * Class used for representing and modifying 2D coordinates in rectangular form.
 * <br>
 * Memory usage per object: 32 or 40 bytes (Header: 12, Variables: 20 or 24,
 * Alignment = 0 or 4) (excludes all objects referenced)
 * 
 * @author Heiko Ribberink
 */

public class Vector2 implements Cloneable {

	protected double x = 0, y = 0;

	public Vector2(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Vector2() {
		x = 0;
		y = 0;
	}

	// Geometrical functions

	public Vector2 rotate(double theta) {
		double r = Math.cos(theta), i = Math.sin(theta);
		double newX = r * x - i * y;
		double newY = r * y + i * x;
		x = newX;
		y = newY;
		return this;
	}
	
	public Vector2 rotate(double cos, double sin) {
		double newX = cos * x - sin * y;
		double newY = cos * y + sin * x;
		x = newX;
		y = newY;
		return this;
	}
	
	public Vector2 add(Vector2 other) {
		x += other.x;
		y += other.y;
		return this;
	}

	public Vector2 subtract(Vector2 other) {
		x -= other.x;
		y -= other.y;
		return this;
	}

	public Vector2 scaleUp(Vector2 other) {
		x *= other.x;
		y *= other.y;
		return this;
	}

	public Vector2 scaleDown(Vector2 other) {
		x /= other.x;
		y /= other.y;
		return this;
	}

	public Vector2 multiply(double a) {
		x *= a;
		y *= a;
		return this;
	}

	public Vector2 normalize() {
		double invMag = Mathf.invSqrt(x * x + y * y);
		x *= invMag;
		y *= invMag;
		return this;
	}
	
	// Standard functions

	@Override
	public Vector2 clone() {
		return new Vector2(x, y);
	}

	@Override
	public String toString() {
		return "Vector2: [x=" + x + ", y=" + y + "];";
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Vector2)) return false;
		Vector2 v = (Vector2) o;
		return v.x == x && v.y == y;
	}

	// Getters and setters

	public Vector2 set(Vector2 v) {
		x = v.x;
		y = v.y;
		return this;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

}
