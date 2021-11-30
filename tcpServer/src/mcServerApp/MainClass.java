package mcServerApp;

import java.io.IOException;

import mcServerApp.files.Configuration;
import mcServerApp.files.Keys;
import mcServerApp.frames.FrameGui;
import mcServerApp.frames.frameMenu.FrameMenu;
import mcServerApp.process.TcpServer;

public class MainClass {

	//TODO gerer les exceptions
	//TODO faire tous les tests d'utilisation
	//TODO faire la javadoc
	//TODO rendre executable sur linux et macOS
	//TODO Possibilite de changer le langage
	
	public static void main(String[] args) throws IOException {
		if (args.length < 1) {
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
		gui.initializeThreads(cfg);
		gui.launch();
	}
}
