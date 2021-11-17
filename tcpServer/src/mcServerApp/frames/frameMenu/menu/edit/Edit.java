package mcServerApp.frames.frameMenu.menu.edit;

import javax.swing.JMenuItem;

import mcServerApp.frames.frameMenu.menu.Menu;
import mcServerApp.frames.frameMenu.menu.edit.item.Redo;
import mcServerApp.frames.frameMenu.menu.edit.item.Undo;

public class Edit extends Menu {

	public Edit() {
		super("Edit", 'E');
	}

	@Override
	protected void addMenuItems() {
		JMenuItem currentMenuItem = new Undo();
		menuItem.add(currentMenuItem);
		add(currentMenuItem);
		currentMenuItem = new Redo();
		menuItem.add(currentMenuItem);
		add(currentMenuItem);
	}
}
