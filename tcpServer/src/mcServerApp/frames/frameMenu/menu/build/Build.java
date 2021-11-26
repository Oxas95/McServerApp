package mcServerApp.frames.frameMenu.menu.build;

import javax.swing.JMenuItem;

import mcServerApp.frames.frameMenu.menu.Menu;
import mcServerApp.frames.frameMenu.menu.build.item.FileChecker;
import mcServerApp.frames.frameMenu.menu.build.item.GenerateLauncher;
import mcServerApp.frames.frameMenu.menu.build.item.Launch;

public class Build extends Menu {

	public Build() {
		super("Build", 'B');
	}

	@Override
	protected void addMenuItems() {
		JMenuItem currentMenuItem = new FileChecker();
		menuItem.add(currentMenuItem);
		add(currentMenuItem);
		currentMenuItem = new GenerateLauncher();
		menuItem.add(currentMenuItem);
		add(currentMenuItem);
		currentMenuItem = new Launch();
		menuItem.add(currentMenuItem);
		add(currentMenuItem);
	}

}
