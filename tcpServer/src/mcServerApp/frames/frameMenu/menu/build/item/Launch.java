package mcServerApp.frames.frameMenu.menu.build.item;

import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import mcServerApp.frames.frameMenu.menu.MenuItem;

public class Launch extends MenuItem {
	
	public Launch() {
		super("Launch");
		setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F11, 0, false));
		setIcon(new ImageIcon(iconsPath + "CircledPlay.png"));
		setMnemonic('L');
		setName(getText());
	}
}
