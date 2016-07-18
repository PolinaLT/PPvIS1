package view;

import model.DrawingPanel;

import javax.imageio.ImageIO;
import javax.swing.*;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Menu extends JMenuBar {
	public Menu(JFrame frame, DrawingPanel panel) {
		
		Font font = new Font("Verdana", Font.PLAIN, 11);
		
		JMenuBar menuBar = new JMenuBar();
		
		JMenu menu = new JMenu("Menu");
		menu.setFont(font);
		
		JMenuItem newMenu = new JMenuItem("Save");
        newMenu.setFont(font);
        menu.add(newMenu);
        newMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser("..");
				fileChooser.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						File file = fileChooser.getSelectedFile();
						try {
							ImageIO.write(panel.getImage(), "png", file);
						} catch (IOException ex) {
							ex.printStackTrace();
						}
						fileChooser.setVisible(false);
					}
				});
				fileChooser.showSaveDialog(frame);
			}
		});
        
        JMenuItem openMenu = new JMenuItem("Open");
        openMenu.setFont(font);
        menu.add(openMenu);
        openMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser("..");
				fileChooser.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						File file = fileChooser.getSelectedFile();
						try {
							BufferedImage image = ImageIO.read(file);
							panel.setSize(image.getWidth(), image.getWidth());
							panel.setImage(image);
		                    panel.repaint();
							
						} catch (IOException ex) {
							ex.printStackTrace();
						}
						fileChooser.setVisible(false);
					}
				});
				fileChooser.showOpenDialog(frame);
				
			}
		});
        

        menuBar.add(menu);
        this.add(menuBar);
	}
}
