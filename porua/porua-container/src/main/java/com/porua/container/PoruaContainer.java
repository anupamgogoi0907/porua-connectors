package com.porua.container;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.porua.core.flow.Flow;

public class PoruaContainer {
	public static String PORUA_APPS;

	public static void main(String[] args) throws Exception {
		if (args.length != 0) {
			PORUA_APPS = args[0];
		} else {
			PORUA_APPS = "apps";
		}
		scanAllApps();
		// AppTask.loadSingleApp("classpath:my-flow-jms.xml", null);
	}

	public static void scanSingleApp(File appFolder) throws Exception {
		URL jarUrl = null;
		List<String> listConfigXml = new ArrayList<>();
		File[] appContents = appFolder.listFiles();
		for (File content : appContents) {
			if (content.getName().endsWith(".jar")) {
				jarUrl = content.toURI().toURL();
				System.out.println("Loading: " + jarUrl);
			} else if (content.getName().endsWith(".xml")) {
				listConfigXml.add("file:".concat(content.getAbsolutePath()));
			}
		}
		loadSingleApp(jarUrl, listConfigXml.toArray(new String[listConfigXml.size()]));
	}

	public static void scanAllApps() throws Exception {
		File[] listApp = new File(PORUA_APPS).listFiles();
		for (File appFolder : listApp) {
			if (appFolder.isDirectory()) {
				scanSingleApp(appFolder);
			}
		}
	}

	public static void loadSingleApp(URL jarUrl, String... app) {
		FileSystemXmlApplicationContext ctx = null;
		try {
			ctx = new FileSystemXmlApplicationContext(app);
			Map<String, Flow> mapFlow = ctx.getBeansOfType(Flow.class);
			for (String key : mapFlow.keySet()) {
				Flow flow = mapFlow.get(key);
				flow.addPoruaClassLoader(jarUrl);
				flow.startFlow();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ctx.close();
		}
	}
}
