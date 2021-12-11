package mcServerApp.process;

import main.TcpClient;
import mcServerApp.files.Configuration;
import mcServerApp.files.Keys;
import mcServerApp.frames.FrameGui;

public class ThreadServerKiller extends Thread {
	
	Configuration cfg;
	FrameGui gui;
	Runnable callback;
	
	/**
	 * Fournir au Thread la configuration utilisee et la fenetre graphique s'il y en a une.
	 * @param cfg La configuration utilisee pour le serveur
	 * @param gui La fenetre graphique du server
	 * @param callback fonction à appeler à la fin de la methode run, mettre null si pas de callback
	 */
	public ThreadServerKiller(Configuration cfg, FrameGui gui, Runnable callback) {
		this.cfg = cfg;
		this.gui = gui;
		this.callback = callback;
	}
	
	public void run() {
		gui.waitClose();
		TcpClient.sendExitMessageToLocalHost((int) cfg.getValueConfig(Keys.appPort));
		if(callback != null) callback.run();
	}
}
