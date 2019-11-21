package me.mrletsplay.jautoclicker.script.instruction;

import me.mrletsplay.jautoclicker.script.ScriptContext;

public class DefineMarkerInstruction implements ScriptInstruction {
	
	private String name;
	
	public DefineMarkerInstruction(String name) {
		this.name = name;
	}

	@Override
	public void execute(ScriptContext context) {
		context.putMarker(name, context.getCurrentInstruction());
	}

	@Override
	public String toScriptText() {
		return "> " + name;
	}

}
