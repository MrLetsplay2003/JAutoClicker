package me.mrletsplay.jautoclicker.script.instruction;

import me.mrletsplay.jautoclicker.JAutoClicker;

public class MoveMouseInstruction implements ScriptInstruction {

	private String x, y;
	
	public MoveMouseInstruction(String x, String y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void execute() {
		int fX = 0, fY = 0;
		if(x.equals("mouse_x")) {
			fX = JAutoClicker.getMouseX();
		}else {
			fX = Integer.parseInt(x);
		}
		
		if(y.equals("mouse_y")) {
			fY = JAutoClicker.getMouseY();
		}else {
			fY = Integer.parseInt(y);
		}
		
		JAutoClicker.moveMouse(fX, fY);
	}

	@Override
	public String toScriptText() {
		return "move_mouse " + x + " " + y;
	}

	
}
