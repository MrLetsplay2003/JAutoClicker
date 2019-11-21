package me.mrletsplay.jautoclicker.script;

import java.util.HashMap;
import java.util.Map;

public class ScriptContext {
	
	private ClickerScript script;

	private boolean exit;
	
	private Map<String, Integer>
		loopPoints,
		variables;
	
	private int currentInstruction;
	
	public ScriptContext(ClickerScript script) {
		this.script = script;
		this.loopPoints = new HashMap<>();
		this.variables = new HashMap<>();
	}
	
	public ClickerScript getScript() {
		return script;
	}
	
	public void setExit(boolean exit) {
		this.exit = exit;
	}
	
	public boolean isExit() {
		return exit;
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
