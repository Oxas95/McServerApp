package main.frames.frameMenu.edit;

import javax.swing.JMenuItem;

import main.frames.frameMenu.Menu;
import main.frames.frameMenu.edit.item.Redo;
import main.frames.frameMenu.edit.item.Undo;

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
