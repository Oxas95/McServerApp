package mcServerApp.frames.frameMenu;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

import mcServerApp.files.Configuration;
import mcServerApp.files.Keys;
import mcServerApp.frames.FileChooser;
import mcServerApp.frames.IFrame;
import mcServerApp.frames.frameMenu.menu.Menu;
import mcServerApp.frames.frameMenu.menu.edit.Edit;
import mcServerApp.frames.frameMenu.menu.help.Help;

public class FrameMenu extends IFrame {
	//TODO petit menu pour le fichier de configuration (create, save as...)
	//un bouton launch pour lancer avec la configuration chargée
	//un bouton pour générer un batch
	
	/**
	 * Default Serial Number
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String titleBase = "McServerApp";
	
	private JMenuBar menuBar;
	private ArrayList<JMenuItem> menuItem;
	private Map<Keys, JTextField> textFields;
	private Map<Keys, JLabel> labels;
	
	private File configFile = null;
	private boolean isNewFile;

	public FrameMenu() {
		super();
	}
	
	@Override
	protected void build() {
		setTitle(titleBase);
		setSize(500, 400);
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		menuItem = new ArrayList<JMenuItem>();
		textFields = new HashMap<Keys, JTextField>();
		labels = new HashMap<Keys, JLabel>();
		
		isNewFile = false;
	}

	@Override
	protected void addContent() {
		
		JPanel panel = new JPanel();
		setContentPane(panel);
		panel.setLayout(new GridLayout(0, 2));
		
		menuBar = new JMenuBar();
		addContentMenu();
		
		Keys[] keys = Keys.values();
		for(Keys k : keys) {
			JLabel label = new JLabel("  " + k.toString() + " : ");
			label.setEnabled(false);
			labels.put(k, label);
			panel.add(label);
			JTextField textField = new JTextField("");
			textField.setEnabled(false);
			textFields.put(k, textField);
			panel.add(textField);
		}
		
		this.setJMenuBar(menuBar);
	}
	
	protected void addContentMenu() {
		ArrayList<Menu> menus = new ArrayList<Menu>();
		menus.add(new mcServerApp.frames.frameMenu.menu.file.File());
		menus.add(new Edit());
		menus.add(new Help());
		
		for(Menu m : menus) {
			menuItem.addAll(m.getMenuItems());
			menuBar.add(m);
		}
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
		actionCloseFile(source);
		actionSave(source);
		actionSaveAs(source);
		actionExit(source);
		
		refreshComponentStatut();
	}
	
	private void refreshComponentStatut() {
		//activer ou desactiver les composants de la fenetre si un fichier est ouvert ou non
		boolean fileOpened = isNewFile || ((configFile != null) ? configFile.exists() : false);
		Keys[] keys = Keys.values();
		for(Keys k : keys) {
			JTextField tf = textFields.get(k);
			if(!fileOpened || isNewFile) tf.setText("");
			tf.setEnabled(fileOpened);
			labels.get(k).setEnabled(fileOpened);
		}
		getMenuItemByName("Close File").setEnabled(fileOpened);
		getMenuItemByName("Save").setEnabled(fileOpened);
		getMenuItemByName("Save As").setEnabled(fileOpened);
		
		//definir le titre de la fenetre
		if(isNewFile) {
			this.setTitle(titleBase + " : New File");
		} else if (configFile != null && configFile.exists()) {
			this.setTitle(titleBase + " : " + configFile.getAbsolutePath());
		} else {
			this.setTitle(titleBase);
		}
	}
	
	/**
	 * recherche et recupere le JMenuItem en fonction de son nom dans menuBar
	 * @param name nom du JMenuItem
	 * @return le JMenuItem ou null si introuvable
	 */
	private JMenuItem getMenuItemByName(String name) {
		for(JMenuItem mi : menuItem)
			if(mi.getName().equals(name)) return mi;
		return null;
	}
	
	private void actionNewFile(Object source) {
		JMenuItem menuItem = getMenuItemByName("New File");
		if(source == menuItem) {
			isNewFile = true;
			configFile = null;
		}
	}
	
	private void actionOpenFile(Object source) {
		JMenuItem menuItem = getMenuItemByName("Open File");
		if(source == menuItem) {
			FileChooser fc = FileChooser.getInstance();
			String openFilePath = fc.selectOpenFilePath(System.getProperty("user.dir"));
			if(openFilePath != null) {
				isNewFile = false;
				configFile = new File(openFilePath);
				if(configFile.exists()) {
					Configuration cfg = new Configuration(configFile.getAbsolutePath());
					Keys[] keys = Keys.values();
					for(Keys k : keys) {
						textFields.get(k).setText("" + cfg.getValueConfig(k));
					}
				}
			}
		}
	}
	
	private void actionCloseFile(Object source) {
		JMenuItem menuItem = getMenuItemByName("Close File");
		if(source == menuItem) {
			isNewFile = false;
			configFile = null;
		}
	}

	private void actionSave(Object source) {
		JMenuItem menuItem = getMenuItemByName("Save");
		if(source == menuItem) {
			if(isNewFile) {
				actionSaveAs(getMenuItemByName("Save As"));
			} else {
				
			}
		}
	}
	
	private void actionSaveAs(Object source) {
		JMenuItem menuItem = getMenuItemByName("Save As");
		if(source == menuItem) {
			FileChooser fc = FileChooser.getInstance();
			configFile = new File(fc.selectNewFilePath(System.getProperty("user.dir")));
			if(configFile.exists()) {
				
			}
		}
	}
	
	private void actionExit(Object source) {
		JMenuItem menuItem = getMenuItemByName("Exit");
		if(source == menuItem) {
			System.exit(0);
		}
	}
}
