package mcServerApp.frames.frameMenu.menu.file;

import javax.swing.JMenuItem;

import mcServerApp.frames.frameMenu.menu.Menu;
import mcServerApp.frames.frameMenu.menu.file.item.CloseFile;
import mcServerApp.frames.frameMenu.menu.file.item.Exit;
import mcServerApp.frames.frameMenu.menu.file.item.NewFile;
import mcServerApp.frames.frameMenu.menu.file.item.OpenFile;
import mcServerApp.frames.frameMenu.menu.file.item.SaveAsFile;
import mcServerApp.frames.frameMenu.menu.file.item.SaveFile;

public class File extends Menu {
	
	public File() {
		super("File",'F');
	}
	
	@Override
	protected void addMenuItems() {
		JMenuItem currentMenuItem = new NewFile();
		menuItem.add(currentMenuItem);
		add(currentMenuItem);
		addSeparator();
		currentMenuItem = new OpenFile();
		menuItem.add(currentMenuItem);
		add(currentMenuItem);
		currentMenuItem = new CloseFile();
		menuItem.add(currentMenuItem);
		add(currentMenuItem);
		addSeparator();
		currentMenuItem = new SaveFile();
		menuItem.add(currentMenuItem);
		add(currentMenuItem);
		currentMenuItem = new SaveAsFile();
		menuItem.add(currentMenuItem);
		add(currentMenuItem);
		addSeparator();
		currentMenuItem = new Exit();
		menuItem.add(currentMenuItem);
		add(currentMenuItem);
	}
}
