package mcServerApp.frames.frameMenu.menu.file.item;

import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class OpenFile extends JMenuItem {

	public OpenFile() {
		super("Open File");
		setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
		setIcon(new ImageIcon("resources/icons/open.png"));
		setMnemonic('O');
		setName(getText());
	}
}
