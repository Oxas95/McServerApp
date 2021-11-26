package mcServerApp.frames;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public abstract class FrameDialog {

	public static void info(JFrame frame, String title, String message) {
		JOptionPane.showMessageDialog(frame, message, title, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static int confirm(JFrame frame, String title, String message) {
		return JOptionPane.showConfirmDialog(frame, message, title, JOptionPane.YES_NO_OPTION);
	}
	
	public static int confirmOverwrite(JFrame frame, String fileName) {
		return confirm(frame, "Confirm registration", fileName + " already exists. Do you want to replace it?");
	}
	
	public static void error(JFrame frame, String title, String message) {
		JOptionPane.showMessageDialog(frame, message, title, JOptionPane.ERROR_MESSAGE);
	}
	
	public static void errorSave(JFrame frame) {
		error(frame, "Warning", "An error occurred while saving the file");
	}
}
