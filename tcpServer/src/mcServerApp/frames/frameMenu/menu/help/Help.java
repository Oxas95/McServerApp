package mcServerApp.frames.frameMenu.menu.help;

import javax.swing.JMenuItem;

import mcServerApp.frames.frameMenu.menu.Menu;
import mcServerApp.frames.frameMenu.menu.help.item.About;

public class Help extends Menu {

	public Help() {
		super("Help", 'H');
	}
	
	@Override
	protected void addMenuItems() {
		JMenuItem currentMenuItem = new About();
		menuItem.add(currentMenuItem);
		add(currentMenuItem);
	}
}
