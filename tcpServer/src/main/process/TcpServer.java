package main.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import main.files.Configuration;
import net.kronos.rkon.core.Rcon;
import net.kronos.rkon.core.ex.AuthenticationException;

public class TcpServer extends ProcessContainer {
	private ServerSocket server;
	private Configuration config;
	private ThreadProcess tp;
	
	public TcpServer(Configuration config) throws IOException {
		this.config = config;
		server = new ServerSocket(config.getAport());
		System.out.println("Creating TCP connection on port : " + config.getAport());
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
		if(config.isStartPassword(message)) {
			startServer();
		} else if(config.isStopPassword(message)) {
			stopServer();
		} else if(message.equals("exit")) {
			server.close();
		} else {
			System.out.println("Nothing to do with message : <" + message + ">");
		}
	}
	
	private void sendCmd(String cmd) {
		try {
			Rcon rcon = new Rcon(config.getIp(), config.getRport(), config.getRconPassword().getBytes());
			rcon.command(cmd);
			rcon.disconnect();
		} catch(Exception e) {
			System.err.println("Rcon : unable to send a command");
		}
		
	}
	
	public void startServer() throws IOException {
		if(process == null) {
			tp = new ThreadProcess(this);
			process = Runtime.getRuntime().exec("cmd /c start /wait " + config.getBatchPath());
			System.out.println("Server is starting");
			tp.start();
		}
	}
	
	public void stopServer() {
		if(process != null) {
			sendCmd("stop");
			System.out.println("Server is stopping");
			try {
				Thread.sleep(1000 * config.getTimeout());
				if(process != null) {
					Runtime.getRuntime().exec(new String[]{ "cmd", "/c", "taskkill /fi \"WINDOWTITLE eq " + config.getBatchPath() + "*\" /t /f"});
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
