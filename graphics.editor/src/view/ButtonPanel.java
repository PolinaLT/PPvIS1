package view;

import controller.ActionTypeHandler;

import javax.swing.*;
import java.awt.*;

public class ButtonPanel extends Box {
	public ButtonPanel(ActionTypeHandler handler) {
		super(BoxLayout.Y_AXIS);
		
		String[] buttonNames = {"pencil.jpeg", "eraser.png", "quadrangle.png", 
				"circle.jpg", "t.png", "someArea.png", "area.gif", "loupe2.png", "loupe4.png",
				"insert.jpg", "delete.jpg"};
		
		for (String x : buttonNames) {
			JButton button = new JButton(new ImageIcon(x));
			button.addActionListener(handler);
			button.setName(x);
			button.setBorder(BorderFactory.createEmptyBorder());
			add(button, BorderLayout.WEST);
		}
		
		Color[] buttonColor = {Color.BLACK, Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN};
		
		for (Color x : buttonColor) {
			JButton button = new JButton();
			button.addActionListener(handler);
	        button.setBackground(x);
	        add(button);
		}
	}
}
