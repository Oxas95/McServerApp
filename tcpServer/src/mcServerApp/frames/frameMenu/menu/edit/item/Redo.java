package mcServerApp.frames.frameMenu.menu.edit.item;

import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class Redo extends JMenuItem {

	public Redo() {
		super("Redo");
		setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, KeyEvent.CTRL_DOWN_MASK));
		setIcon(new ImageIcon("resources/icons/redo.png"));
		setMnemonic('R');
		setName(getText());
	}
}
