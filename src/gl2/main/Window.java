package gl2.main;

import java.awt.Canvas;
import java.awt.Point;

import javax.swing.JFrame;

/**
 * Used for simplifying the creation and use of virtual windows using {@link javax.swing.JFrame JFrame}.
 * @author Heiko Ribberink
 *
 */

public class Window extends JFrame {
	private static final long serialVersionUID = 1L;
	private Canvas canvas;

	/**
	 * Creates a new virtual window with a specified title, width and height.
	 * @param title
	 * @param width
	 * @param height
	 */
	
	public Window(String title, int width, int height) {
		setSize(width, height);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle(title);
		setVisible(true);
		setLocation(new Point(0, 0));
		
		canvas = new Canvas();
		canvas.setSize(width, height);
		canvas.setFocusable(true);
		add(canvas);
	}

	public Canvas getCanvas() {
		return canvas;
	}

	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}
}
