package me.mrletsplay.jautoclicker;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

import me.mrletsplay.jautoclicker.script.ClickerScript;
import me.mrletsplay.jautoclicker.script.ConstantValue;
import me.mrletsplay.jautoclicker.script.MouseButton;
import me.mrletsplay.jautoclicker.script.ScriptParsingException;
import me.mrletsplay.jautoclicker.script.instruction.ClickInstruction;
import me.mrletsplay.jautoclicker.script.instruction.DelayInstruction;
import me.mrletsplay.jautoclicker.script.instruction.MoveMouseInstruction;
import me.mrletsplay.jautoclicker.script.instruction.ScriptInstruction;

public class JAutoClicker implements NativeKeyListener, NativeMouseInputListener {
	
	public static final JAutoClicker INSTANCE = new JAutoClicker();
	
	private static Thread clickerThread;
	private static int mouseX, mouseY;
	private static ClickerFrame clickerFrame;
	private static Robot robot;
	private static boolean recording;
	
	private static long lastRecordingClick;
	private static List<ScriptInstruction> recordingInstructions;
	
	protected static void init() throws NativeHookException {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch(UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {};
		
		clickerFrame = new ClickerFrame();
		
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
			return;
		}

		Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		logger.setLevel(Level.OFF);
		
		GlobalScreen.registerNativeHook();
		GlobalScreen.addNativeKeyListener(INSTANCE);
		GlobalScreen.addNativeMouseMotionListener(INSTANCE);
		GlobalScreen.addNativeMouseListener(INSTANCE);
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent nativeEvent) {
		
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent nativeEvent) {
		
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent nativeEvent) {
		if(nativeEvent.getKeyCode() == NativeKeyEvent.VC_F9) {
			if(isRunning()) {
				stop();
			}else {
				start();
			}
		}else if(nativeEvent.getKeyCode() == NativeKeyEvent.VC_F7) {
			if(isRecording()) {
				stopRecording();
			}else {
				startRecording();
			}
		}else if(nativeEvent.getKeyCode() == NativeKeyEvent.VC_F8) {
			try {
				ClickerScript sc = ClickerScript.parse(clickerFrame.getScriptText());
				sc.execute();
			}catch(ScriptParsingException e) {
				e.printStackTrace();
			}
		}else if(nativeEvent.getKeyCode() == NativeKeyEvent.VC_F10) {
			JOptionPane.showMessageDialog(clickerFrame, "Current Mouse Pos is (" + mouseX + "/" + mouseY + ")");
		}
	}

	@Override
	public void nativeMouseMoved(NativeMouseEvent nativeEvent) {
		mouseX = nativeEvent.getX() + Coordinates.screenOffsetX;
		mouseY = nativeEvent.getY() + Coordinates.screenOffsetY;
	}

	@Override
	public void nativeMouseDragged(NativeMouseEvent nativeEvent) {
		mouseX = nativeEvent.getX() + Coordinates.screenOffsetX;
		mouseY = nativeEvent.getY() + Coordinates.screenOffsetY;
	}
	
	@Override
	public void nativeMouseClicked(NativeMouseEvent nativeEvent) {
		
	}
	
	@Override
	public void nativeMousePressed(NativeMouseEvent nativeEvent) {
		if(recording) {
			if(lastRecordingClick != 0) {
				recordingInstructions.add(new DelayInstruction(new ConstantValue((int) (System.currentTimeMillis() - lastRecordingClick))));
			}
			recordingInstructions.add(new MoveMouseInstruction(new ConstantValue(getMouseX()), new ConstantValue(getMouseY())));
			
			MouseButton b = null;
			switch(nativeEvent.getButton()) {
				case NativeMouseEvent.BUTTON1:
				{
					b = MouseButton.LEFT;
					break;
				}
				case NativeMouseEvent.BUTTON2:
				{
					b = MouseButton.MIDDLE;
					break;
				}
				case NativeMouseEvent.BUTTON3:
				{
					b = MouseButton.RIGHT;
					break;
				}
			}
			if(b == null) return;
			
			recordingInstructions.add(new ClickInstruction(b));
			lastRecordingClick = System.currentTimeMillis();
		}
	}
	
	@Override
	public void nativeMouseReleased(NativeMouseEvent nativeEvent) {
		
	}
	
	public static void moveMouse(int x, int y) {
		robot.mouseMove(x, y);
	}
	
	public static void click(MouseButton button) throws InterruptedException {
		int cps = clickerFrame.getCps();
		if(cps == -1) return;
		
		int clickDelay = 1000 / cps;
		double cp = clickerFrame.getClickPercent();
		
		robot.mousePress(button.buttonDownMask); // TODO: Fix InterruptedException
		Thread.sleep(Math.max(1, (int) Math.floor(cp * clickDelay)));
		robot.mouseRelease(button.buttonDownMask);
		Thread.sleep(Math.max(1, (int) Math.floor((1 - cp) * clickDelay)));
	}
	
	public static int getMouseX() {
		return mouseX;
	}
	
	public static int getMouseY() {
		return mouseY;
	}
	
	public static void start() {
		if(isRunning()) return;
		
		int cps = clickerFrame.getCps();
		if(cps == -1) return;
		
		int clickDelay = 1000 / cps;
		double cp = clickerFrame.getClickPercent();
		
		boolean customPos = clickerFrame.isCustomClickPosition();
		
		int cPosX, cPosY;
		
		if(customPos) {
			cPosX = clickerFrame.getPosX();
			cPosY = clickerFrame.getPosY();
		}else {
			cPosX = -1;
			cPosY = -1;
		}
		
		clickerThread = new Thread(() -> {
			System.out.println("ClickerThread started!");
			while(!Thread.interrupted() && isRunning()) {
				try {
					if(customPos) robot.mouseMove(cPosX, cPosY);
					robot.mousePress(InputEvent.BUTTON1_DOWN_MASK); // TODO: Fix InterruptedException
					Thread.sleep(Math.max(1, (int) Math.floor(cp * clickDelay)));
					robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
					Thread.sleep(Math.max(1, (int) Math.floor((1 - cp) * clickDelay)));
				} catch (InterruptedException e) {
					break;
				}
			}
			robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK); // Release mouse just to be sure
			System.out.println("ClickerThread stopped!");
		});
		clickerThread.start();
	}
	
	public static boolean isRunning() {
		return clickerThread != null;
	}
	
	public static void stop() {
		if(!isRunning()) return;
		clickerThread.interrupt();
		clickerThread = null;
	}
	
	public static void startRecording() {
		recording = true;
		recordingInstructions = new ArrayList<>();
		lastRecordingClick = 0;
	}
	
	public static boolean isRecording() {
		return recording;
	}
	
	public static void stopRecording() {
		recording = false;
		clickerFrame.appendScriptText(new ClickerScript(recordingInstructions).toString());
	}

}
