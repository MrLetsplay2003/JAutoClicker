package me.mrletsplay.jautoclicker.script.instruction;

import me.mrletsplay.jautoclicker.JAutoClicker;
import me.mrletsplay.jautoclicker.script.ScriptContext;
import me.mrletsplay.jautoclicker.script.ScriptValue;

public class MoveMouseInstruction implements ScriptInstruction {

	private ScriptValue
		x,
		y;
	
	public MoveMouseInstruction(ScriptValue x, ScriptValue y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void execute(ScriptContext context) {
		JAutoClicker.moveMouse(x.getValue(context), y.getValue(context));
	}

	@Override
	public String toScriptText() {
		return "move_mouse " + x + " " + y;
	}

	
}
