package gl2.input;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class FocusManager implements FocusListener {
	
	private static boolean focused = false;

	public FocusManager() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void focusGained(FocusEvent e) {
		focused = true;

	}

	@Override
	public void focusLost(FocusEvent e) {
		focused = false;

	}

	public static boolean isFocused() {
		return focused;
	}	
	

}
