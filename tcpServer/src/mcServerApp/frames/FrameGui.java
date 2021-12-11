package mcServerApp.frames;

import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import mcServerApp.MainClass;
import mcServerApp.files.Configuration;
import mcServerApp.frames.frameMenu.menu.MenuItem;

public class FrameGui extends JFrame {

	JTextArea textArea;
	Thread server;
	Thread loopWriter;
	Configuration cfg;
	
	public FrameGui() {
		super();
		build();
		addContent();
	}

	private void build() {
		setTitle("McServerApp");
		setSize(500, 400);
		setResizable(true);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		onCloseOperation();
		setLocationRelativeTo(null);
		setIconImage(Toolkit.getDefaultToolkit().getImage(MenuItem.iconsPath + "IndividualServer.png"));
	}

	private void addContent() {
		JPanel panel = new JPanel();
		setContentPane(panel);
		panel.setLayout(new GridLayout(1, 1));
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		
		panel.add(textArea);
	}
	
	private void onCloseOperation() {
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				setVisible(false);
				dispose();
			}
		});
	}
	
	public void initializeThreads(Configuration cfg) {
		this.cfg = cfg;
		JFrame frame = this;
		server = new Thread() {
			public void run() {
				try {
					MainClass.run(cfg);
				} catch (IOException e) {
					FrameDialog.error(frame, "Error", "The server launch was interrupted due to an error.");
				}
			}
		};
		
		loopWriter = new Thread() {
			public void run() {
				PrintStream standardOutput = System.out;
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				PrintStream ps = new PrintStream(baos);
				System.setOut(ps);
				
				
				try {
					while(frame.isVisible()) {
						if(baos.toString().isEmpty() == false) {
							textArea.append(baos.toString());
							baos.reset();
						}
						Thread.sleep(200);
					}
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				System.setOut(standardOutput);
				ps.close();
				try {
					baos.close(); 
				} catch (IOException e) {}
			}
		};
	}
	
	/**
	 * Requiert un appel à la methode InitalizeThreads() avant d'appeler cette fonction
	 */
	public void launch() {
		try {
			setVisible(true);
			loopWriter.start();
			server.start();
		} catch (NullPointerException e) {
			FrameDialog.error(this, "Error", "Unable to launch the server : Initialization undone.");
		}
	}
	
	public void waitClose() {
		while(this.isVisible()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
