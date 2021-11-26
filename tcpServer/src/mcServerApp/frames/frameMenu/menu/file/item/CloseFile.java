package mcServerApp.frames.frameMenu.menu.file.item;

import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import mcServerApp.frames.frameMenu.menu.MenuItem;

public class CloseFile extends MenuItem {

	public CloseFile() {
		super("Close File");
		setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, KeyEvent.CTRL_DOWN_MASK));
		setIcon(new ImageIcon(iconsPath + "Cancel.png"));
		setMnemonic('C');
		setName(getText());
	}
}
