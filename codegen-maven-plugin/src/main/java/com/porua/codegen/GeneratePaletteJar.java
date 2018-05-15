package com.porua.codegen;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

import com.porua.core.tag.Connector;
import com.porua.core.tag.ConnectorConfig;

public class GeneratePaletteJar {
	public static void generatePaletteAssetsJar(String jarName) throws Exception {
		JarOutputStream jarOutputStream = createJarOutputStream(jarName.concat("-palette"));
		addClasss(jarOutputStream);
		addResources(jarOutputStream);
		addExternalClasses(jarOutputStream);
		jarOutputStream.close();
	}

	private static JarOutputStream createJarOutputStream(String name) throws Exception {
		Manifest manifest = new Manifest();
		manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");

		String jarFileName = GenerateCode.PALETTE_JAR_PATH.concat(name).concat(".jar");
		Path jarPath = Paths.get(jarFileName);
		Files.createDirectories(jarPath.getParent());
		JarOutputStream jarOutputStream = new JarOutputStream(new FileOutputStream(jarFileName), manifest);
		return jarOutputStream;

	}

	private static void addClasss(JarOutputStream target) throws Exception {
		File[] files = new File(GenerateCode.TARGET_PATH.concat(GenerateCode.PALETTE_PACKAGE_PATH)).listFiles();
		if (files != null && files.length != 0) {
			for (File file : files) {
				add(file, target, GenerateCode.TARGET_PATH);
			}
		}
	}

	private static void addResources(JarOutputStream target) throws Exception {
		File[] files = new File(GenerateCode.TARGET_PATH).listFiles();
		if (files != null) {
			for (File file : files) {
				if (!file.isDirectory()) {
					add(file, target, GenerateCode.TARGET_PATH);
				}
			}
		}
	}

	private static void addExternalClasses(JarOutputStream target) throws Exception {
		String dir = GenerateCode.TARGET_PATH + "temp/";
		add(new File(createTempFile(Connector.class, dir)), target, dir);
		add(new File(createTempFile(ConnectorConfig.class, dir)), target, dir);
	}

	private static void add(File source, JarOutputStream target, String dir) throws IOException {
		BufferedInputStream in = null;
		JarEntry entry = new JarEntry(source.getPath().substring(dir.length()));
		entry.setTime(source.lastModified());
		target.putNextEntry(entry);
		in = new BufferedInputStream(new FileInputStream(source));

		byte[] buffer = new byte[1024];
		while (true) {
			int count = in.read(buffer);
			if (count == -1)
				break;
			target.write(buffer, 0, count);
		}
		target.closeEntry();
		in.close();
	}

	private static String createTempFile(Class<?> clazz, String dir) throws Exception {
		String classNamePath = "/" + clazz.getName().replaceAll("\\.", "/").concat(".class");

		URL url = clazz.getResource(classNamePath);
		String file = dir + classNamePath;

		Path path = Paths.get(file);
		Files.createDirectories(path.getParent());
		if (path.toFile().exists()) {
			path.toFile().delete();
		}
		Files.createFile(path);

		InputStream is = url.openStream();
		byte[] buffer = new byte[is.available()];
		is.read(buffer);
		OutputStream outStream = new FileOutputStream(file);
		outStream.write(buffer);
		outStream.close();

		return file;
	}
}
