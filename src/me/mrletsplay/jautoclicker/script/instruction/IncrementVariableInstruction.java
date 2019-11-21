package me.mrletsplay.jautoclicker.script.instruction;

import me.mrletsplay.jautoclicker.script.ScriptContext;

public class IncrementVariableInstruction implements ScriptInstruction {
	
	private String variable;

	public IncrementVariableInstruction(String variable) {
		this.variable = variable;
	}

	@Override
	public void execute(ScriptContext context) {
		context.setVariable(variable, context.getVariable(variable) + 1);
	}

	@Override
	public String toScriptText() {
		return "inc " + variable;
	}

}
