package me.mrletsplay.jautoclicker.script;

import java.util.HashMap;
import java.util.Map;

public class ScriptContext {

	private Map<String, Integer>
		loopPoints,
		variables;
	
	private int currentInstruction;
	
	public ScriptContext() {
		this.loopPoints = new HashMap<>();
		this.variables = new HashMap<>();
	}
	
	public void setCurrentInstruction(int currentInstruction) {
		this.currentInstruction = currentInstruction;
	}
	
	public int getCurrentInstruction() {
		return currentInstruction;
	}
	
	public void putMarker(String name, int atInstruction) {
		loopPoints.put(name, atInstruction);
	}
	
	public int getMarker(String name) {
		return loopPoints.get(name);
	}
	
	public void setVariable(String name, int value) {
		variables.put(name, value);
	}
	
	public int getVariable(String name) {
		return variables.getOrDefault(name, 0);
	}
	
}
