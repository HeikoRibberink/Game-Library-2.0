package gl2.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * A class for handling all key inputs from the keyboard using the
 * {@link java.awt.event.KeyListener KeyListener} interface.
 * 
 * @author Heiko Ribberink
 * @see KeyListener
 * @see KeyEvent
 *
 */

public class KeyManager implements KeyListener {

	/**
	 * Contains the current pressed status for each key. <br>
	 * To test if a key is pressed, use the static integer fields of
	 * {@link java.awt.event.KeyEvent KeyEvent} as the index to search the array.
	 */

	public static final boolean[] pressed = new boolean[1024];

	@Override
	public void keyPressed(KeyEvent e) {
		pressed[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		pressed[e.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

}