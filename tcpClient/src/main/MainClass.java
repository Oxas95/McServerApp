package main;

import java.io.IOException;

public class MainClass {

	public static void main(String[] args) throws IOException {
		TcpClient client = new TcpClient(args[0], Integer.parseInt(args[1]));
		client.send(args[2]);
		client.close();
	}

}
