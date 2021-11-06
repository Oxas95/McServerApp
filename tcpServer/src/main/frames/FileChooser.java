package main.frames;

import java.io.File;

import javax.swing.JFileChooser;

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
	    chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
	    int retrival = chooser.showSaveDialog(null);
	    if (retrival == JFileChooser.APPROVE_OPTION) {
	    	return chooser.getSelectedFile().toString();
	    } else {
	    	return null;
	    }
	}
	
}
