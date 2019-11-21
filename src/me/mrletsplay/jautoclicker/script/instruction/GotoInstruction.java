package me.mrletsplay.jautoclicker.script.instruction;

import me.mrletsplay.jautoclicker.script.ScriptContext;

public class GotoInstruction implements ScriptInstruction {
	
	private String name;
	
	public GotoInstruction(String name) {
		this.name = name;
	}

	@Override
	public void execute(ScriptContext context) {
		context.setCurrentInstruction(context.getMarker(name));
	}

	@Override
	public String toScriptText() {
		return "goto " + name;
	}

}
