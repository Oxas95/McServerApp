package mcServerApp.frames;

import java.awt.event.ActionListener;

import javax.swing.JFrame;

public abstract class IFrame extends JFrame implements ActionListener {

	/**
	 * Default Serial Number
	 */
	private static final long serialVersionUID = 1L;
	
	
	public IFrame() {
		super();
		build();
		addContent();
		addListeners();
	}
	
	/**
	 * Creer la frame (title, size...)
	 */
	protected abstract void build();
	
	/**
	 * ajouter du contenu dans la frame
	 */
	protected abstract void addContent();
	
	/**
	 * object.addActionListener(this); pour chaque button,... qui doit interagir avec les entrees utilisateur
	 */
	protected abstract void addListeners();
}
