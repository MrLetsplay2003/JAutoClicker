package me.mrletsplay.jautoclicker;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
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
import org.jnativehook.mouse.NativeMouseMotionListener;

public class JAutoClicker implements NativeKeyListener, NativeMouseMotionListener {
	
	public static final JAutoClicker INSTANCE = new JAutoClicker();
	
	private static Thread clickerThread;
	private static int mouseX, mouseY;
	private static ClickerFrame clickerFrame;
	private static Robot robot;
	
	public static void main(String[] args) throws NativeHookException {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch(UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {};
		clickerFrame = new ClickerFrame();

		Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		logger.setLevel(Level.OFF);
		
		GlobalScreen.registerNativeHook();
		GlobalScreen.addNativeKeyListener(INSTANCE);
		GlobalScreen.addNativeMouseMotionListener(INSTANCE);
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
		}else if(nativeEvent.getKeyCode() == NativeKeyEvent.VC_F10) {
			JOptionPane.showMessageDialog(clickerFrame, "Current Mouse Pos is (" + mouseX + "/" + mouseY + ")");
		}
	}

	@Override
	public void nativeMouseMoved(NativeMouseEvent nativeEvent) {
		mouseX = nativeEvent.getX();
		mouseY = nativeEvent.getY();
	}

	@Override
	public void nativeMouseDragged(NativeMouseEvent nativeEvent) {
		mouseX = nativeEvent.getX();
		mouseY = nativeEvent.getY();
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
		System.out.println(clickerFrame.isCustomClickPosition());
		
		int cPosX, cPosY;
		
		if(customPos) {
			cPosX = clickerFrame.getPosX();
			cPosY = clickerFrame.getPosY();
		}else {
			cPosX = -1;
			cPosY = -1;
		}
		
		if(robot == null) {
			try {
				robot = new Robot();
			} catch (AWTException e) {
				e.printStackTrace();
				return;
			}
		}
		
		clickerThread = new Thread(() -> {
			System.out.println("ClickerThread started!");
			while(!Thread.interrupted() && isRunning()) {
				try {
					if(customPos) robot.mouseMove(cPosX, cPosY);
					robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
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

}
