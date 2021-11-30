package mcServerApp.frames.frameMenu.menu;

import javax.swing.JMenuItem;

public abstract class MenuItem extends JMenuItem{
	public static final String iconsPath = "resources/Icons/";
	
	public MenuItem(String text) {
		super(text);
	}
}
