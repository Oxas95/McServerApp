package main;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class TcpClient {
	
	private Socket socket = null;

	public TcpClient(String address, int port) throws UnknownHostException, IOException {
		System.out.println("Connection...");
		InetAddress server = InetAddress.getByName(address);
		socket = new Socket(server, port);
	}
	
	public void send(String message) throws IOException {
		System.out.println("Send : <" + message + ">");
		PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
        out.println(message);
        out.flush();
	}
	
	public void close() throws IOException {
		System.out.println("Close connection");
		socket.close();
	}
}
