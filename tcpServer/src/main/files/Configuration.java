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
	 * recuperer la valeur du fichier de configuration JSON
	 * @param key cle pour laquelle il faut extraire sa valeur
	 * @return la valeur associee a key si key existe et null sinon
	 */
	public Object getValueConfig(Keys key) {
		try {
			return obj.get(key.toString());
		} catch (JSONException e) {
			System.err.println("Missing parameter \"" + key + "\".");
			return null;
		}
	}
	
	/**
	 * verifie la presence d’une valeur dans le fichier de configuration et l’ajoute si elle est  introuvable
	 * @param k cle et sa valeur par defaut a ajouter si introuvable dans le fichier
	 */
	private void insertIfAbsent(Keys k) {
		if(getValueConfig(k) == null) {
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
		
		/*verification des valeurs des parametres*/
		if(!this.isValid()) { //s'il manque des valeurs
			insertKeysValues();
			jsonToFile(fileName);
			obj = null;
		} else { //si les valeurs sont correctes
			/* startPassword != stopPassword ? */
			String start = (String) getValueConfig(Keys.startPassword);
			String stop  = (String) getValueConfig(Keys.stopPassword);
			if(start.equals(stop)) obj = null;
			
			/* verifie la valeur de chaque clé independamment des autres */
			Keys[] keys = Keys.values();
			for(int i = 0; i < keys.length && obj != null; i++) {
				if(!keys[i].check(obj)) obj = null;
			}
			
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
			obj = null;
		}
	}
	
	private void fileToJson(String path) throws FileNotFoundException {
		//TODO gérer les exceptions
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
		if(obj != null) {
			if(obj.keySet().size() == Keys.values().length) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * obtenir le chemin absolue vers le fichier de configuration
	 * @return
	 */
	public String getAbsolutePathConfigFile() {
		return new File(fileName).getAbsolutePath();
	}
	
	/**
	 * obtenir le nom du fichier de configuration (filename + extension)
	 * @return
	 */
	public String getFileName() {
		String tmp = getAbsolutePathConfigFile();
		String[] path = tmp.split(File.separator.replace("\\", "\\\\"));
		return path[path.length - 1];
	}
	
	/**
	 * obtenir le nom de la configuration
	 * @return
	 */
	public String getConfigFileName() {
		return getFileName().replace(".json", "");
	}
}
