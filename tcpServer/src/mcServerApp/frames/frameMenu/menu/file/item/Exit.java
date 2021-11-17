package mcServerApp.frames.frameMenu.menu.file.item;

import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class Exit extends JMenuItem {

	public Exit() {
		super("Exit");
		setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, KeyEvent.ALT_DOWN_MASK));
		setIcon(new ImageIcon("resources/icons/exit.png"));
		setMnemonic('X');
		setName(getText());
	}
}
