package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import net.kronos.rkon.core.Rcon;
import net.kronos.rkon.core.ex.AuthenticationException;

public class TcpServer extends AbstractProcessContainer {
	private ServerSocket server;
	private Configuration config;
	private ThreadProcess tp;
	
	public TcpServer(Configuration config) throws IOException {
		this.config = config;
		server = new ServerSocket(config.getAport());
		System.out.println("Creating TCP connection on port : " + config.getAport());
	}
	
	public void connect() throws IOException, AuthenticationException {
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
	
	private void traitement(String message) throws IOException, AuthenticationException {
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
	
	private void sendCmd(String cmd) throws IOException, AuthenticationException {
		Rcon rcon = new Rcon(config.getIp(), config.getRport(), config.getPasswordRcon().getBytes());
		rcon.command(cmd);
		rcon.disconnect();
	}
	
	public void startServer() throws IOException {
		if(process == null) {
			tp = new ThreadProcess(this);
			process = Runtime.getRuntime().exec("cmd /c start /wait " + config.getBatchPath());
			System.out.println("Server is starting");
			tp.start();
		}
		
	}
	
	public void stopServer() throws IOException, AuthenticationException {
		if(process != null) {
			System.out.println("Server stopped");
			sendCmd("stop");
		}
	}
	
	public Configuration getConfig() {
		return config;
	}
}
