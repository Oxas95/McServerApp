package main.frames;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.swing.*;

import main.files.Configuration;

import java.io.File;

public class FrameBatchGenerator extends JFrame implements ActionListener{
	
	/**
	 * Default Serial Number 
	 */
	private static final long serialVersionUID = 1L;
	
	private static String defaultName = "config.json";
	
	private JTextField text;
	private JButton chooseConfigPath;
	private JButton valider;
	
	public FrameBatchGenerator() {
		super();
		build();
		addContent();
		addListeners();
	}
	
	private void build() {
		setTitle("McServerApp");
		setSize(400, 100);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}
	
	private void addContent() {
		JPanel panel = new JPanel();
		setContentPane(panel);
		panel.setLayout(new FlowLayout());
		
		text = new JTextField(15);
		text.setText(defaultName);
		chooseConfigPath = new JButton("Parcourir");
		valider = new JButton("Valider");
		
		panel.add(new JLabel("Entrez un nom pour votre fichier de configuration :"));
		panel.add(text);
		panel.add(chooseConfigPath);
		panel.add(valider);
	}
	
	private void addListeners() {
		chooseConfigPath.addActionListener(this);
		valider.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		actionChooseConfigPath(source);
		actionValider(source);
	}
	
	private void actionValider(Object source) {
		if(source == valider) {
			Configuration cfg = new Configuration(text.getText());
		}
	}
	
	private void actionChooseConfigPath(Object source) {
		if(source == chooseConfigPath) {
			FileChooser fc = FileChooser.getInstance();
			String path = fc.selectNewFilePath(System.getProperty("user.dir"));
			if(path != null) {
				text.setText(path + ".json");
			}
		}
	}
	
	private void generateBatch(Configuration cfg) {
		try {
			PrintWriter writer = new PrintWriter(System.getProperty("user.dir") + File.separator + cfg.getConfigFileName() + ".bat");
			writer.print("java -jar " + System.getProperty("user.dir") + File.separator + "McServerApp.jar " + cfg.getAbsolutePathConfigFile());
			writer.flush();
			writer.close();
			
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	}
}
