package main.frames;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileChooser {
	
	private static FileChooser fc = null;
	
	private void FileChooser() {}
	
	public static FileChooser getInstance() {
		if(fc == null) {
			fc = new FileChooser();
		}
		return fc;
	}
	
	/**
	 * Selectionner un chemin pour un nouveau fichier
	 * @param from ouvrir l'explorateur de fichier sur from
	 * @return le chemin et le nom du fichier choisi, null sinon
	 */
	public String selectNewFilePath(String from) {
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
	public String selectOpenFilePath(String from) {
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
	
}
