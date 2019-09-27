package Templates;

import java.awt.*;

import javax.swing.*;

import GUI.Frame;
import GUI.Panel;
import Screens.LoginScreen;

public class InitialTemplate {
	public static void generateInitial(JFrame frame) {
		//set the frame dimensions
		Frame.setDimensions(1000, 1000);
		
		//create background panel
		JPanel backgroundPanel = Panel.setBackground("./Images/Background.jpg");
		backgroundPanel.setLayout(new BorderLayout());
		
		//add the logo to top of background panel
		backgroundPanel.add(GUI.Label.getImageLabel("./Images/Logo.png"), BorderLayout.PAGE_START);
		
		Panel.setWorkingPanelBackground(backgroundPanel);
		
		//create panel for working area
		JPanel workingPanel = new JPanel();
		
		//make working panel background invisible so it wont cover the Frame background
		workingPanel.setOpaque(false);
		
		
		//add panel to background panel
		backgroundPanel.add(workingPanel, BorderLayout.CENTER);
		
		//set the panel that a screen should load into
		Panel.setWorkingPanel(workingPanel, false);
		
		//set the entire background into the pane
		frame.setContentPane(backgroundPanel);
		
		//set the template variable
		Frame.setTemplate(1);
		
		//finalize the frame
		Frame.finalize(frame);
	}
}