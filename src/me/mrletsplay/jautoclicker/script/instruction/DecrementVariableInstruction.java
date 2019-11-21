package me.mrletsplay.jautoclicker.script.instruction;

import me.mrletsplay.jautoclicker.script.ScriptContext;

public class DecrementVariableInstruction implements ScriptInstruction {
	
	private String variable;

	public DecrementVariableInstruction(String variable) {
		this.variable = variable;
	}

	@Override
	public void execute(ScriptContext context) {
		context.setVariable(variable, context.getVariable(variable) - 1);
	}

	@Override
	public String toScriptText() {
		return "dec " + variable;
	}

}
