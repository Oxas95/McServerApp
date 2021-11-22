package mcServerApp.files;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

public class Configuration {
	
	private JSONObject obj;
	private String fileName;
	//TODO parametre nogui

	/**
	 * Valeur par defaut des champs de type String (utilise dans l’enum Keys)
	 */
	public static String none = "";
	
	public Configuration(String fileName, boolean generateIfNotExists) {
		this.fileName = fileName.replace("/", File.separator);
		try {
			readConfig(fileName, generateIfNotExists);
		} catch (NullPointerException | IOException e) {
			System.err.println("Unable to load configuration");
		}
		if(obj == null) {
			obj = new JSONObject();
		}
	}
	
	public Configuration(String fileName) {
		this(fileName, true);
	}
	
	public Configuration() {}

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
	 * modifier une valeur du fichier de configuration JSON
	 * @param key cle pour laquelle il faut modifier sa valeur
	 * @param value nouvelle valeur
	 */
	public void setValueConfig(Keys key, Object value) {
		obj.put(key.toString(), value);
	}
	
	/**
	 * verifie la presence d’une valeur dans le fichier de configuration et l’ajoute si elle est introuvable
	 * @param k cle et sa valeur par defaut a ajouter si introuvable dans le fichier
	 */
	private void insertIfAbsent(Keys k) {
		if(getValueConfig(k) == null) {
			obj.put(k.toString(), k.getDefaultValue());
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
	 * @param generateIfNotExists generera un fichier de configuration s'il n'existait pas
	 * @throws NullPointerException
	 * @throws IOException
	 */
	public boolean readConfig(String fileName, boolean generateIfNotExists) throws NullPointerException, IOException {
		try {
			fileToJson();
		} catch (FileNotFoundException e) {
			if(generateIfNotExists) generateNewConfigFile(fileName);
			return false;
		} catch (Exception e) {
			System.err.println("Unable to read the file \"" + getAbsolutePathConfigFile() + "\"");
			return false;
		}
		
		/*verification des valeurs des parametres*/
		if(!this.isValid()) { //s'il manque des valeurs
			insertKeysValues();
			jsonToFile();
			return false;
		} else { //si les valeurs sont correctes
			
			/* Modification du separateur  si besoin dans batchPath */
			setValueConfig(Keys.batchPath, ((String) getValueConfig(Keys.batchPath)).replace("/", File.separator));
			
			return true;
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
		jsonToFile();
	}
	
	private void fileToJson() throws FileNotFoundException {
		//TODO gérer les exceptions
		if(fileName.endsWith(".json") == false) fileName += ".json";
		Scanner s = new Scanner(new File(fileName));
		String file = "";
		while(s.hasNext()) {
    		file += s.nextLine();
    	}
    	s.close();
    	obj = new JSONObject(file);
	}
	
	public void jsonToFile() throws IOException, NullPointerException {
    	if(fileName == null) throw new NullPointerException ();
		if(fileName.endsWith(".json") == false) fileName += ".json";
        OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(getAbsolutePathConfigFile()));
        fw.write(obj.toString(4));
        fw.close();
        System.out.println("config file saved at " + getAbsolutePathConfigFile());
    }
	
	/**
	 * Vérifie si l'objet JSON et chaque clé contient une valeur valide
	 * @return true si vérification ok, false sinon
	 */
	public boolean isValid() {
		if(obj != null && fileName != null) {
			if(obj.keySet().size() == Keys.values().length) {
				Keys[] keys = Keys.values();
				for(Keys k : keys) {
					if(!k.check(getValueConfig(k))) {
						return false;
					}
				}
				
				/* startPassword != stopPassword ? */
				String start = (String) getValueConfig(Keys.startPassword);
				String stop  = (String) getValueConfig(Keys.stopPassword);
				if(start.equals(stop)) return false;
				
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
	
	public void setFileName(String newFileName) {
		fileName = newFileName;
		obj = null;
	}
}
