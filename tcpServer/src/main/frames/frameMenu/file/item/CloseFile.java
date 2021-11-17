package main.frames.frameMenu.file.item;

import java.awt.event.KeyEvent;

import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class CloseFile extends JMenuItem {

	public CloseFile() {
		super("Close File");
		setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, KeyEvent.CTRL_DOWN_MASK));
		//currentMenuItem.setIcon(new ImageIcon("resources/icons/close.png"));
		setMnemonic('C');
		setName(getText());
		setEnabled(false);
	}
}
