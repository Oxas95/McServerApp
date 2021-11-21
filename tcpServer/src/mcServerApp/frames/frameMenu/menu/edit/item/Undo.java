package mcServerApp.frames.frameMenu.menu.edit.item;

import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class Undo extends JMenuItem {

	public Undo() {
		super("Undo");
		setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK));
		setIcon(new ImageIcon("resources/icons/undo.png"));
		setMnemonic('U');
		setName(getText());
	}
}
