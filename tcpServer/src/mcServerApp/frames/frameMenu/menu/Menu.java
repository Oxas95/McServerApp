package mcServerApp.frames.frameMenu.menu;

import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

public abstract class Menu extends JMenu {

	protected ArrayList<JMenuItem> menuItem;
	
	public Menu(String text, char mnemonic) {
		super(text);
		setName(getText());
		setMnemonic(mnemonic);
		
		menuItem = new ArrayList<JMenuItem>();
		addMenuItems();
	}
	
	protected abstract void addMenuItems();
	
	@SuppressWarnings("unchecked")
	public ArrayList<JMenuItem> getMenuItems() {
		return (ArrayList<JMenuItem>) menuItem.clone();
	}
}
