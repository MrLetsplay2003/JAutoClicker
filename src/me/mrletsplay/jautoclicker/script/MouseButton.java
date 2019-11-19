package me.mrletsplay.jautoclicker.script;

import java.awt.event.InputEvent;

public enum MouseButton {

	LEFT(InputEvent.BUTTON1_DOWN_MASK),
	RIGHT(InputEvent.BUTTON3_DOWN_MASK),
	MIDDLE(InputEvent.BUTTON2_DOWN_MASK);
	
	public final int buttonDownMask;

	private MouseButton(int buttonDownMask) {
		this.buttonDownMask = buttonDownMask;
	}
	
}
