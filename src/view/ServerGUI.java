package view;

import javax.swing.JFrame;

import controller.MultiThreadedServerA2;

/**
 * 
 * @author Pawel Paszki
 * 
 * This class uses two Panels, authPanel for db authentication and mainServerPanel to 
 * display info about client(s)-server communication
 */
public class ServerGUI{

	// main frame of the UI
	private JFrame serverFrame;
	// panel used to gather db auth data
	private AuthPanel authPanel;
	// panel shown after db authentication shows communication details
	// between client(s) and server
	private MainServerPanel mainServerPanel;

	/**
	 * constructor:
	 * @param server used to pass to authButton to pass 
	 * actions, so that they will be handled by the MultiThreadedServerA2
	 */
	public ServerGUI(MultiThreadedServerA2 server) {
		init(server);
	}

	/**
	 * construct UI
	 * @param server used to pass to authButton to pass 
	 * actions, so that they will be handled by the MultiThreadedServerA2
	 */
	private void init(MultiThreadedServerA2 server) {
		serverFrame = new JFrame("Welcome");
		serverFrame.setSize(500, 300); 
		serverFrame.setResizable(false); // resizing the window is disabled
		serverFrame.setLocationRelativeTo(null); // center relative to desktop
		serverFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // close app, when "x" button is pressed
		serverFrame.setLayout(null); // no layout manager used - all bounds are set manually
		
		authPanel = new AuthPanel();
		authPanel.setBounds(0,0,500,300);
		authPanel.getAuthButton().addActionListener(server);
		
		serverFrame.getContentPane().add(authPanel);
		serverFrame.setVisible(true);

	}

	/**
	 * @return the authPanel
	 */
	public AuthPanel getAuthPanel() {
		return authPanel;
	}

	/**
	 * @param authPanel the authPanel to set
	 */
	public void setAuthPanel(AuthPanel authPanel) {
		this.authPanel = authPanel;
	}

	/**
	 * @return the mainServerPanel
	 */
	public MainServerPanel getMainServerPanel() {
		return mainServerPanel;
	}

	/**
	 * @param mainServerPanel the mainServerPanel to set
	 */
	public void setMainServerPanel(MainServerPanel mainServerPanel) {
		this.mainServerPanel = mainServerPanel;
	}

	/**
	 * @return the serverFrame
	 */
	public JFrame getServerFrame() {
		return serverFrame;
	}

	/**
	 * @param serverFrame the serverFrame to set
	 */
	public void setServerFrame(JFrame serverFrame) {
		this.serverFrame = serverFrame;
	}
	
	/**
	 * This method is used to replace authPanel with mainServerPanel
	 * upon successful db authentication
	 */
	public void removeAuthPanel() {
		serverFrame.remove(authPanel);
		serverFrame.setVisible(false);
		serverFrame.setVisible(true);
		mainServerPanel = new MainServerPanel();
		serverFrame.getContentPane().add(mainServerPanel);
		serverFrame.setTitle("Multithreaded Server");
	}
}
