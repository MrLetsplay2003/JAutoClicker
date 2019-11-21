package me.mrletsplay.jautoclicker.script.instruction;

import me.mrletsplay.jautoclicker.script.ScriptContext;
import me.mrletsplay.jautoclicker.script.ScriptValue;

public class DelayInstruction implements ScriptInstruction {

	private ScriptValue delay;

	public DelayInstruction(ScriptValue delay) {
		this.delay = delay;
	}

	@Override
	public void execute(ScriptContext context) {
		try {
			Thread.sleep(delay.getValue(context));
		} catch (InterruptedException e) {}
	}

	@Override
	public String toScriptText() {
		return "delay " + delay;
	}
	
}
