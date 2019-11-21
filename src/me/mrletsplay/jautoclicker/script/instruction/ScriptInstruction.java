package me.mrletsplay.jautoclicker.script.instruction;

import me.mrletsplay.jautoclicker.script.ScriptContext;

public interface ScriptInstruction {
	
	public void execute(ScriptContext context);
	
	public String toScriptText();

}
