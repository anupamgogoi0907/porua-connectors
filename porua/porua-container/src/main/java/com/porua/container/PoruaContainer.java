package com.porua.container;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.porua.core.flow.Flow;

public class PoruaContainer {
	public static String PORUA_APPS = "app/";

	public static void main(String[] args) throws Exception {
		if (args.length != 0) {
			PORUA_APPS = args[0];
		}
		scanAllApps();
	}

	public static void scanSingleApp(File jarApp) throws Exception {
		String appDir = PORUA_APPS + jarApp.getName().substring(0, jarApp.getName().length() - ".jar".length()) + "/";
		Path appDirPath = Paths.get(appDir);
		Files.createDirectories(Paths.get(appDir));
		List<String> listConfigXml = extractJar(jarApp, appDirPath);
		loadSingleApp(jarApp.toURI().toURL(), listConfigXml.toArray(new String[listConfigXml.size()]));
	}

	public static void scanAllApps() throws Exception {
		File[] listApp = new File(PORUA_APPS).listFiles();
		for (File appFolder : listApp) {
			if (appFolder.isDirectory()) {
				scanSingleApp(appFolder);
			}
		}
	}

	private static void loadSingleApp(URL jarUrl, String... app) {
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

	private static List<String> extractJar(File jarApp, Path appDirPath) throws Exception {
		List<String> listConfigXml = new ArrayList<>();
		JarFile jarFile = new JarFile(jarApp);
		Enumeration<JarEntry> en = jarFile.entries();
		while (en.hasMoreElements()) {
			JarEntry entry = en.nextElement();
			if (!entry.getName().contains("/") && entry.getName().endsWith(".xml")) {
				Path path = appDirPath.resolve(entry.getName());
				if (path.toFile().exists()) {
					Files.delete(path);
				}
				Files.copy(jarFile.getInputStream(entry), path);
				listConfigXml.add("file:".concat(path.toFile().getAbsolutePath()));
			}
		}
		jarFile.close();
		return listConfigXml;
	}
}
