package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controller.ClientA2;

/**
 * 
 * @author Pawel Paszki
 * This test class checks, if ClientGUI components are properly created.
 * Additionally, input text fields are validated - whether (in-) correct
 * text input results in showing/ hiding error labels
 *
 */
public class ClientUIComponentsTest {

	private ClientA2 client;
	@Before
	public void setUp() throws Exception {
		client = new ClientA2();
	}

	@After
	public void tearDown() throws Exception {
		client = null;
	}

	/**
	 * check, if components are created
	 */
	@Test
	public void testComponentCreation() {
		assertTrue(client != null);
		assertTrue(client.getClientGUI().getModuleNameTextField() != null);
		assertTrue(client.getClientGUI().getStudentIDTextField() != null);
		assertTrue(client.getClientGUI().getSubmitDataButton() != null);
		assertTrue(client.getClientGUI().getInvalidIDInfoLabel() != null);
		assertTrue(client.getClientGUI().getInvalidModuleLengthLabel() != null);
		assertTrue(client.getClientGUI().getFirstNameTextField() != null);
		assertTrue(client.getClientGUI().getSurnameTextField() != null);
		assertTrue(client.getClientGUI().getCaMarkTextField() != null);
		assertTrue(client.getClientGUI().getExamMarkTextField() != null);
		assertTrue(client.getClientGUI().getOverallGradeTextField() != null);
		assertTrue(client.getClientGUI().getCompsToPopulate() != null);
		assertTrue(client.getClientGUI().getErrorMessagePanel() != null);
		assertTrue(client.getClientGUI().getErrorMessageLabel() != null);
	}
	
	/**
	 * check, if all text fields but id and module input are disabled
	 */
	@Test
	public void testComponentsDisabled() {
		assertTrue(client.getClientGUI().getStudentIDTextField().isEditable());
		assertTrue(client.getClientGUI().getModuleNameTextField().isEditable());
		assertFalse(client.getClientGUI().getFirstNameTextField().isEditable());
		assertFalse(client.getClientGUI().getSurnameTextField().isEditable());
		assertFalse(client.getClientGUI().getCaMarkTextField().isEditable());
		assertFalse(client.getClientGUI().getExamMarkTextField().isEditable());
		assertFalse(client.getClientGUI().getOverallGradeTextField().isEditable());
	}

	/**
	 * check, whether input validation works properly for id input
	 */
	@Test
	public void testInvalidIDInput() {
		assertTrue(client.getClientGUI().isValidIDInput() == false);
		assertTrue(client.getClientGUI().getInvalidIDInfoLabel().isVisible() == false);
		client.getClientGUI().getStudentIDTextField().setText("a");
		assertTrue(client.getClientGUI().isValidIDInput() == false);
		assertTrue(client.getClientGUI().getInvalidIDInfoLabel().isVisible() == true);
		client.getClientGUI().getStudentIDTextField().setText("1");
		assertTrue(client.getClientGUI().isValidIDInput());
		assertTrue(client.getClientGUI().getInvalidIDInfoLabel().isVisible() == false);
		client.getClientGUI().getStudentIDTextField().setText("");
		assertTrue(client.getClientGUI().isValidIDInput() == false);
		assertTrue(client.getClientGUI().getInvalidIDInfoLabel().isVisible() == true);
	}

	/**
	 * check, whether input validation works properly for module name input
	 */
	@Test
	public void testInvalidModuleNameInput() {
		assertTrue(client.getClientGUI().isValidModuleNameLength() == false);
		assertTrue(client.getClientGUI().getInvalidModuleLengthLabel().isVisible() == false);
		client.getClientGUI().getModuleNameTextField().setText("M");
		assertTrue(client.getClientGUI().getInvalidModuleLengthLabel().isVisible() == false);
		assertTrue(client.getClientGUI().isValidModuleNameLength()); 
		client.getClientGUI().getModuleNameTextField().setText("");
		assertTrue(client.getClientGUI().getInvalidModuleLengthLabel().isVisible() == true);
		assertTrue(client.getClientGUI().isValidModuleNameLength() == false);
	}
}
