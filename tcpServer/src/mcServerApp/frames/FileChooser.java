package mcServerApp.frames;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public abstract class FileChooser {
	
	private void FileChooser() {}
	
	/**
	 * Selectionner un chemin pour un nouveau fichier
	 * @param from ouvrir l'explorateur de fichier sur from
	 * @return le chemin et le nom du fichier choisi, null sinon
	 */
	public static String selectNewFilePath(String from) {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Json files", "json");
		chooser.setFileFilter(filter);
	    chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
	    int retrival = chooser.showSaveDialog(null);
	    if (retrival == JFileChooser.APPROVE_OPTION) {
	    	return chooser.getSelectedFile().toString();
	    } else {
	    	return null;
	    }
	}
	
	/**
	 * Selectionner un chemin pour un nouveau fichier
	 * @param from ouvrir l'explorateur de fichier sur from
	 * @return le chemin et le nom du fichier choisi, null sinon
	 */
	public static String selectOpenFilePath(String from) {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Json files", "json");
		chooser.setFileFilter(filter);
	    chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
	    int retrival = chooser.showOpenDialog(null);
	    if (retrival == JFileChooser.APPROVE_OPTION) {
	    	return chooser.getSelectedFile().toString();
	    } else {
	    	return null;
	    }
	}
	
	/**
	 * Selectionner un dossier
	 * @param from ouvrir l'explorateur de fichier sur from
	 * @return le chemin du dossier choisi, null sinon
	 */
	public static String selectFolder(String from) {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Json files", "json");
		chooser.setFileFilter(filter);
	    chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
	    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    int retrival = chooser.showOpenDialog(null);
	    if (retrival == JFileChooser.APPROVE_OPTION) {
	    	return chooser.getSelectedFile().toString();
	    } else {
	    	return null;
	    }
	}
	
}
