package util;

import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import view.ClientGUI;

/**
 * 
 * @author Pawel Paszki
 * 
 *         This class is used for component creation in order not to repeat the
 *         same code for similar components, like JLabels or JTextFields
 *
 */
public class ComponentCreator {

	/**
	 * 
	 * @param name
	 *            - name of the panel
	 * @param x
	 *            - x coordinate inside frame
	 * @param y
	 *            - y coordinate inside frame
	 * @param width
	 *            - width of the panel
	 * @param height
	 *            - height of the panel
	 * @return created JPanel
	 */
	public static JPanel createJPanel(String name, int x, int y, int width, int height) {
		JPanel panel = new JPanel();
		panel.setName(name);
		panel.setBounds(x, y, width, height);
		panel.setLayout(null);
		panel.setBackground(Config.DARK_COLOR);
		return panel;
	}

	/**
	 * 
	 * @param panel
	 *            - JPanel, to which the component will be added
	 * @param text
	 *            - label's text
	 * @param isErrorLabel
	 *            - specifies, whether created label needs to have red font
	 * @param x
	 *            - x coordinate inside the panel
	 * @param y
	 *            - y coordinate inside the panel
	 * @param width
	 *            - width of the label
	 * @param height
	 *            - height of the label
	 * @return created JLabel with specified parameters
	 */
	public static JLabel createJLabel(JPanel panel, String text, boolean isErrorLabel, int x, int y, int width,
			int height) {
		JLabel label = new JLabel(text);
		label.setBounds(x, y, width, height);
		// set red font color for error labels or light color otherwise
		if (isErrorLabel) {
			label.setForeground(Config.ERROR_COLOR);
			if (panel.getName().equals("main")) {
				label.setFont(new Font("Arial", Font.BOLD, 14));
				label.setHorizontalAlignment(SwingConstants.RIGHT);
			} else {
				label.setFont(new Font("Arial", Font.BOLD, 16));
				label.setHorizontalAlignment(SwingConstants.CENTER);
			}
			label.setVisible(false);
		} else {
			label.setForeground(Config.LIGHT_COLOR);
		}
		panel.add(label);
		return label;
	}

	/**
	 * 
	 * @param clientGUI - instance of UI class used to get the relative path of the project
	 * @param panel - JPanel, to which the component will be added
	 * @param toolTipText - text to be displayed, when mouse is hovered over the label
	 * @param x
	 *            - x coordinate inside the panel
	 * @param y
	 *            - y coordinate inside the panel
	 * @param width
	 *            - width of the label
	 * @param height
	 *            - height of the label
	 * @return created JLabel with icon
	 */
	public static JLabel createInfoIconLabel(ClientGUI clientGUI, JPanel panel, String toolTipText, int x, int y, int width, int height) {
		try {
			JLabel label = new JLabel();
			label.setBounds(x, y, width, height);
			label.setFont(new Font("Arial", Font.BOLD, 0));
			Image icon = ImageIO.read(clientGUI.getClass().getResource(
					"/res/icon.png"));
			label.setIcon(new ImageIcon(icon));
			label.setToolTipText(toolTipText);
			panel.add(label);
			return label;
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
		return null;
	}

	/**
	 * 
	 * @param panel
	 *            - JPanel, to which the component will be added
	 * @param editable
	 *            - disable user input, if set to false
	 * @param x
	 *            - x coordinate inside the panel
	 * @param y
	 *            - y coordinate inside the panel
	 * @param width
	 *            - width of the JTextField
	 * @param height
	 *            - height of the JTextField
	 * @return created JLabel with specified parameters
	 */
	public static JTextField createTextField(JPanel panel, boolean editable, int x, int y, int width, int height) {
		JTextField textField = new JTextField();
		textField.setBounds(x, y, width, height);
		textField.setEditable(editable);
		panel.add(textField);
		return textField;
	}

	/**
	 * 
	 * @param panel
	 *            - JPanel, to which the component will be added
	 * @param text
	 *            - text displayed inside the button component
	 * @param x
	 *            - x coordinate inside the panel
	 * @param y
	 *            - y coordinate inside the panel
	 * @param width
	 *            - width of the button
	 * @param height
	 *            - height of the button
	 * @return created button with specified parameters
	 */
	public static JButton createButton(JPanel panel, String text, int x, int y, int width, int height) {
		JButton button = new JButton(text);
		button.setFocusPainted(false);
		button.setMargin(new Insets(0, 0, 0, 0));
		button.setBounds(x, y, width, height);
		button.setBackground(Config.DARK_COLOR);
		button.setForeground(Config.LIGHT_COLOR);
		panel.add(button);
		return button;
	}

	/**
	 * 
	 * @param jtArea - test area to be used inside JScrollPane
	  * @param x
	 *            - x coordinate inside the panel
	 * @param y
	 *            - y coordinate inside the panel
	 * @param width
	 *            - width of the button
	 * @param height
	 *            - height of the button
	 * @return created JScrollPane
	 */
	public static JScrollPane createJScrollPane(JTextArea jtArea, int x, int y, int width, int height) {
		JScrollPane scrollPane = new JScrollPane(jtArea);
		scrollPane.setBounds(x, y, width, height);
		scrollPane.setBorder(new LineBorder(Config.LIGHT_COLOR, 5));
		scrollPane.setViewportBorder(new LineBorder(Config.DARK_COLOR, 5));
		return scrollPane;
	}

	/**
	 * 
	 * @param x
	 *            - x coordinate inside the panel
	 * @param y
	 *            - y coordinate inside the panel
	 * @param width
	 *            - width of the text area
	 * @param height
	 *            - height of the text area
	 * @return JTextArea with specified parameters
	 */
	public static JTextArea createJTextArea(int x, int y, int width, int height) {
		JTextArea jtArea = new JTextArea();
		jtArea.setBounds(x, y, width, height);
		jtArea.setEditable(false);
		jtArea.setBackground(Config.DARK_COLOR);
		jtArea.setForeground(Config.LIGHT_COLOR);
		return jtArea;
	}
}
