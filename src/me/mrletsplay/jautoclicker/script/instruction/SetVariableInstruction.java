package me.mrletsplay.jautoclicker.script.instruction;

import me.mrletsplay.jautoclicker.script.ScriptContext;
import me.mrletsplay.jautoclicker.script.ScriptValue;

public class SetVariableInstruction implements ScriptInstruction {
	
	private String variable;
	private ScriptValue value;

	public SetVariableInstruction(String variable, ScriptValue value) {
		this.variable = variable;
		this.value = value;
	}

	@Override
	public void execute(ScriptContext context) {
		context.setVariable(variable, value.getValue(context));
	}

	@Override
	public String toScriptText() {
		return "set " + variable + " " + value;
	}

}
