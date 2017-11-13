package util;

import java.awt.Color;

/**
 * 
 * @author Pawel Paszki
 * 
 * This utility class provides some constant values
 * used by GUI components
 */
public class Config {

	public static final Color DARK_COLOR = Color.black;
	public static final Color LIGHT_COLOR = new Color(77,254,21);
	public static final Color PANEL_BACKGROUND_COLOR = new Color(33,33,33);
	public static final Color LABELS_FOREGROUND_COLOR = new Color(189,189,189);
	public static final Color ERROR_COLOR = Color.red;
	public static final String INVALID_INPUT = "only digits allowed";
	public static final String NO_INPUT = "some input expected";
	public static final String CONNECTION_REFUSED = "Unable to connect to the server!";
	public static final String CA_MARK_TOOLTIP_TEXT = "CA Mark is calculated by multiplying the CA result by 0.3";
	public static final String EXAM_MARK_TOOLTIP_TEXT = "Exam Mark is calculated by multiplying the exam result by 0.7";
	public static final String OVERALL_MARK_TOOLTIP_TEXT = "Overall Mark is calculated by adding CA Mark and Exam Mark";
}
