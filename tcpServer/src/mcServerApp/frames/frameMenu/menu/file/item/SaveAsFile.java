package mcServerApp.frames.frameMenu.menu.file.item;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;

public class SaveAsFile extends JMenuItem {

	public SaveAsFile() {
		super("Save As");
		setIcon(new ImageIcon("resources/icons/save_as.png"));
		setMnemonic('A');
		setName(getText());
	}
}
