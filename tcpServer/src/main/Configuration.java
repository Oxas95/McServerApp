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
	private Integer aport;
	private String passwordStart = null;
	private String passwordStop = null;
	private String batchPath = null;
	private Integer rport;
	private String ip = null;
	private String passwordRcon = null;
	private Boolean autoStart;
	private static String none = "not set";

	public Configuration(String fileName) {
		try {
			readConfig(fileName);
		} catch (NullPointerException | IOException e) {
			System.err.println("Unable to load configuration");
		}
	}
	
	private void insertKeysValues() {
		try {
			obj.get(Keys.startPassword.toString());
		} catch(JSONException e) {
			obj.put(Keys.startPassword.toString(), none);
		}
		try {
			obj.get(Keys.stopPassword.toString());
		} catch(JSONException e) {
			obj.put(Keys.stopPassword.toString(), none);
		}
		try {
			obj.get(Keys.appPort.toString());
		} catch(JSONException e) {
			obj.put(Keys.appPort.toString(), 0);
		}
		try {
			obj.get(Keys.batchPath.toString());
		} catch(JSONException e) {
			obj.put(Keys.batchPath.toString(), none);
		}
		try {
			obj.get(Keys.rconPort.toString());
		} catch(JSONException e) {
			obj.put(Keys.rconPort.toString(), 0);
		}
		try {
			obj.get(Keys.rconIp.toString());
		} catch(JSONException e) {
			obj.put(Keys.rconIp.toString(), none);
		}
		try {
			obj.get(Keys.rconPassword.toString());
		} catch(JSONException e) {
			obj.put(Keys.rconPassword.toString(), none);
		}
		try {
			obj.get(Keys.autoStart.toString());
		} catch(JSONException e) {
			obj.put(Keys.autoStart.toString(), false);
		}
	}
	
	private Object getValueConfig(String key) {
		try {
			return obj.get(key);
		} catch (JSONException e) {
			System.err.println("Missing parameter \"" + key + "\". Edit the config file to set the value of the new parameter (auto added).");
			return null;
		}
	}
	
	private void readConfig(String fileName) throws NullPointerException, IOException, JSONException {
		readFile(fileName);
		if(obj == null) {
			System.out.println("New file configuration is created");
			System.out.println("Edit the generated file and re-run this app");
			return;
		}
		
		/*Lecture des paramètres*/
		aport = (Integer) getValueConfig(Keys.appPort.toString());
		rport = (Integer) getValueConfig(Keys.rconPort.toString());
		if(aport < 1000 || rport < 1000) {
			System.err.println("Port must be upper than 1000");
			obj = null;
		}
		passwordStart =  (String)  getValueConfig(Keys.startPassword.toString());
		passwordStop  =  (String)  getValueConfig(Keys.stopPassword.toString());
		batchPath     = ((String)  getValueConfig(Keys.batchPath.toString())).replace("/", "\\");
		ip            =  (String)  getValueConfig(Keys.rconIp.toString());
		passwordRcon  =  (String)  getValueConfig(Keys.rconPassword.toString());
		autoStart	  =  (Boolean) getValueConfig(Keys.autoStart.toString());
		
		if(!this.isValid()) {
			insertKeysValues();
			jsonToFile(fileName);
		}
	}
	
	private void generateNewConfigFile(String fileName) throws NullPointerException, IOException {
		obj = new JSONObject();
		insertKeysValues();
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
	
	/**
	 * Vérifie si l'objet JSON et chaque clé contient une valeur
	 * @return true si vérification ok, false sinon
	 */
	public boolean isValid() {
		try {
			return null != obj && this.aport != null && this.autoStart != null && this.batchPath != null
					&& this.ip != null && this.passwordRcon != null && this.passwordStart != null
					&& this.passwordStop != null && this.rport != null;
		} catch(Exception e) {
			return false;
		}
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
	
	public boolean getAutoStart() {
		return autoStart;
	}
}
