package me.mrletsplay.jautoclicker.script.instruction;

import me.mrletsplay.jautoclicker.script.ScriptContext;

public class GotoIfInstruction implements ScriptInstruction {
	
	private String name, variable;
	
	public GotoIfInstruction(String name, String variable) {
		this.name = name;
		this.variable = variable;
	}

	@Override
	public void execute(ScriptContext context) {
		if(context.getVariable(variable) > 0) {
			context.setCurrentInstruction(context.getMarker(name));
		}
	}

	@Override
	public String toScriptText() {
		return "gotoif " + name + " " + variable;
	}

}
