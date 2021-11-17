package main.frames.frameMenu.file.item;

import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class SaveFile extends JMenuItem {

	public SaveFile() {
		super("Save");
		setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
		setIcon(new ImageIcon("resources/icons/save.png"));
		setMnemonic('S');
		setName(getText());
		setEnabled(false);
	}
}
