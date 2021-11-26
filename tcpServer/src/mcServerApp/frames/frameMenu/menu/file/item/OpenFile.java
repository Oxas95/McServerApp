package mcServerApp.frames.frameMenu.menu.file.item;

import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import mcServerApp.frames.frameMenu.menu.MenuItem;

public class OpenFile extends MenuItem {

	public OpenFile() {
		super("Open File");
		setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
		setIcon(new ImageIcon(iconsPath + "OpenDocument.png"));
		setMnemonic('O');
		setName(getText());
	}
}
