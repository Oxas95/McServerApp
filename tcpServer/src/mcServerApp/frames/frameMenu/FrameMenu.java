package mcServerApp.frames.frameMenu;

import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.TcpClient;
import mcServerApp.MainClass;
import mcServerApp.files.Configuration;
import mcServerApp.files.InvalidKeysValueException;
import mcServerApp.files.Keys;
import mcServerApp.frames.FileChooser;
import mcServerApp.frames.FrameDialog;
import mcServerApp.frames.FrameGui;
import mcServerApp.frames.IFrame;
import mcServerApp.frames.frameMenu.menu.Menu;
import mcServerApp.frames.frameMenu.menu.MenuItem;
import mcServerApp.frames.frameMenu.menu.build.Build;
import mcServerApp.frames.frameMenu.menu.build.item.FileChecker;
import mcServerApp.frames.frameMenu.menu.build.item.GenerateLauncher;
import mcServerApp.frames.frameMenu.menu.build.item.Launch;
import mcServerApp.frames.frameMenu.menu.file.item.CloseFile;
import mcServerApp.frames.frameMenu.menu.file.item.Exit;
import mcServerApp.frames.frameMenu.menu.file.item.NewFile;
import mcServerApp.frames.frameMenu.menu.file.item.OpenFile;
import mcServerApp.frames.frameMenu.menu.file.item.SaveAsFile;
import mcServerApp.frames.frameMenu.menu.file.item.SaveFile;
import mcServerApp.frames.frameMenu.menu.help.Help;
import mcServerApp.frames.frameMenu.menu.help.item.About;

public class FrameMenu extends IFrame {
	
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
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setIconImage(Toolkit.getDefaultToolkit().getImage(MenuItem.iconsPath + "Diamond.png"));
		
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
		
