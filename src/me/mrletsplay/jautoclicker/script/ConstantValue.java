package me.mrletsplay.jautoclicker.script;

public class ConstantValue implements ScriptValue {

	private int value;
	
	public ConstantValue(int value) {
		this.value = value;
	}

	@Override
	public int getValue(ScriptContext context) {
		return value;
	}
	
	@Override
	public String toString() {
		return String.valueOf(value);
	}
	
}
