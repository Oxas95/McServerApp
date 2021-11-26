package mcServerApp.frames.frameMenu.menu.file.item;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;

import mcServerApp.frames.frameMenu.menu.MenuItem;

public class SaveAsFile extends MenuItem {

	public SaveAsFile() {
		super("Save As");
		setIcon(new ImageIcon(iconsPath + "SaveAs.png"));
		setMnemonic('A');
		setName(getText());
	}
}
