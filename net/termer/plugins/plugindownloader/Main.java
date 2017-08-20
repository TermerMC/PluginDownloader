package net.termer.plugins.plugindownloader;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import net.termer.flps.ButtonHandler;
import net.termer.flps.FLPlugin;
import net.termer.flps.Runnable;
import net.termer.utils.Utils;
import net.xeoh.plugins.base.annotations.PluginImplementation;

@PluginImplementation
public class Main implements FLPlugin {
	public static PluginsWindow pluginsWindow = null;
	public static String[] plugins = null;
	
	public void end() {
		
	}
	
	public String getName() {
		return "PluginDownloader";
	}
	
	public void start() {
		ButtonHandler.addButton("Download Plugins", new Runnable() {
			public void run() {
				pluginsWindow.setVisible(true);
			}
		});
	}
	
	public void preInit() {
		try {
		File plgs = new File("plugins/list.meta");
		try {
			if(!plgs.exists()) {
				plgs.createNewFile();
			}
			download("http://flpluginsupport-ianmb.rhcloud.com/plugins/get.php","plugins/list.meta");
		} catch(IOException e) {
			e.printStackTrace();
		}
		ArrayList<String> pl = new ArrayList<String>();
		try {
			for(String pn : Utils.getFile(plgs)[0].split(":")) {
				pl.add(pn);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		plugins = pl.toArray(new String[0]);
		} catch(Exception e) {
			e.printStackTrace();
		}
		pluginsWindow = new PluginsWindow();
		
	}
	public static void download(String url, String name) throws IOException {
		String fileName = name;
		URL link = new URL(url);
		InputStream in = new BufferedInputStream(link.openStream());
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		int n = 0;
		while (-1!=(n=in.read(buf))) {
			out.write(buf, 0, n);
		}
		out.close();
		in.close();
		byte[] response = out.toByteArray();
		FileOutputStream fos = new FileOutputStream(fileName);
		fos.write(response);
		fos.close();
	}
}
