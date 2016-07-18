package view;

import controller.ActionTypeHandler;
import model.DrawingPanel;

import javax.swing.*;
import java.awt.*;


public class MainWindow {
	public MainWindow() {
		JFrame frame = new JFrame("Graphics Editor");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setSize(1400, 730);
		frame.setLayout(new BorderLayout());
		
		DrawingPanel drawingPanel = new DrawingPanel(1000, 1000);
		frame.add(drawingPanel);	
		
		ActionTypeHandler handler = new ActionTypeHandler(drawingPanel);
		
		Menu menu = new Menu(frame, drawingPanel);
		frame.add(menu, BorderLayout.NORTH);
		
		ButtonPanel buttonPanel = new ButtonPanel(handler);
		frame.add(buttonPanel, BorderLayout.WEST);
		
		
		frame.setVisible(true); 
		
		drawingPanel.test();
	}
	
	public static void main(String[] args) {
		MainWindow window = new MainWindow();
	}
}
