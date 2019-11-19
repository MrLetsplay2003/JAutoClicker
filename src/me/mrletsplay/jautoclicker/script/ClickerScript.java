package me.mrletsplay.jautoclicker.script;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import me.mrletsplay.jautoclicker.script.instruction.ClickInstruction;
import me.mrletsplay.jautoclicker.script.instruction.DelayInstruction;
import me.mrletsplay.jautoclicker.script.instruction.MoveMouseInstruction;
import me.mrletsplay.jautoclicker.script.instruction.ScriptInstruction;

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
					if(!isNumber(parts[1])) throw new ScriptParsingException("x needs to be a number");
					if(!isNumber(parts[2])) throw new ScriptParsingException("y needs to be a number");
					instrList.add(new MoveMouseInstruction(parts[1], parts[2]));
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
					if(parts.length != 2) throw new ScriptParsingException("click needs 0 args");
					if(!isNumber(parts[1])) throw new ScriptParsingException("delay needs to be a number");
					instrList.add(new DelayInstruction(Integer.parseInt(parts[1])));
					break;
				}
				default:
					throw new ScriptParsingException("Invalid instruction");
			}
		}
		return new ClickerScript(instrList);
	}
	
	private static boolean isNumber(String str) {
		try {
			Integer.parseInt(str);
			return true;
		}catch(Exception e) {
			return str.equals("mouse_x") || str.equals("mouse_y");
		}
	}
	
	public void execute() {
		for(ScriptInstruction instr : instructions) {
			instr.execute();
		}
	}
	
	@Override
	public String toString() {
		return instructions.stream()
				.map(ScriptInstruction::toScriptText)
				.collect(Collectors.joining("\n"));
	}

}
