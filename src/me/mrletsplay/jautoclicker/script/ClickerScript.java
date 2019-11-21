package me.mrletsplay.jautoclicker.script;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import me.mrletsplay.jautoclicker.JAutoClicker;
import me.mrletsplay.jautoclicker.script.instruction.ClickInstruction;
import me.mrletsplay.jautoclicker.script.instruction.DecrementVariableInstruction;
import me.mrletsplay.jautoclicker.script.instruction.DefineMarkerInstruction;
import me.mrletsplay.jautoclicker.script.instruction.DelayInstruction;
import me.mrletsplay.jautoclicker.script.instruction.GotoIfInstruction;
import me.mrletsplay.jautoclicker.script.instruction.GotoInstruction;
import me.mrletsplay.jautoclicker.script.instruction.IncrementVariableInstruction;
import me.mrletsplay.jautoclicker.script.instruction.MoveMouseInstruction;
import me.mrletsplay.jautoclicker.script.instruction.ScriptInstruction;
import me.mrletsplay.jautoclicker.script.instruction.SetVariableInstruction;

public class ClickerScript {
	
	private List<ScriptInstruction> instructions;
	
	public ClickerScript(List<ScriptInstruction> instructions) {
		this.instructions = instructions;
	}

	public static ClickerScript parse(String input) {
		List<ScriptInstruction> instrList = new ArrayList<>();
		for(String instr : input.split("\n")) {
			instr = instr.trim().toLowerCase();
			if(instr.isEmpty()) continue;
			String[] parts = instr.split(" ");
			System.out.println(">> " + Arrays.toString(parts));
			switch(parts[0]) {
				case "move_mouse":
				{
					if(parts.length != 3) throw new ScriptParsingException("move_mouse needs 2 args");
					instrList.add(new MoveMouseInstruction(makeNumber(parts[1]), makeNumber(parts[2])));
					break;
				}
				case "click":
				{
					if(parts.length > 2) throw new ScriptParsingException("click needs 0-1 args");
					MouseButton b = MouseButton.LEFT;
					if(parts.length == 2) {
						try {
							b = MouseButton.valueOf(parts[1].toUpperCase());
						}catch(IllegalArgumentException e) {
							throw new ScriptParsingException("Invalid button");
						}
					}
					instrList.add(new ClickInstruction(b));
					break;
				}
				case "delay":
				{
					if(parts.length != 2) throw new ScriptParsingException("click needs 1 arg");
					instrList.add(new DelayInstruction(makeNumber(parts[1])));
					break;
				}
				case ">":
				{
					if(parts.length != 2) throw new ScriptParsingException("> needs 1 arg");
					instrList.add(new DefineMarkerInstruction(parts[1]));
					break;
				}
				case "goto":
				{
					if(parts.length != 2) throw new ScriptParsingException("goto needs 1 arg");
					instrList.add(new GotoInstruction(parts[1]));
					break;
				}
				case "set":
				{
					if(parts.length != 3) throw new ScriptParsingException("set needs 2 args");
					instrList.add(new SetVariableInstruction(parts[1], makeNumber(parts[2])));
					break;
				}
				case "inc":
				{
					if(parts.length != 2) throw new ScriptParsingException("inc needs 1 arg");
					instrList.add(new IncrementVariableInstruction(parts[1]));
					break;
				}
				case "dec":
				{
					if(parts.length != 2) throw new ScriptParsingException("dec needs 1 arg");
					instrList.add(new DecrementVariableInstruction(parts[1]));
					break;
				}
				case "gotoif":
				{
					if(parts.length != 3) throw new ScriptParsingException("set needs 2 args");
					instrList.add(new GotoIfInstruction(parts[1], parts[2]));
					break;
				}
				default:
					throw new ScriptParsingException("Invalid instruction: " + instr);
			}
		}
		return new ClickerScript(instrList);
	}
	
	public void step(ScriptContext ctx) {
		ScriptInstruction ins = instructions.get(ctx.getCurrentInstruction());
		ctx.setVariable("mouse_x", JAutoClicker.getMouseX());
		ctx.setVariable("mouse_y", JAutoClicker.getMouseY());
		ins.execute(ctx);
		ctx.setCurrentInstruction(ctx.getCurrentInstruction() + 1);
	}
	
	private static ScriptValue makeNumber(String str) {
		try {
			int val = Integer.parseInt(str);
			return new ConstantValue(val);
		}catch(Exception e) {
			return new VariableValue(str);
		}
	}
	
	public void execute() {
		ScriptContext ctx = new ScriptContext();
		while(ctx.getCurrentInstruction() != instructions.size()) {
			step(ctx);
		}
	}
	
	@Override
	public String toString() {
		return instructions.stream()
				.map(ScriptInstruction::toScriptText)
				.collect(Collectors.joining("\n"));
	}

}
