package net.termer.plugins.plugindownloader;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class PluginsWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private String lp = "";
	public PluginsWindow() {
		super("Available Plugins");
		setSize(350,100);
		setResizable(false);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		try {
			setIconImage(ImageIO.read(new File("jafl.ico")));
		} catch (IOException e2) {
			System.out.println("[PluginDownloader] Error whilst setting up Plugin Window");
			e2.printStackTrace();
		}
		for(String p : Main.plugins) {
			System.out.println(p);
			JButton pb = new JButton(p);
			lp = p;
			pb.addActionListener(new ActionListener() {
				private String pn = lp;
				public void actionPerformed(ActionEvent e) {
					try {
						new DescWindow(pn);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			});
			getContentPane().add(pb);
		}
		setLayout(new FlowLayout());
		getContentPane().add(new JLabel("Click on a plugin to see description"));
	}
}