package mcServerApp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import mcServerApp.files.Configuration;
import mcServerApp.files.Keys;
import mcServerApp.frames.FrameDialog;
import mcServerApp.frames.FrameGui;
import mcServerApp.frames.frameMenu.FrameMenu;
import mcServerApp.process.TcpServer;
import mcServerApp.process.ThreadServerKiller;

public class MainClass {

	//TODO faire un bouton pour remplir automatiquement le fichier args.txt dans resources
	//TODO gerer les exceptions
	//TODO faire tous les tests d'utilisation
	//TODO faire la javadoc
	//TODO rendre executable sur linux et macOS
	//TODO Possibilite de changer le langage
	
	public static void main(String[] args) throws IOException {
		//FrameDialog.info(null, "Information", Paths.get("").toAbsolutePath().toString());
		if(args.length == 0) {
			args = getArgsInResources();
		}
		if (args.length != 1) {
			FrameMenu fm = new FrameMenu();
			fm.setVisible(true);
		} else {
			Configuration config = new Configuration(args[0]);
			boolean noGui = (boolean) config.getValueConfig(Keys.noGui);
				if(noGui)
					run(config);
				else
					runGui(config);
		}
	}
	
	public static String[] getArgsInResources() {
		String filePath = "Resources/args.txt";
		try {
			Path path = Paths.get(filePath);
			List<String> lines = Files.readAllLines(path);
			if(lines.size() == 1) {
				return lines.get(0).split(" ");
			}
		} catch (IOException e) {
			java.io.File f = new java.io.File(filePath);
			if(!f.exists())
				try {
					f.createNewFile();
				} catch (IOException e1) {}
		}
		return new String[] {};
	}
	
	public static void run(Configuration cfg) throws IOException {
		if(!cfg.isValid()) return;
		TcpServer server = new TcpServer(cfg);
		if((boolean) cfg.getValueConfig(Keys.autoStart)) server.startServer();
		while(!server.closed()) {
			try {
				server.connect();
			} catch (Exception e) {
				System.err.println("An error occurred during the connection attempt.");
				//e.printStackTrace();
			}
		}
		server.stopServer();
	}
	
	public static void runGui(Configuration cfg) {
		FrameGui gui = new FrameGui();
		
		if(!cfg.isValid()) {
			FrameDialog.error(gui, "Error", "Cannot start the server because the configuration file is invalid.");
			return;
		} else {
			gui.initializeThreads(cfg);
			gui.launch();
			new ThreadServerKiller(cfg, gui, null).start();
		}
	}
}
