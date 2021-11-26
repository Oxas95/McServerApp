package mcServerApp.frames.frameMenu.menu.help.item;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;

import mcServerApp.frames.frameMenu.menu.MenuItem;

public class About extends MenuItem {

	public About() {
		super("About");
		setIcon(new ImageIcon(iconsPath + "About.png"));
		setMnemonic('A');
		setName(getText());
	}
}
