package view;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import util.ComponentCreator;
import util.Config;

/**
 * 
 * @author Pawel Paszki 
 * 
 * This class is used to create user interface, which is
 * used to gather DB login info and submit it to the phpmyadmin using
 * mysql connector to get connection to the database
 */
public class AuthPanel extends JPanel {

	private static final long serialVersionUID = -1041195868627048017L;
	// text field used to gather db username
	private JTextField userTextField; 
	// text field used to gather db password
	private JTextField passwordTextField;
	// authentication button
	private JButton authButton; 
	// this label is used a hint for user. it turns red when auth credentials are incorrect
	private JLabel promptLabel; 

	/**
	 * default constructor.
	 */
	public AuthPanel() {
		setLayout(null);
		setBackground(Config.DARK_COLOR);
		setForeground(Config.LIGHT_COLOR);
		// create labels, textfields  and buttons 
		promptLabel = ComponentCreator.createJLabel(this, "Please enter DB credentials", false, 105, 55, 280, 40);
		
		ComponentCreator.createJLabel(this, "user", false, 105, 100, 100, 40);
		
		ComponentCreator.createJLabel(this, "password", false, 105, 145, 100, 40);
		
		userTextField = ComponentCreator.createTextField(this, true, 205, 110, 180, 20);
		
		passwordTextField = ComponentCreator.createTextField(this, true, 205, 155, 180, 20);
		
		authButton = ComponentCreator.createButton(this, "authenticate", 105, 195, 280, 30);
	}

	/**
	 * @return the userTextField
	 */
	public JTextField getUserTextField() {
		return userTextField;
	}

	/**
	 * @param userTextField
	 *            the userTextField to set
	 */
	public void setUserTextField(JTextField userTextField) {
		this.userTextField = userTextField;
	}

	/**
	 * @return the passwordTextField
	 */
	public JTextField getPasswordTextField() {
		return passwordTextField;
	}

	/**
	 * @param passwordTextField
	 *            the passwordTextField to set
	 */
	public void setPasswordTextField(JTextField passwordTextField) {
		this.passwordTextField = passwordTextField;
	}

	/**
	 * @return the authButton
	 */
	public JButton getAuthButton() {
		return authButton;
	}

	/**
	 * @param authButton
	 *            the authButton to set
	 */
	public void setAuthButton(JButton authButton) {
		this.authButton = authButton;
	}

	/**
	 * @return the promptLabel
	 */
	public JLabel getPromptLabel() {
		return promptLabel;
	}

	/**
	 * @param promptLabel
	 *            the promptLabel to set
	 */
	public void setPromptLabel(JLabel promptLabel) {
		this.promptLabel = promptLabel;
	}
}
