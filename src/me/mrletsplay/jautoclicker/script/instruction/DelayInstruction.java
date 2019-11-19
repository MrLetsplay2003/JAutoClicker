package me.mrletsplay.jautoclicker.script.instruction;

public class DelayInstruction implements ScriptInstruction {

	private int delay;

	public DelayInstruction(int delay) {
		this.delay = delay;
	}

	@Override
	public void execute() {
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {}
	}

	@Override
	public String toScriptText() {
		return "delay " + delay;
	}
	
}
