package GUI;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

public class Panel {
	
	private static JPanel workingPanel = new JPanel(), workingPanelBackground = new JPanel();
	
	//returns a background panel of the provided picture with the provided size
	public static JPanel setBackground(String path, int width, int height) {
		//paint the panel as the image provided
		JPanel background = new JPanel() {
			BufferedImage image = Helpers.getImage(path);
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(image, 0, 0, width, height, this);
			}
		};
		
		return background;
	}
	
	public static JPanel errorCards(Dimension size){
		JPanel temp = new JPanel(new CardLayout());
		temp.setMaximumSize(size);
		temp.setOpaque(false);
		return temp;
	}
}