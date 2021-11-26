package mcServerApp.frames.frameMenu.menu.file.item;

import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import mcServerApp.frames.frameMenu.menu.MenuItem;

public class SaveFile extends MenuItem {

	public SaveFile() {
		super("Save");
		setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
		setIcon(new ImageIcon(iconsPath + "Save.png"));
		setMnemonic('S');
		setName(getText());
	}
}
