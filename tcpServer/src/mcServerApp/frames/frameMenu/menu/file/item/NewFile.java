package mcServerApp.frames.frameMenu.menu.file.item;

import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class NewFile extends JMenuItem {
	
	public NewFile() {
		super("New File");
		setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
		setIcon(new ImageIcon("resources/icons/new.png"));
		setMnemonic('N');
		setName(getText());
	}
}
