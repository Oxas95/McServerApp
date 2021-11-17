package main.frames.frameMenu.file;

import java.util.ArrayList;

import javax.swing.JMenuItem;

import main.frames.frameMenu.Menu;
import main.frames.frameMenu.file.item.CloseFile;
import main.frames.frameMenu.file.item.Exit;
import main.frames.frameMenu.file.item.NewFile;
import main.frames.frameMenu.file.item.OpenFile;
import main.frames.frameMenu.file.item.SaveAsFile;
import main.frames.frameMenu.file.item.SaveFile;

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
