package main.frames.frameMenu.help;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;

public class About extends JMenuItem {

	public About() {
		super("About");
		setIcon(new ImageIcon("resources/icons/about.png"));
		setMnemonic('A');
		setName(getText());
	}
}
