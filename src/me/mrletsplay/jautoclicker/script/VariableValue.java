package me.mrletsplay.jautoclicker.script;

public class VariableValue implements ScriptValue {

	private String variable;
	
	public VariableValue(String variable) {
		this.variable = variable;
	}

	@Override
	public int getValue(ScriptContext context) {
		return context.getVariable(variable);
	}
	
	@Override
	public String toString() {
		return variable;
	}
	
}
