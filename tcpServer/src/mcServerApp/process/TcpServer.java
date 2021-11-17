package mcServerApp.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import mcServerApp.files.Configuration;
import mcServerApp.files.Keys;
import net.kronos.rkon.core.Rcon;

public class TcpServer extends ProcessContainer {
	private ServerSocket server;
	private Configuration config;
	private ThreadProcess tp;
	
	public TcpServer(Configuration config) throws IOException {
		this.config = config;
		int appPort = (int) config.getValueConfig(Keys.appPort);
		server = new ServerSocket(appPort);
		System.out.println("Creating TCP connection on port : " + appPort);
	}
	
	public void connect() throws IOException {
		System.out.println("Waiting for a connection...");
		Socket client = server.accept();
		BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        String message = in.readLine();
        traitement(message);
		client.close();
	}
	
	public boolean closed() {
		return server.isClosed();
	}
	
	private void traitement(String message) throws IOException {
		if(message.equals(config.getValueConfig(Keys.startPassword))) {
			startServer();
		} else if(message.equals(config.getValueConfig(Keys.stopPassword))) {
			stopServer();
		} else if(message.equals("exit")) {
			server.close();
		} else {
			System.out.println("Nothing to do with message : <" + message + ">");
		}
	}
	
	private void sendCmd(String cmd) {
		try {
			String ipServer = "127.0.0.1";
			int rconPort = (int) config.getValueConfig(Keys.rconPort);
			String rconPassword = (String) config.getValueConfig(Keys.rconPassword);
			Rcon rcon = new Rcon(ipServer, rconPort, rconPassword.getBytes());
			rcon.command(cmd);
			rcon.disconnect();
		} catch(Exception e) {
			System.err.println("Rcon : unable to send a command");
		}
		
	}
	
	public void startServer() throws IOException {
		if(process == null) {
			String batchPath = (String) config.getValueConfig(Keys.batchPath);
			tp = new ThreadProcess(this);
			process = Runtime.getRuntime().exec("cmd /c start /wait " + batchPath);
			System.out.println("Server is starting");
			tp.start();
		}
	}
	
	public void stopServer() {
		if(process != null) {
			sendCmd("stop");
			System.out.println("Server is stopping");
			try {
				int timeout = (int) config.getValueConfig(Keys.timeout);
				String batchPath = (String) config.getValueConfig(Keys.batchPath);
				Thread.sleep(1000 * timeout);
				if(process != null) {
					Runtime.getRuntime().exec(new String[]{ "cmd", "/c", "taskkill /fi \"WINDOWTITLE eq " + batchPath + "*\" /t /f"});
					System.out.println("Process killed");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public Configuration getConfig() {
		return config;
	}
}
