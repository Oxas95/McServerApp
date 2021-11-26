package mcServerApp;

import java.io.IOException;

import mcServerApp.files.Configuration;
import mcServerApp.files.Keys;
import mcServerApp.frames.FrameFileGenerator;
import mcServerApp.process.TcpServer;

public class MainClass {

	public static void main(String[] args) throws IOException {
		if (args.length < 1) {
			//System.err.println("Arguments required to launch : <config file>");
			//System.exit(1);
			FrameFileGenerator fg = new FrameFileGenerator();
			fg.setVisible(true);
		} else {
			Configuration config = new Configuration(args[0]);
			run(config);
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
}
