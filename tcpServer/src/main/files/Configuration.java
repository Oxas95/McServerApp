package main.files;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.util.Scanner;

import org.json.*;

public class Configuration {
	
	@Override
	public String toString() {
		return "Configuration [obj=" + obj + ", appPort=" + appPort + ", startPassword=" + startPassword
				+ ", stopPassword=" + stopPassword + ", batchPath=" + batchPath + ", rconPort=" + rconPort + ", rconIp="
				+ rconIp + ", rconPassword=" + rconPassword + ", autoStart=" + autoStart + ", timeout=" + timeout + "]";
	}
	
	private Integer appPort = null; 
	private Boolean autoStart = null;
	private String batchPath = null;
	private String rconIp = null;
	private String rconPassword = null;
	private Integer rconPort = null;
	private String startPassword = null;
	private String stopPassword = null;
	private Integer timeout = null;
	private JSONObject obj = null;
	private String fileName;
	//TODO parametre nogui

	/**
	 * Valeur par defaut des champs de type String (utilise dans l’enum Keys)
	 */
	public static String none = "not set";
	
	public Configuration(String fileName) {
		this.fileName = fileName.replace("/", File.separator);
		try {
			readConfig(fileName);
		} catch (NullPointerException | IOException e) {
			System.err.println("Unable to load configuration");
		}
	}
	
	/**
	 * verifie la presence d’une valeur dans le fichier de configuration et l’ajoute si elle est  introuvable
	 * @param k cle et sa valeur par defaut a ajouter si introuvable dans le fichier
	 */
	private void insertIfAbsent(Keys k) {
		try {
			obj.get(k.toString());
		} catch(JSONException e) {
			Object tmp = k.getDefaultValue();
			obj.put(k.toString(), tmp.getClass().cast(tmp));
		}
	}
	
	/**
	 * ajoute toutes les valeurs manquantes dans le fichier de configuration
	 */
	private void insertKeysValues() {
		for(Keys k : Keys.values()) {
			insertIfAbsent(k);
		}
	}
	
	/**
	 * recuperer la valeur du fichier de configuration JSON
	 * @param key cle pour laquelle il faut extraire sa valeur
	 * @return la valeur associee a key si key existe et null sinon
	 */
	private Object getValueConfig(String key) {
		try {
			return obj.get(key);
		} catch (JSONException e) {
			System.err.println("Missing parameter \"" + key + "\". Edit the config file to set the value of this new parameter added.");
			//Pas vraiment ajoutes. c'est fait plus tard
			return null;
		}
	}
	
	/**
	 * lecture du fichier et stockage des valeurs lues dans les champs de la classe Configuration
	 * ajoute les cles manquantes dans le fichier de configuration et ne lance pas le serveur
	 * @param fileName chemin vers le fichier de configuration
	 * @throws NullPointerException
	 * @throws IOException
	 */
	private void readConfig(String fileName) throws NullPointerException, IOException {
		readFile(fileName);
		if(obj == null) { //le fichier de configuration n'existe pas
			System.out.println("New file configuration is created");
			System.out.println("Edit the generated file and re-run this app");
			return;
		}
		
		/*Lecture des parametres*/
		Field[] fsk = Keys.class.getDeclaredFields();
		for(Field fk : fsk) {
			try {
				Keys k = (Keys) fk.get(fk.getName());
				Field ft = Configuration.class.getDeclaredField(fk.getName());
				Object value = getValueConfig(k.toString());
				ft.set(this, value.getClass().cast(value));
				
			} catch (Exception e) {
				//on ignore juste les erreurs
			}
		}
		
		/*Modification du séparateur si besoin*/
		batchPath = batchPath.replace("/", File.separator);
		
		/*Verification des valeurs des parametres*/
		if(!this.isValid()) {
			insertKeysValues();
			jsonToFile(fileName);
		} else {
			if(appPort < 1000 || rconPort < 1000) {
				System.err.println("Port must be upper than 1000");
				obj = null;
			}
			//TODO verifier startPassword != stopPassword
			//TODO demander un mot de passe de taille minimum 8
		}
	}
	
	/**
	 * genere un nouveau fichier de configuration
	 * @param fileName nom du fichier
	 * @throws NullPointerException
	 * @throws IOException
	 */
	private void generateNewConfigFile(String fileName) throws NullPointerException, IOException {
		obj = new JSONObject();
		insertKeysValues();
		jsonToFile(fileName);
		obj = null;
	}
	
	/**
	 * lit le fichier de configuration JSON et stocke les donnees dans obj.
	 * genere le fichier de configuration s’il n’existe pas
	 * @param fileName chemin vers le fichier
	 * @throws NullPointerException
	 * @throws IOException
	 */
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
	
	/**
	 * Vérifie si l'objet JSON et chaque clé contient une valeur
	 * @return true si vérification ok, false sinon
	 */
	public boolean isValid() {
		boolean b = true;
		Field[] fields = this.getClass().getDeclaredFields();
		try {
			for(Field f : fields) {
				b = b && f.get(this) != null;
			}
		} catch (IllegalAccessException | IllegalArgumentException e) {
			return false;
		}
		return b;
	}
	

	public boolean isStartPassword(String passwordToTest) {
		return startPassword.equals(passwordToTest);
	}
	
	public boolean isStopPassword(String passwordToTest) {
		return stopPassword.equals(passwordToTest);
	}
	
	public String getBatchPath() {
		return batchPath;
	}

	public int getAport() {
		return appPort;
	}

	public int getRport() {
		return rconPort;
	}

	public String getIp() {
		return rconIp;
	}

	public String getRconPassword() {
		return rconPassword;
	}
	
	public boolean getAutoStart() {
		return autoStart;
	}
	
	public int getTimeout() {
		return timeout;
	}
	
	public String getAbsolutePathConfigFile() {
		return new File(fileName).getAbsolutePath();
	}
	
	public String getFileName() {
		String tmp = getAbsolutePathConfigFile();
		String[] path = tmp.split(File.separator.replace("\\", "\\\\"));
		return path[path.length - 1];
	}
	
	public String getConfigFileName() {
		return getFileName().replace(".json", "");
	}
}
