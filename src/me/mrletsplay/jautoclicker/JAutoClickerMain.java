package me.mrletsplay.jautoclicker;

import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

import org.jnativehook.NativeHookException;

public class JAutoClickerMain {
	
	public static void main(String[] args) throws NativeHookException {
		JAutoClicker.init();
		
		Rectangle r = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().getBounds();
		Coordinates.screenOffsetX = r.x;
		Coordinates.screenOffsetY = r.y;
	}

}
