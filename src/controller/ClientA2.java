package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import util.Config;
import view.ClientGUI;

/**
 * 
 * @author Pawel Paszki 
 * 
 * This class is the controller for each individual client
 * instance
 */
public class ClientA2 implements ActionListener {
	// clientGUI instance
	private ClientGUI clientGUI;
	// stream used to send data to server
	private DataOutputStream toServer;
	// stream used to receive data from server
	private DataInputStream fromServer;
	// endpoint for communication between server and client
	private Socket socket;

	public static void main(String[] args) {
		new ClientA2();
	}

	/**
	 * Default constructor
	 */
	public ClientA2() {
		clientGUI = new ClientGUI(this);
		try {
			// initialize socket and input/ output streams
			socket = new Socket("localhost", 8000);
			fromServer = new DataInputStream(socket.getInputStream());
			toServer = new DataOutputStream(socket.getOutputStream());
		} catch (Exception e) {
			// if connection refused (no server is running), appropriate info is displayed
			if (e.toString().contains("Connection refused")) {
				clientGUI.getMainClientPanel().setVisible(false);
				clientGUI.getErrorMessagePanel().setVisible(true);
				clientGUI.getErrorMessageLabel().setVisible(true);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		// only send data, if valid (ie numbers for student id and no empty module names
		if (clientGUI.isValidIDInput() && clientGUI.isValidModuleNameLength()) {
			try {
				int studentNumber = Integer.parseInt(clientGUI.getStudentIDTextField().getText());
				// send studentNumber to server
				toServer.writeInt(studentNumber);
				String moduleName = clientGUI.getModuleNameTextField().getText();
				// send module name to server
				toServer.writeUTF(moduleName);

				// get properly formatted response from server
				String serverResponse = fromServer.readUTF();
				// check, if student is in the database
				if (serverResponse.startsWith("Sorry")) {
					// if student not present in db, socket is closed
					// and appropriate info is displayed
					clientGUI.getMainClientPanel().setVisible(false);
					clientGUI.getErrorMessagePanel().setVisible(true);
					clientGUI.getErrorMessageLabel().setVisible(true);
					clientGUI.getErrorMessageLabel().setText(serverResponse);
				} else {
					// if student's id sent to the server was valid, the response
					// will be ";"-separated, so first it needs to be split into
					// individual components
					String[] serverRespComps = serverResponse.split(";");
					if (serverRespComps.length == clientGUI.getCompsToPopulate().size()) {
						for (int i = 0; i < clientGUI.getCompsToPopulate().size(); i++) {
							clientGUI.getCompsToPopulate().get(i).setText(serverRespComps[i]);
							if (i >= 2) {
								// if grades data starts with "no" it means that module does not exist
								// to emphasize no grades' details, text in the textfield is
								// displayed in red. Otherwise it is displayed in black
								if (serverRespComps[i].startsWith("no")) {
									clientGUI.getCompsToPopulate().get(i).setForeground(Config.ERROR_COLOR);
								} else {
									clientGUI.getCompsToPopulate().get(i).setForeground(Color.black);
								}
							}
						}
					}
				}
			} catch (Exception e) {
				if(e.getMessage().startsWith("Broken pipe")) {
					// this occurs, when previously running server is closed - appopriate message is displayed in client
					clientGUI.getMainClientPanel().setVisible(false);
					clientGUI.getErrorMessagePanel().setVisible(true);
					clientGUI.getErrorMessageLabel().setVisible(true);
					clientGUI.getErrorMessageLabel().setText("Server is shut down. Please try again later");
				}
			}
		} else {
			// if id is invalid, the corresponding textfield will be highlighted in red
			// and appropriate label will be displayed
			if (!clientGUI.isValidIDInput()) {
				clientGUI.getStudentIDTextField().setBackground(Config.ERROR_COLOR);
				clientGUI.getInvalidIDInfoLabel().setVisible(true);
			}
			// if module name is an empty string, the corresponding textfield will be
			// highlighted in red
			// and appropriate label will be displayed
			if (!clientGUI.isValidModuleNameLength()) {
				clientGUI.getInvalidModuleLengthLabel().setVisible(true);
				clientGUI.getModuleNameTextField().setBackground(Config.ERROR_COLOR);
			}
		}

	}

	/**
	 * @return the clientGUI
	 */
	public ClientGUI getClientGUI() {
		return clientGUI;
	}

	/**
	 * @param clientGUI
	 *            the clientGUI to set
	 */
	public void setClientGUI(ClientGUI clientGUI) {
		this.clientGUI = clientGUI;
	}
}
