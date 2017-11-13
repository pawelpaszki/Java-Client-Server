package view;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import controller.ClientA2;
import util.ComponentCreator;
import util.Config;

/**
 * 
 * @author Pawel Paszki 
 * 
 * This class is used to provide user interface for client
 * application
 */
public class ClientGUI {

	// main frame of the UI window
	private JFrame mainClientFrame;
	// main panel to hold all components
	private JPanel mainClientPanel;
	// panel displayed when socket is closed or invalid student attempts
	// to get his/her results
	private JPanel errorMessagePanel;
	// label shown when data other than digits is entered into studentIDTextField
	private JLabel invalidIDInfoLabel;
	// text field used to gather student id data
	private JTextField studentIDTextField;
	// label shown when no input is provided for module name
	private JLabel invalidModuleLengthLabel;
	// text field used to gather student id data
	private JTextField moduleNameTextField;
	// text field used to display student's first name
	private JTextField firstNameTextField;
	// text field used to display student's surname
	private JTextField surnameTextField;
	// text field used to display ca mark for module
	private JTextField caMarkTextField;
	// text field used to display exam mark for module
	private JTextField examMarkTextField;
	// text field used to display overall grade for module
	private JTextField overallGradeTextField;
	// ArrayList used to ease population of textfields
	private ArrayList<JTextField> compsToPopulate = new ArrayList<>();
	private JButton submitDataButton;
	// flag set to true, if number pvodied as an ID
	private boolean validIDInput;
	// flag set to true, if module name length is greater than 0
	private boolean validModuleNameLength;
	// message displayed, when there is an error in connection to server
	// or if invalid student id is provided and thefefore socket is closed
	private JLabel errorMessageLabel;

	/**
	 * constructor used to create GUI
	 * 
	 * @param client
	 *            - controller class instance used to get all button press actions
	 */
	public ClientGUI(ClientA2 client) {
		init(client);
	}

	/**
	 * UI construction method
	 * 
	 * @param client
	 *            - controller class instance used to get all button press actions
	 */
	private void init(ClientA2 client) {
		mainClientFrame = new JFrame("Client");
		mainClientFrame.setSize(500, 400);
		mainClientFrame.setResizable(false); // resizing the window is disabled
		mainClientFrame.setLocationRelativeTo(null); // center relative to desktop
		mainClientFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // close app, when "x" button is pressed
		mainClientFrame.setLayout(null); // no layout manager used - all bounds are set manually

		mainClientPanel = ComponentCreator.createJPanel("main", 0, 0, 500, 400);

		errorMessagePanel = ComponentCreator.createJPanel("error", 0, 0, 500, 400);

		errorMessageLabel = ComponentCreator.createJLabel(errorMessagePanel, Config.CONNECTION_REFUSED, true, 10, 190,
				480, 20);

		errorMessagePanel.setVisible(false);

		ComponentCreator.createJLabel(mainClientPanel, "Student id: ", false, 60, 5, 180, 20);

		studentIDTextField = ComponentCreator.createTextField(mainClientPanel, true, 60, 30, 380, 20);
		// check, if id is a number and not empty
		studentIDTextField.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				checkValue();
			}

			public void removeUpdate(DocumentEvent e) {
				checkValue();
			}

			public void insertUpdate(DocumentEvent e) {
				checkValue();
			}

