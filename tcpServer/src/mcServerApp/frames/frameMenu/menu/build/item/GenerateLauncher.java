package mcServerApp.frames.frameMenu.menu.build.item;

import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import mcServerApp.frames.frameMenu.menu.MenuItem;

public class GenerateLauncher extends MenuItem {

	public GenerateLauncher() {
		super("Generate Launcher");
		setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F10, 0, false));
		setIcon(new ImageIcon(iconsPath + "Console.png"));
		setMnemonic('G');
		setName(getText());
	}
}
