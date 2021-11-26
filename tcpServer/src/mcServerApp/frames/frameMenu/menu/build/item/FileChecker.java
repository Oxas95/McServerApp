package mcServerApp.frames.frameMenu.menu.build.item;

import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import mcServerApp.frames.frameMenu.menu.MenuItem;

public class FileChecker extends MenuItem {

	public FileChecker() {
		super("Check");
		setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F9, 0, false));
		setIcon(new ImageIcon(iconsPath + "RescanDocument.png"));
		setMnemonic('C');
		setName(getText());
	}

}
