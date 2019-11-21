package me.mrletsplay.jautoclicker.script.instruction;

import me.mrletsplay.jautoclicker.JAutoClicker;
import me.mrletsplay.jautoclicker.script.MouseButton;
import me.mrletsplay.jautoclicker.script.ScriptContext;

public class ClickInstruction implements ScriptInstruction {

	private MouseButton button;
	
	public ClickInstruction(MouseButton button) {
		this.button = button;
	}
	
	public ClickInstruction() {
		this(MouseButton.LEFT);
	}
	
	@Override
	public void execute(ScriptContext context) {
		try {
			JAutoClicker.click(button);
		} catch (InterruptedException e) {}
	}

	@Override
	public String toScriptText() {
		return "click " + button.toString().toLowerCase();
	}
	
}
