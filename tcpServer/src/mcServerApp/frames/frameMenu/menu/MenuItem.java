package mcServerApp.frames.frameMenu.menu;

import javax.swing.JMenuItem;

public abstract class MenuItem extends JMenuItem{
	protected String iconsPath = "resources/Icon8_24px/";
	
	public MenuItem(String text) {
		super(text);
	}
}
