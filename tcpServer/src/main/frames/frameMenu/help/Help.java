package main.frames.frameMenu.help;

import javax.swing.JMenuItem;

import main.frames.frameMenu.Menu;

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
