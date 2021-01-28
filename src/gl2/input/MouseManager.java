package gl2.input;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import gl2.main.Game;

/**
 * A class for handling most computer mouse inputs using the
 * {@link java.awt.event.MouseAdapter MouseAdapter} class.
 * 
 * @author Heiko Ribberink
 *
 */

public class MouseManager extends MouseAdapter {

	/**
	 * Contains the current pressed status for each mouse button. <br>
	 * To test if a button is pressed, use the static integer fields of
	 * {@link java.awt.event.MouseEvent MouseEvent} as the index to search the
	 * array.
	 * 
	 * @see java.awt.event.MouseEvent
	 */

	public static final boolean[] buttons = new boolean[32];

	@Override
	public void mousePressed(MouseEvent e) {
		buttons[e.getButton()] = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		buttons[e.getButton()] = false;
	}

	/**
	 * Used for retrieving the location of the cursor.
	 * 
	 * @return the current location of the cursor relative to the origin of the
	 *         current window as a {@link java.awt.Point Point}.
	 */

	public static Point getMousePosition() {
		return new Point(
				MouseInfo.getPointerInfo().getLocation().x
						- Game.getGame().getWindow().getComponents()[0].getLocationOnScreen().x,
				MouseInfo.getPointerInfo().getLocation().y
						- Game.getGame().getWindow().getComponents()[0].getLocationOnScreen().y);
	}
	
	private static int mouseWheelRotation = 0;
	
	/**
	 * @return the current rotation of the mouse wheel in integer precision.
	 */
	
	public static int getMouseWheelRotation() {
		return mouseWheelRotation;
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		mouseWheelRotation += e.getWheelRotation();
	}

}
