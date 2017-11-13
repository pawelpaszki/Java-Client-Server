package view;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import util.ComponentCreator;

/**
 * 
 * @author Pawel Paszki
 * 
 * This class represents UI for the server application
 */
public class MainServerPanel extends JPanel {

	private static final long serialVersionUID = 8046263928535614519L;
	// text area container
	private JScrollPane scrollPane;
	// text area displaying info about server-client communication
	private JTextArea displayArea;

	public MainServerPanel() {
		setBounds(0, 0, 500, 300);
		setLayout(null); // no layout manager
		displayArea = ComponentCreator.createJTextArea(0, 0, 500, 300);
		scrollPane = ComponentCreator.createJScrollPane(displayArea, 0, 0, 500, 300);
		add(scrollPane);
	}

	/**
	 * @return the scrollPane
	 */
	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	/**
	 * @param scrollPane
	 *            the scrollPane to set
	 */
	public void setScrollPane(JScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}

	/**
	 * @return the displayArea
	 */
	public JTextArea getDisplayArea() {
		return displayArea;
	}

	/**
	 * @param displayArea
	 *            the displayArea to set
	 */
	public void setDisplayArea(JTextArea displayArea) {
		this.displayArea = displayArea;
	}
}
