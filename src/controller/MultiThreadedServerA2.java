package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.Date;

import util.Config;
import view.ServerGUI;

/**
 * 
 * @author Pawel Paszki 
 * 
 * This class is the controller for the server. it creates
 * multiple connections with clients, during their initialization
 */
public class MultiThreadedServerA2 implements ActionListener {

	// connection used to communicate with mysql db
	private Connection connection;
	// GUI of the application
	private ServerGUI serverGUI;
	// statement used to create db queries
	private Statement statement;
	// data structure for results obtained from db
	private ResultSet resultSet;
	// client number used to set names of the created threads
	private int clientNumber = 1;

	public static void main(String[] args) {
		new MultiThreadedServerA2();
	}

	/**
	 * default constructor
	 */
	public MultiThreadedServerA2() {
		serverGUI = new ServerGUI(this);
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(8000);
			// create new thread for each client connected to the server
			while (true) {
				Socket socket = serverSocket.accept();
				MultiThreadedClient c = new MultiThreadedClient(socket);
				c.start();
			}
		} catch (Exception e) {

		}
	}

	/**
	 * attempt to connect to the mysql db on button click
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		connectToDB();
	}

	/**
	 * attempt to connect to db. User and password are gathered from text fields and
	 * to connection. Upon unsuccessful authentication appropriate info is
	 * displayed. When connection is successful, info about starting the server is
	 * displayed and authentication panel is replaced by mainServerPanel
	 */
	public void connectToDB() {
		try {
			String user = serverGUI.getAuthPanel().getUserTextField().getText();
			String password = serverGUI.getAuthPanel().getPasswordTextField().getText();
			Class.forName("com.mysql.jdbc.Driver");
			// try to connect to the database with specified user and password
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gradedatabase", user, password);
			// remove authentication panel upon successful DB connection
			serverGUI.removeAuthPanel();
			// display info about server being stated with the timestamp
			serverGUI.getMainServerPanel().getDisplayArea().append("Server started at " + new Date() + '\n');
		} catch (Exception e) {
			// when invalid credentials are provided or other error occurs, appropriate info
			// is displayed
			serverGUI.getAuthPanel().getPromptLabel().setText("Unable to connect. Try again");
			serverGUI.getAuthPanel().getPromptLabel().setForeground(Config.ERROR_COLOR);
		}
	}

	/**
	 * 
	 * @author Pawel Paszki This class is used for each individual client
	 */
	private class MultiThreadedClient extends Thread {
		// endpoint for communication between server and client
		private Socket socket;
		// instance of Inetaddress used to get info about client-server connection
		private InetAddress inetAddress;
		// stream used to get data from client
		private DataInputStream inputFromClient;
		// stream used to send data to client
		private DataOutputStream outputToClient;
		// determines, whether display "client connected" info
		private boolean clientConnectedDisplayed;

		/**
		 * constructor
		 * 
		 * @param socket
		 *            - socket used for communication to and from server
		 * @throws IOException
		 */
		public MultiThreadedClient(Socket socket) throws IOException {
			this.socket = socket;
			// initialize input and output streams
			inputFromClient = new DataInputStream(socket.getInputStream());
			outputToClient = new DataOutputStream(socket.getOutputStream());
			inetAddress = socket.getInetAddress();
			setName("Client-" + clientNumber++);
		}

		/**
		 * Thread's run method
		 */
		public void run() {
			try {
				// append thread's name to server's text area
				serverGUI.getMainServerPanel().getDisplayArea()
						.append("Thread Id: " + Thread.currentThread().getName() + "\n");
				while (true) {
					// read student number from client
					int studentNumber = inputFromClient.readInt();
					// read module name from client
					String moduleName = inputFromClient.readUTF();

					// select student with given id
					statement = connection.createStatement();
					resultSet = statement.executeQuery("select * from students where stud_id=" + studentNumber);
					String firstName = "";
					String surname = "";
					// assign name details of a student, if any data retrieved from db
					while (resultSet.next()) {
						firstName += resultSet.getString("fname");
						surname = resultSet.getString("sname");
					}
					// if no data retrieved - appropriate message is sent (no user in db) and socket
					// is closed
					if (firstName.length() == 0) {
						outputToClient.writeUTF("Sorry " + studentNumber + ". You are not a registered student. Bye.");
						socket.close();
					} else {
						// if id is valid, attempt to get specified module's data for student is made
						String query = "select * from modulegrades where stud_id=" + studentNumber + " && modulename="
								+ "\"" + moduleName + "\"";
						resultSet = statement.executeQuery(query);
						// assign and calculate marks
						int caMark = 0;
						double partialCaMark = 0;
						int examMark = 0;
						double partialExamMark = 0;
						double overallGrade = 0;
						String outputToSend = "";// change name later
						boolean hasData = false;
						while (resultSet.next()) {
							hasData = true;
							caMark = resultSet.getInt("ca_mark");
							examMark = resultSet.getInt("exam_mark");
							if (caMark > 0) {
								partialCaMark = caMark * 0.3;
							}
							if (examMark > 0) {
								partialExamMark = examMark * 0.7;
							}
							overallGrade = partialCaMark + partialExamMark;
						}
						// concatenate first and surnane of student to the output
						outputToSend += firstName + ";" + surname + ";";
						// concatenate noData String for module grades, if the requested module
						// is not in the database, otherwise concatenate calculated grades
						if (!hasData) {
							String noData = "no data for module: " + moduleName;
							outputToSend += noData + ";" + noData + ";" + noData;
						} else {
							// concatenate the gathered data to specific format into outputToSend string
							// format double numbers, to only display two digits after decimal point
							DecimalFormat df = new DecimalFormat("0.00");
							outputToSend += df.format(partialCaMark) + ";" + df.format(partialExamMark) + ";"
									+ df.format(overallGrade);
						}
						// display client connected info in server's text area
						if (!clientConnectedDisplayed) {
							serverGUI.getMainServerPanel().getDisplayArea()
									.append("Client connected. Hostname: " + inetAddress.getHostName()
											+ "; Ip address: " + inetAddress.getHostAddress() + "\n");
							clientConnectedDisplayed = true;
						}
						serverGUI.getMainServerPanel().getDisplayArea()
								.append("Processing " + getName() + " request... (host: " + inetAddress.getHostName()
										+ "; ip address: " + inetAddress.getHostAddress() + ")\n");
						// send data to client
						outputToClient.writeUTF(outputToSend);
					}
					outputToClient.flush();
				}
			} catch (Exception e) {
				// append exception info to server's text area
				if (serverGUI.getMainServerPanel() != null && e.getMessage() != null) {
					serverGUI.getMainServerPanel().getDisplayArea().append(e.getMessage() + ": " + getName());
				}
			}
		}
	}

	/**
	 * @return the serverGUI
	 */
	public ServerGUI getServerGUI() {
		return serverGUI;
	}

	/**
	 * @param serverGUI
	 *            the serverGUI to set
	 */
	public void setServerGUI(ServerGUI serverGUI) {
		this.serverGUI = serverGUI;
	}
}
