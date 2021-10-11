package main;

import java.io.IOException;

import net.kronos.rkon.core.ex.AuthenticationException;

public class MainClass {

	public static void main(String[] args) throws IOException, AuthenticationException {
		if (args.length < 1) {
			System.err.println("Arguments required to launch : <config file>");
			System.exit(1);
		}
		
		Configuration config = new Configuration(args[0]);
		if(!config.isValid()) return;
		TcpServer server = new TcpServer(config);
		server.startServer();
		while(!server.closed()) {
			try {
				server.connect();
				
			} catch (Exception e) {
				System.err.println("An error occurred during the connection attempt :");
				e.printStackTrace();
			}
		}
		server.stopServer();
	}

}
