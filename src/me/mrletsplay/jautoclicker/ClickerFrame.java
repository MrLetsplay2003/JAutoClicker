package me.mrletsplay.jautoclicker;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ClickerFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 175078711027490120L;
	
	private JTextField
		clickSpeedField;
	
	private JButton
		startButton,
		scriptingHelpButton;
	
	private JSlider
		percentClickSlider;
	
	private JTextArea
		scriptArea;
	
	private JRadioButton
		atMousePosButton,
		customPosButton;
	
	private JTextField
		posXField,
		posYField;
	
	public ClickerFrame() {
		super("JAutoClicker");
		pack();
		Insets insets = getInsets();
		setLayout(null);
		setBounds(0, 0, 720, 480);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JLabel clickSpeedLbl = new JLabel("Click Speed (cps)");
		clickSpeedLbl.setBounds(10, 10, 120, 25);
		add(clickSpeedLbl);
		
		clickSpeedField = new JTextField("100");
		clickSpeedField.setBounds(140, 10, 100, 25);
		add(clickSpeedField);
		
		startButton = new JButton("Start Clicking (F9)!");
		startButton.setBounds(getWidth() - 210, 10, 200, 25);
		startButton.addActionListener(this);
		add(startButton);
		
		JLabel percClickLbl = new JLabel("% of click");
		percClickLbl.setBounds(10, 55, 120, 25);
		add(percClickLbl);
		
		JLabel percClickLbl2 = new JLabel("pressed down");
		percClickLbl2.setBounds(10, 70, 120, 25);
		add(percClickLbl2);
		
		percentClickSlider = new JSlider(0, 100, 50);
		percentClickSlider.setBounds(140, 50, 100, 50);
		add(percentClickSlider);
		
		
		scriptingHelpButton = new JButton("?");
		scriptingHelpButton.setBounds(10, 110, 30, 30);
		scriptingHelpButton.addActionListener(this);
		add(scriptingHelpButton);
		
		atMousePosButton = new JRadioButton("Click At Mouse Position");
		atMousePosButton.setBounds(250, 10, 200, 25);
		atMousePosButton.setSelected(true);
		add(atMousePosButton);
		
		customPosButton = new JRadioButton("Click At Custom Position");
		customPosButton.setBounds(250, 35, 200, 25);
		add(customPosButton);
		
		ButtonGroup grp = new ButtonGroup();
		grp.add(atMousePosButton);
		grp.add(customPosButton);
		
		JLabel posXLbl = new JLabel("X");
		posXLbl.setBounds(250, 70, 15, 25);
		add(posXLbl);
		
		posXField = new JTextField();
		posXField.setBounds(270, 70, 200, 25);
		add(posXField);
		
		JLabel posYLbl = new JLabel("Y");
		posYLbl.setBounds(250, 100, 15, 25);
		add(posYLbl);
		
		posYField = new JTextField();
		posYField.setBounds(270, 100, 200, 25);
		add(posYField);
		
		scriptArea = new JTextArea();
		
		JScrollPane sp = new JScrollPane(scriptArea);
		sp.setBounds(10, 150, 700, getHeight() - 160 - insets.top);
		add(sp);
		
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == startButton) {
			
		}else if(e.getSource() == scriptingHelpButton) {
			JOptionPane.showMessageDialog(this, "Hi!", "Scripting Info", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public int getCps() {
		try {
			return Integer.parseInt(clickSpeedField.getText());
		}catch(NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Invalid click speed", "Error", JOptionPane.ERROR_MESSAGE);
			return -1;
		}
	}
	
	public double getClickPercent() {
		return percentClickSlider.getValue() / 100D;
	}
	
	public boolean isCustomClickPosition() {
		return customPosButton.isSelected();
	}
	
	public int getPosX() {
		try {
			return Integer.parseInt(posXField.getText());
		}catch(NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Invalid X position", "Error", JOptionPane.ERROR_MESSAGE);
			return -1;
		}
	}
	
	public int getPosY() {
		try {
			return Integer.parseInt(posYField.getText());
		}catch(NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Invalid Y position", "Error", JOptionPane.ERROR_MESSAGE);
			return -1;
		}
	}
	
}
