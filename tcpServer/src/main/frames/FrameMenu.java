package main.frames;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class FrameMenu extends IFrame {
	//TODO petit menu pour le fichier de configuration (create, save as...)
	//un bouton launch pour lancer avec la configuration chargée
	//un bouton pour générer un batch
	
	/**
	 * Default Serial Number
	 */
	private static final long serialVersionUID = 1L;
	
	private JMenuBar menuBar;
	private ArrayList<JMenuItem> menuItem;

	public FrameMenu() {
		super();
	}
	
	@Override
	protected void build() {
		setTitle("McServerApp");
		setSize(500, 600);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		menuItem = new ArrayList<JMenuItem>();
	}

	@Override
	protected void addContent() {
		JPanel panel = new JPanel();
		setContentPane(panel);
		
		menuBar = new JMenuBar();
		menuBar.add(addContentMenuFile());
		menuBar.add(addContentMenuEdit());
		menuBar.add(addContentMenuHelp());
		menuBar.setPreferredSize(new Dimension((int) this.getSize().getWidth() - 20, 20));
		
		panel.add(menuBar);
		
	}
	
	protected JMenu addContentMenuFile() {
		JMenu currentMenu = new JMenu("File");
		currentMenu.setName(currentMenu.getText());
		currentMenu.setMnemonic('F');
		
		JMenuItem currentMenuItem = new JMenuItem("New File");
		currentMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
		currentMenuItem.setIcon(new ImageIcon("resources/icons/new.png"));
		currentMenuItem.setMnemonic('N');
		currentMenuItem.setName(currentMenuItem.getText());
		menuItem.add(currentMenuItem);
		currentMenu.add(currentMenuItem);
		currentMenu.addSeparator();
		currentMenuItem = new JMenuItem("Open File");
		currentMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
		currentMenuItem.setIcon(new ImageIcon("resources/icons/open.png"));
		currentMenuItem.setMnemonic('O');
		currentMenuItem.setName(currentMenuItem.getText());
		menuItem.add(currentMenuItem);
		currentMenu.add(currentMenuItem);
		currentMenuItem = new JMenuItem("Save");
		currentMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
		currentMenuItem.setIcon(new ImageIcon("resources/icons/save.png"));
		currentMenuItem.setMnemonic('S');
		currentMenuItem.setName(currentMenuItem.getText());
		menuItem.add(currentMenuItem);
		currentMenu.add(currentMenuItem);
		currentMenuItem = new JMenuItem("Save As");
		currentMenuItem.setIcon(new ImageIcon("resources/icons/save_as.png"));
		currentMenuItem.setMnemonic('A');
		currentMenuItem.setName(currentMenuItem.getText());
		menuItem.add(currentMenuItem);
		currentMenu.add(currentMenuItem);
		currentMenu.addSeparator();
		currentMenuItem = new JMenuItem("Exit");
		currentMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, KeyEvent.ALT_DOWN_MASK));
		currentMenuItem.setIcon(new ImageIcon("resources/icons/exit.png"));
		currentMenuItem.setMnemonic('X');
		currentMenuItem.setName(currentMenuItem.getText());
		menuItem.add(currentMenuItem);
		currentMenu.add(currentMenuItem);
		return currentMenu;
	}
	
	protected JMenu addContentMenuEdit() {
		JMenu currentMenu = new JMenu("Edit");
		currentMenu.setName(currentMenu.getText());
		currentMenu.setMnemonic('E');
		
		JMenuItem currentMenuItem = new JMenuItem("Undo");
		currentMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, KeyEvent.CTRL_DOWN_MASK));
		currentMenuItem.setIcon(new ImageIcon("resources/icons/undo.png"));
		currentMenuItem.setMnemonic('U');
		currentMenuItem.setName(currentMenuItem.getText());
		menuItem.add(currentMenuItem);
		currentMenu.add(currentMenuItem);
		currentMenuItem = new JMenuItem("Redo");
		currentMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, KeyEvent.CTRL_DOWN_MASK));
		currentMenuItem.setIcon(new ImageIcon("resources/icons/redo.png"));
		currentMenuItem.setMnemonic('R');
		currentMenuItem.setName(currentMenuItem.getText());
		menuItem.add(currentMenuItem);
		currentMenu.add(currentMenuItem);
		
		return currentMenu;
	}
	
	protected JMenu addContentMenuHelp() {
		JMenu currentMenu = new JMenu("Help");
		currentMenu.setName(currentMenu.getText());
		currentMenu.setMnemonic('H');
		
		JMenuItem currentMenuItem = new JMenuItem("About");
		currentMenuItem.setIcon(new ImageIcon("resources/icons/about.png"));
		currentMenuItem.setMnemonic('A');
		currentMenuItem.setName(currentMenuItem.getText());
		menuItem.add(currentMenuItem);
		currentMenu.add(currentMenuItem);
		
		return currentMenu;
	}

	@Override
	protected void addListeners() {
		for(JMenuItem mi : menuItem)
			mi.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		actionNewFile(source);
		actionOpenFile(source);
	}
	
	/**
	 * recherche et récupère le JMenuItem en fonction de son nom dans menuBar
	 * @param name nom du JMenuItem
	 * @return le JMenuItem ou null si introuvable
	 */
	private JMenuItem getMenuItemByName(String name) {
		for(JMenuItem mi : menuItem)
			if(mi.getName().equals(name)) return mi;
		return null;
	}
	
	private void actionNewFile(Object source) {
		JMenuItem newFile = getMenuItemByName("New File");
		if(source == newFile) {
			FileChooser fc = FileChooser.getInstance();
			String newFilePath = fc.selectNewFilePath(System.getProperty("user.dir"));
		}
	}
	
	private void actionOpenFile(Object source) {
		JMenuItem newFile = getMenuItemByName("Open File");
		if(source == newFile) {
			FileChooser fc = FileChooser.getInstance();
			String openFilePath = fc.selectOpenFilePath(System.getProperty("user.dir"));
		}
	}
}