		refreshComponentStatut();
	}
	
	protected void addContentMenu() {
		ArrayList<Menu> menus = new ArrayList<Menu>();
		menus.add(new mcServerApp.frames.frameMenu.menu.file.File());
		menus.add(new Build());
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
		actionGenerateLauncher(source);
		actionFileChecker(source);
		actionLaunch(source);
		
		actionAbout(source);
		
		refreshComponentStatut();
	}

	private void refreshComponentStatut() {
		//activer ou desactiver les composants de la fenetre si un fichier est ouvert ou non
		boolean fileOpened = isNewFile || ((configFile != null) ? configFile.exists() : false);
		for(Keys k : Keys.values()) {
			JTextField tf = textFields.get(k);
			tf.setEnabled(fileOpened);
			labels.get(k).setEnabled(fileOpened);
		}
		getMenuItemByName("Close File").setEnabled(fileOpened);
		getMenuItemByName("Save").setEnabled(fileOpened);
		getMenuItemByName("Save As").setEnabled(fileOpened);
		getMenuItemByName("Check").setEnabled(fileOpened);
		getMenuItemByName("Generate Launcher").setEnabled(configFile != null);
		getMenuItemByName("Launch").setEnabled(fileOpened);
		
		//redefinir le titre de la fenetre
		if(isNewFile)
			this.setTitle(titleBase + " : New File");
		else if (configFile != null && configFile.exists())
			this.setTitle(titleBase + " : " + configFile.getAbsolutePath());
		else
			this.setTitle(titleBase);
	}
	
	/**
	 * recherche et recupere le JMenuItem en fonction de son nom dans menuBar
	 * @param name nom du JMenuItem
	 * @return le JMenuItem ou null si introuvable
	 */
	private JMenuItem getMenuItemByName(String name) {
		for(JMenuItem mi : menuItem)
			if(mi.getName().equals(name))
				return mi;
		return null;
	}
	
	private Configuration getConfiguration(String path) {
		try {
			
			System.out.println(path);
			Configuration cfg = new Configuration(path, false);
			Keys[] keys = Keys.values();
			for(Keys k : keys)
				cfg.setValueConfig(k, k.parse(textFields.get(k).getText()));
			return cfg;
		} catch (InvalidKeysValueException | NullPointerException e) {
			return null;
		}
	}
	
	private void actionNewFile(Object source) {
		if(source.getClass() == NewFile.class) {
			isNewFile = true;
			configFile = null;
			for(Keys k : Keys.values())
				textFields.get(k).setText("" + k.getDefaultValue());
		}
	}
	
	private void actionOpenFile(Object source) {
		if(source.getClass() == OpenFile.class) {
			String openFilePath = FileChooser.selectOpenFilePath(System.getProperty("user.dir"));
			if(openFilePath != null) {
				isNewFile = false;
				configFile = new File(openFilePath);
				if(configFile.exists()) {
					Configuration cfg = new Configuration(configFile.getAbsolutePath());
					for(Keys k : Keys.values()) {
						textFields.get(k).setText("" + cfg.getValueConfig(k));
					}
				}
			}
		}
	}
	
	private void actionCloseFile(Object source) {
		if(source.getClass() == CloseFile.class) {
			isNewFile = false;
			configFile = null;
			for(Keys k : Keys.values())
				textFields.get(k).setText("");
		}
	}
	
	private void writeConfig(Configuration cfg) {
		try {
			if(check()) {
				cfg.jsonToFile();
			}
			this.configFile = new File(cfg.getAbsolutePathConfigFile());
			isNewFile = false;
		} catch (NullPointerException | IOException e) {
			e.printStackTrace();
			FrameDialog.errorSave(this);
		}
	}
	
	private void save(boolean confirmOverwrite, File configFile) {
		Configuration cfg = getConfiguration(configFile.getPath());
		if(configFile.exists() && confirmOverwrite) {
			Integer res;
			res = FrameDialog.confirmOverwrite(this, cfg.getFileName());
			if(res == JOptionPane.YES_OPTION)
				writeConfig(cfg);
		} else
			writeConfig(cfg);
	}

	private void actionSave(Object source) {
		if(source.getClass() == SaveFile.class) {
			if(isNewFile) {
				actionSaveAs(getMenuItemByName("Save As"));
			} else {
				save(false, this.configFile);
			}
		}
	}
	
	private void actionSaveAs(Object source) {
		if(source.getClass() == SaveAsFile.class) {
			String path = FileChooser.selectNewFilePath(System.getProperty("user.dir"));
			File configFile = null;
			if(path != null) {
				configFile = new File(path);
				save(true, configFile);
			}
		}
	}
	
	private void actionExit(Object source) {
		if(source.getClass() == Exit.class) {
			System.exit(0);
		}
	}
	
	private void generateBatch(Configuration cfg, String folder) {
		String path = folder + File.separator + cfg.getConfigFileName() + ".bat";
		File f = new File(path);
		if(f.exists()) {
			FrameDialog.error(this, "Error", "The file <" + cfg.getConfigFileName() + ".bat> already exists in this folder.");
			return;
		}
		try {
			PrintWriter writer = new PrintWriter(path);
			writer.print("java -jar " + System.getProperty("user.dir") + File.separator + "McServerApp.jar " + cfg.getAbsolutePathConfigFile());
			writer.flush();
			writer.close();
			FrameDialog.info(this, "Information", "The file <" + path + "> was created successfully.");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	}
	
	private void actionGenerateLauncher(Object source) {
		if(source.getClass() == GenerateLauncher.class) {
			String folder = FileChooser.selectFolder(System.getProperty("user.dir"));
			if(folder != null)
				generateBatch(new Configuration(configFile.getAbsolutePath(), false), folder);
		}
	}
	
	private boolean check() {
		for(Keys k : Keys.values()) {
			String errorMessage =  "The value for the <" + k.toString() + "> field is invalid";
			try {
				Object value = k.parse(textFields.get(k).getText());
				if(!k.check(value)) {
					FrameDialog.error(this, "Error", errorMessage);
					return false;
				}
			} catch (InvalidKeysValueException e) {
				e.printStackTrace();
				FrameDialog.error(this, "Error", errorMessage);
				return false;
			}
		}
		return true;
	}
	
	private void actionFileChecker(Object source) {
		if(source.getClass() == FileChecker.class) {
			if(check())
				FrameDialog.custom(this, "Information", "All the fields are valid", "Approval.png");
		}
	}
	
	private void actionLaunch(Object source) {
		if(source.getClass() == Launch.class) {
			
			if(check()) {
				Configuration cfg = getConfiguration(configFile.getPath());
				FrameGui gui = new FrameGui();
				gui.initializeThreads(cfg);
				gui.launch();
				
				JFrame frame = this;
				Thread t = new Thread() {
					public void run() {
						frame.setVisible(false);
						while(gui.isVisible()) {
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						frame.setVisible(true);
						try {
							TcpClient client = new TcpClient("127.0.0.1", (int) cfg.getValueConfig(Keys.appPort));
							client.send("exit");
							client.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				};
				t.start();
			}
		}
	}
	

	private void actionAbout(Object source) {
		if(source.getClass() == About.class) {
			try {
				Desktop.getDesktop().browse(new URI("https://github.com/Oxas95/McServerApp"));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
	}
}
