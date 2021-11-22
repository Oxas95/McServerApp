package mcServerApp.frames;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import mcServerApp.files.Configuration;


public class FrameFileGenerator extends IFrame implements ActionListener{
	
	/**
	 * Default Serial Number 
	 */
	private static final long serialVersionUID = 1L;
	
	private static String defaultName = "config.json";
	
	private JTextField text;
	private JButton chooseConfigPath;
	private JButton valider;
	
	@Override
	protected void build() {
		setTitle("McServerApp");
		setSize(400, 100);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}
	
	@Override
	protected void addContent() {
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
	
	@Override
	protected void addListeners() {
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
			new Configuration(text.getText());
		}
	}
	
	private void actionChooseConfigPath(Object source) {
		if(source == chooseConfigPath) {
			String path = FileChooser.selectNewFilePath(System.getProperty("user.dir"));
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
