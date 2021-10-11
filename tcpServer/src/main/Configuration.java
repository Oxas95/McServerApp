package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;

import org.json.*;

public class Configuration {
	private JSONObject obj = null;
	private int aport = 0;
	private String passwordStart = null;
	private String passwordStop = null;
	private String batchPath = null;
	private int rport = 0;
	private String ip = null;
	private String passwordRcon = null;

	public Configuration(String fileName) {
		try {
			getConfig(fileName);
		} catch (NullPointerException | IOException e) {
			System.err.println("Unable to load configuration");
		}
	}
	
	private void getConfig(String fileName) throws NullPointerException, IOException {
		readFile(fileName);
		if(obj == null) {
			System.out.println("New file configuration is created");
			System.out.println("Edit the generated file and re-run this app");
			return;
		}
		aport = (int) obj.get("app port");
		rport = (int) obj.get("rcon port");
		if(aport < 1000 || rport < 1000) {
			System.err.println("Port must be upper than 1000");
			obj = null;
		}
		passwordStart =  (String) obj.get("server start password");
		passwordStop  =  (String) obj.get("server stop password");
		batchPath     = ((String) obj.get("batch path")).replace("/", "\\");
		ip            =  (String) obj.get("rcon ip");
		passwordRcon  =  (String) obj.get("rcon password");
		
	}
	
	private void generateNewConfigFile(String fileName) throws NullPointerException, IOException {
		obj = new JSONObject();
		String none = "not set";
		obj.put("server start password", none);
		obj.put("server stop password", none);
		obj.put("app port", 0);
		obj.put("batch path", none);
		obj.put("rcon port", 0);
		obj.put("rcon ip", none);
		obj.put("rcon password", none);
		jsonToFile(fileName);
		obj = null;
	}
	
	private void readFile(String fileName) throws NullPointerException, IOException {
		try {
			fileToJson(fileName);
		} catch (FileNotFoundException e) {
			generateNewConfigFile(fileName);
		}
	}
	
	private void fileToJson(String path) throws FileNotFoundException {
		if(path.endsWith(".json") == false) path += ".json";
		Scanner s = new Scanner(new File(path));
		String file = "";
		while(s.hasNext()) {
    		file += s.nextLine();
    	}
    	s.close();
    	obj = new JSONObject(file);
	}
	
	private void jsonToFile(String fileName) throws IOException, NullPointerException {
    	if(fileName == null) throw new NullPointerException ();
		if(fileName.endsWith(".json") == false) fileName += ".json";
        OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(fileName));
        fw.write(obj.toString(4));
        fw.close();
    }
	
	public boolean isStartPassword(String passwordToTest) {
		return passwordStart.equals(passwordToTest);
	}
	
	public boolean isStopPassword(String passwordToTest) {
		return passwordStop.equals(passwordToTest);
	}
	
	public String getBatchPath() {
		return batchPath;
	}
	
	public boolean isValid() {
		return null != obj;
	}

	public int getAport() {
		return aport;
	}

	public int getRport() {
		return rport;
	}

	public String getIp() {
		return ip;
	}

	public String getPasswordRcon() {
		return passwordRcon;
	}
}
