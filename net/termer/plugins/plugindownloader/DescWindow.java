package net.termer.plugins.plugindownloader;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import net.termer.utils.Utils;

public class DescWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private DescWindow t = this;
	private String pn = null;
	public DescWindow(String plugin) throws IOException {
		super(plugin);
		pn = plugin;
		setSize(300,130);
		JLabel status = new JLabel("Loading...");
		setLayout(new FlowLayout());
		getContentPane().add(status);
		setVisible(true);
		File p = new File("tmp/"+plugin);
		p.mkdirs();
		p.deleteOnExit();
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new java.awt.event.WindowAdapter() {
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        t.dispose();
		    }
		});
		try {
			Main.download("http://flpluginsupport-ianmb.rhcloud.com/plugins/"+plugin+"/desc.meta", "tmp/"+plugin+"/desc.meta");
		} catch(Exception e) {
			
		}
		JLabel desc = null;
		try {
			desc = new JLabel(Utils.getFile(new File("tmp/"+plugin+"/desc.meta"))[0]);
			getContentPane().add(desc);
			try {
				Main.download("http://flpluginsupport-ianmb.rhcloud.com/plugins/"+plugin+"/approved", "tmp/"+plugin+"/approved");
				status.setForeground(Color.green);
				status.setText("This plugin has been approved");
			} catch(Exception e) {
				status.setForeground(Color.red);
				status.setText("This plugin is not approved");
			}
			JButton dl = new JButton("Download Plugin");
			dl.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						Main.download("http://flpluginsupport-ianmb.rhcloud.com/plugins/"+pn+"/plugin.jar","plugins/"+pn+".jar");
						JOptionPane.showMessageDialog(null, "Plugin downloaded");
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(null, "Failed to download plugin. Try downloading from the website!");
						e1.printStackTrace();
					}
				}
			});
			getContentPane().add(dl);
		} catch(Exception e) {
			e.printStackTrace();
			status.setText("Failed to load plugin :(");
		}
	}
}