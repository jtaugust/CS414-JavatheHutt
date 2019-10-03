package Screens;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.border.MatteBorder;

import App.Application;
import GUI.Helpers;
import GUI.Label;
import GUI.Panel;

public class LoginScreen {
	private static boolean loginError = false;
	public static void screen(){

		Application.setCurrentScreen("Login");
		
		//get the working panel
		JPanel workingPanel = Panel.getWorkingPanel();
		//set the working panel's layout
		workingPanel.setLayout(new BorderLayout());
		
		
		//create section for username and password
		JPanel textFields = new JPanel();
		textFields.setLayout(new BoxLayout(textFields, BoxLayout.Y_AXIS));
		
		//create username field 
		JTextField username = Helpers.newTextField("Username", "Username");
		username.setBorder(BorderFactory.createCompoundBorder(new MatteBorder(0,6,0,0,new Color(79,175,255)),new MatteBorder(8,8,8,8, Color.white)));
		
		//add space before username
		textFields.add(Helpers.spacer(100, 150));
		
		//add username field
		textFields.add(username);
		
		//create space under username textfield
		textFields.add(Helpers.spacer(200, 50));
		
		//create password field
		JTextField password = Helpers.newTextField("Password", "Password");
		password.setBorder(BorderFactory.createCompoundBorder(new MatteBorder(0,6,0,0,new Color(79,175,255)),new MatteBorder(8,8,8,8, Color.white)));
		
		//add password field
		textFields.add(password);
		
		//make sure background is transparent so the template background will show
		textFields.setOpaque(false);
		
		//create space under password textfield (for password error)
		textFields.add(Helpers.spacer(200, 50));
		
	    //create the panel below password
	    JPanel error = new JPanel();
	    error.setOpaque(false);
	    error.setMaximumSize(new Dimension(400,50));
	    if (loginError == true){ //if login failed, update panel to an error label
	    	error.setOpaque(false);
	    	error.add(Label.errorLabel("<html>Incorrect username or password.<html>", Color.red));
	    }
	    textFields.add(error);	    
	    
	    
	    //create section for buttons (below the fields section)
	    JPanel bottomButtons = new JPanel();
	    bottomButtons.setLayout(new BoxLayout(bottomButtons, BoxLayout.LINE_AXIS));
	    bottomButtons.setBackground(Color.black);
	    bottomButtons.setBorder(BorderFactory.createCompoundBorder(new MatteBorder(6,0,0,0,new Color(79,175,255)),new MatteBorder(5,5,5,5, Color.black)));
	    bottomButtons.setPreferredSize(new Dimension(600,75));
	    
	    //create and add "Login" button
	    JPanel login = new JPanel();
	    login.setBackground(new Color(79,175,255));
	  	login.setLayout(new GridBagLayout());
	  	login.add(new JLabel("Login"));
	  	login.addMouseListener(new MouseAdapter() {
	  		@Override
	  		public void mousePressed(final MouseEvent e) {
	  			login.setBackground(new Color(110,190,255));
	  		}
	  		@Override
	  		public void mouseReleased(final MouseEvent e) {
	  			String name = username.getText(), pass = password.getText();
				if (!Database.DB.isUser(name, pass)){ // user doesn't exist
					LoginScreen.setLoginError();
					Application.changeScreen("Login");
				}else{
					//update application variable "User" to username.getText()
					Application.setUser(name);
					//redirect to main page
					Application.changeScreen("InitialMain");
				}
	  		}
		});
	    login.setBorder(BorderFactory.createCompoundBorder(new MatteBorder(10,10,10,10,Color.black), new MatteBorder(2,2,2,2,new Color(79,175,255))));
	    bottomButtons.add(login);
		
		
		//create and add "Register" button
	    JPanel register = new JPanel();
	    register.setBackground(new Color(90,90,90));
	    register.setLayout(new GridBagLayout());
	    register.add(new JLabel("Sign up here!"));
	    register.addMouseListener(new MouseAdapter() {
	  		@Override
	  		public void mousePressed(final MouseEvent e) {
	  			register.setBackground(new Color(255,255,255));
	  		}
	  		@Override
	  		public void mouseReleased(final MouseEvent e) {
	  			Application.changeScreen("Registration");
	  		}
		});
	    register.setBorder(BorderFactory.createCompoundBorder(new MatteBorder(10,10,10,10,Color.black), new MatteBorder(2,2,2,2,new Color(79,175,255))));
	    bottomButtons.add(register);
	    
	    //add field and button panels to the working panel
	    workingPanel.add(textFields, BorderLayout.PAGE_START);
	    workingPanel.add(bottomButtons, BorderLayout.PAGE_END);
		
		//if loginError was set, change it back
		if (loginError == true){
			loginError = false;
		}
	}

	public static void setLoginError(){
		loginError = true;
	}
	
	
}