			public void checkValue() {
				try {
					Integer.parseInt(studentIDTextField.getText());
					validIDInput = true;
					studentIDTextField.setBackground(Color.white);
				} catch (NumberFormatException e) {
					validIDInput = false;
				}
				invalidIDInfoLabel.setVisible(!validIDInput);
			}
		});

		ComponentCreator.createJLabel(mainClientPanel, "Module name: ", false, 60, 55, 180, 20);

		moduleNameTextField = ComponentCreator.createTextField(mainClientPanel, true, 60, 80, 380, 20);
		// check if module name is not empty
		moduleNameTextField.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				checkValue();
			}

			public void removeUpdate(DocumentEvent e) {
				checkValue();
			}

			public void insertUpdate(DocumentEvent e) {
				checkValue();
			}

			public void checkValue() {
				int moduleNameLength = moduleNameTextField.getText().length();
				if (moduleNameLength == 0) {
					validModuleNameLength = false;
				} else {
					validModuleNameLength = true;
					moduleNameTextField.setBackground(Color.white);
				}
				invalidModuleLengthLabel.setVisible(!validModuleNameLength);
			}
		});
		
		invalidIDInfoLabel = ComponentCreator.createJLabel(mainClientPanel, Config.INVALID_INPUT, true, 260, 5, 180,
				20);

		invalidModuleLengthLabel = ComponentCreator.createJLabel(mainClientPanel, Config.NO_INPUT, true, 260, 55, 180,
				20);

		// first nane label displayed over the text field
		ComponentCreator.createJLabel(mainClientPanel, "First Name: ", false, 60, 105, 180, 20);

		firstNameTextField = ComponentCreator.createTextField(mainClientPanel, false, 60, 130, 380, 20);
		compsToPopulate.add(firstNameTextField); // add to ArrayList for simpler population inside a loop

		// surname label displayed over the text field
		ComponentCreator.createJLabel(mainClientPanel, "Surname: ", false, 60, 155, 180, 20);

		surnameTextField = ComponentCreator.createTextField(mainClientPanel, false, 60, 180, 380, 20);
		compsToPopulate.add(surnameTextField); // add to ArrayList for simpler population inside a loop

		// CA Mark label displayed over the text field
		ComponentCreator.createJLabel(mainClientPanel, "CA Mark: ", false, 60, 205, 180, 20);
		// info icon - when hovered over - tooltip text is displayed
		ComponentCreator.createInfoIconLabel(this, mainClientPanel, Config.CA_MARK_TOOLTIP_TEXT, 420, 205, 20, 20);

		caMarkTextField = ComponentCreator.createTextField(mainClientPanel, false, 60, 230, 380, 20);
		compsToPopulate.add(caMarkTextField); // add to ArrayList for simpler population inside a loop

		ComponentCreator.createJLabel(mainClientPanel, "Exam Mark: ", false, 60, 255, 180, 20);
		// info icon - when hovered over - tooltip text is displayed
		ComponentCreator.createInfoIconLabel(this, mainClientPanel, Config.EXAM_MARK_TOOLTIP_TEXT, 420, 255, 20, 20);

		examMarkTextField = ComponentCreator.createTextField(mainClientPanel, false, 60, 280, 380, 20);
		compsToPopulate.add(examMarkTextField); // add to ArrayList for simpler population inside a loop

		ComponentCreator.createJLabel(mainClientPanel, "Overall Grade ", false, 60, 305, 180, 20);
		// info icon - when hovered over - tooltip text is displayed
		ComponentCreator.createInfoIconLabel(this, mainClientPanel, Config.OVERALL_MARK_TOOLTIP_TEXT, 420, 305, 20, 20);

		overallGradeTextField = ComponentCreator.createTextField(mainClientPanel, false, 60, 330, 380, 20);
		compsToPopulate.add(overallGradeTextField); // add to ArrayList for simpler population inside a loop

		submitDataButton = ComponentCreator.createButton(mainClientPanel, "submit", 90, 360, 320, 30);
		submitDataButton.addActionListener(client);

		mainClientFrame.getContentPane().add(mainClientPanel);
		mainClientFrame.getContentPane().add(errorMessagePanel);
		mainClientFrame.setVisible(true);
	}

	/**
	 * @return the studentIDTextField
	 */
	public JTextField getStudentIDTextField() {
		return studentIDTextField;
	}

	/**
	 * @param studentIDTextField
	 *            the studentIDTextField to set
	 */
	public void setStudentIDTextField(JTextField studentIDTextField) {
		this.studentIDTextField = studentIDTextField;
	}

	/**
	 * @return the moduleNameTextField
	 */
	public JTextField getModuleNameTextField() {
		return moduleNameTextField;
	}

	/**
	 * @param moduleNameTextField
	 *            the moduleNameTextField to set
	 */
	public void setModuleNameTextField(JTextField moduleNameTextField) {
		this.moduleNameTextField = moduleNameTextField;
	}

	/**
	 * @return the submitDataButton
	 */
	public JButton getSubmitDataButton() {
		return submitDataButton;
	}

	/**
	 * @param submitDataButton
	 *            the submitDataButton to set
	 */
	public void setSubmitDataButton(JButton submitDataButton) {
		this.submitDataButton = submitDataButton;
	}

	/**
	 * @return the validIDInput
	 */
	public boolean isValidIDInput() {
		return validIDInput;
	}

	/**
	 * @param validIDInput
	 *            the validIDInput to set
	 */
	public void setValidIDInput(boolean validIDInput) {
		this.validIDInput = validIDInput;
	}

	/**
	 * @return the validModuleNameLength
	 */
	public boolean isValidModuleNameLength() {
		return validModuleNameLength;
	}

	/**
	 * @param validModuleNameLength
	 *            the validModuleNameLength to set
	 */
	public void setValidModuleNameLength(boolean validModuleNameLength) {
		this.validModuleNameLength = validModuleNameLength;
	}

	/**
	 * @return the invalidIDInfoLabel
	 */
	public JLabel getInvalidIDInfoLabel() {
		return invalidIDInfoLabel;
	}

	/**
	 * @param invalidIDInfoLabel
	 *            the invalidIDInfoLabel to set
	 */
	public void setInvalidIDInfoLabel(JLabel invalidIDInfoLabel) {
		this.invalidIDInfoLabel = invalidIDInfoLabel;
	}

	/**
	 * @return the invalidModuleLengthLabel
	 */
	public JLabel getInvalidModuleLengthLabel() {
		return invalidModuleLengthLabel;
	}

	/**
	 * @param invalidModuleLengthLabel
	 *            the invalidModuleLengthLabel to set
	 */
	public void setInvalidModuleLengthLabel(JLabel invalidModuleLengthLabel) {
		this.invalidModuleLengthLabel = invalidModuleLengthLabel;
	}

	/**
	 * @return the mainClientPanel
	 */
	public JPanel getMainClientPanel() {
		return mainClientPanel;
	}

	/**
	 * @param mainClientPanel
	 *            the mainClientPanel to set
	 */
	public void setMainClientPanel(JPanel mainClientPanel) {
		this.mainClientPanel = mainClientPanel;
	}

	/**
	 * @return the errorMessagePanel
	 */
	public JPanel getErrorMessagePanel() {
		return errorMessagePanel;
	}

	/**
	 * @param errorMessagePanel
	 *            the errorMessagePanel to set
	 */
	public void setErrorMessagePanel(JPanel errorMessagePanel) {
		this.errorMessagePanel = errorMessagePanel;
	}

	/**
	 * @return the errorMessageLabel
	 */
	public JLabel getErrorMessageLabel() {
		return errorMessageLabel;
	}

	/**
	 * @param errorMessageLabel
	 *            the errorMessageLabel to set
	 */
	public void setErrorMessageLabel(JLabel errorMessageLabel) {
		this.errorMessageLabel = errorMessageLabel;
	}

	/**
	 * @return the compsToPopulate
	 */
	public ArrayList<JTextField> getCompsToPopulate() {
		return compsToPopulate;
	}

	/**
	 * @param compsToPopulate
	 *            the compsToPopulate to set
	 */
	public void setCompsToPopulate(ArrayList<JTextField> compsToPopulate) {
		this.compsToPopulate = compsToPopulate;
	}

	/**
	 * @return the firstNameTextField
	 */
	public JTextField getFirstNameTextField() {
		return firstNameTextField;
	}

	/**
	 * @param firstNameTextField
	 *            the firstNameTextField to set
	 */
	public void setFirstNameTextField(JTextField firstNameTextField) {
		this.firstNameTextField = firstNameTextField;
	}

	/**
	 * @return the surnameTextField
	 */
	public JTextField getSurnameTextField() {
		return surnameTextField;
	}

	/**
	 * @param surnameTextField
	 *            the surnameTextField to set
	 */
	public void setSurnameTextField(JTextField surnameTextField) {
		this.surnameTextField = surnameTextField;
	}

	/**
	 * @return the caMarkTextField
	 */
	public JTextField getCaMarkTextField() {
		return caMarkTextField;
	}

	/**
	 * @param caMarkTextField
	 *            the caMarkTextField to set
	 */
	public void setCaMarkTextField(JTextField caMarkTextField) {
		this.caMarkTextField = caMarkTextField;
	}

	/**
	 * @return the examMarkTextField
	 */
	public JTextField getExamMarkTextField() {
		return examMarkTextField;
	}

	/**
	 * @param examMarkTextField
	 *            the examMarkTextField to set
	 */
	public void setExamMarkTextField(JTextField examMarkTextField) {
		this.examMarkTextField = examMarkTextField;
	}

	/**
	 * @return the overallGradeTextField
	 */
	public JTextField getOverallGradeTextField() {
		return overallGradeTextField;
	}

	/**
	 * @param overallGradeTextField
	 *            the overallGradeTextField to set
	 */
	public void setOverallGradeTextField(JTextField overallGradeTextField) {
		this.overallGradeTextField = overallGradeTextField;
	}
}
