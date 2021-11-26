package mcServerApp.frames.frameMenu.menu.file.item;

import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import mcServerApp.frames.frameMenu.menu.MenuItem;

public class NewFile extends MenuItem {
	
	public NewFile() {
		super("New File");
		setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
		setIcon(new ImageIcon(iconsPath + "Document.png"));
		setMnemonic('N');
		setName(getText());
	}
}
