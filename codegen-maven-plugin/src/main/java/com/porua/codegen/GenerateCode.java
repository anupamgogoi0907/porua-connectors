package com.porua.codegen;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GenerateCode {
	public static String SRC_PATH = "./src/main/java/";
	public static String TARGET_PATH = "./target/classes/";
	public static String META_INF_DIR = "./src/main/resources/META-INF/";
	public static String PALETTE_JAR_PATH = "./palette/";

	public static String SPRING_ASSET_PACKAGE_NAME;
	public static String SPRING_ASSET_PACKAGE_PATH;
	public static String SPRING_CONNECTOR_SCHEMA_NAME = "connector-schema.xsd";
	public static String SPRING_HANDLERS_FILE = META_INF_DIR + "spring.handlers";
	public static String SPRING_SCHEMAS_FILE = META_INF_DIR + "spring.schemas";

	public static String PALETTE_PACKAGE_NAME;
	public static String PALETTE_PACKAGE_PATH;
	public static String JAXB_PACKAGE_NAME;
	public static String JAXB_PACKAGE_PATH;

	// genjava
	public static void generateJavaAssets(String pkg, List<String> connectors, ClassLoader loader) throws Exception {
		configure(pkg);
		List<Class<?>> listConnector = loadConnectorClasses(connectors, loader);
		GenerateSpringAssets.generateSpringAssets(listConnector);
		GeneratePaletteClass.generatePaletteAssets(listConnector);
		GenerateJaxbClass.generateJaxbAssets(listConnector);
	}

	// genpalette
	public static void generatePaletteAssets(String pkg, List<String> connectors, ClassLoader loader) throws Exception {
		configure(pkg);
		List<Class<?>> listConnector = loadConnectorClasses(connectors, loader);
		GeneratePaletteClass.generatePaletteAssets(listConnector);
	}

	// genxsd
	public static void generateXsdAssets(String pkg, List<String> connectors, ClassLoader loader) throws Exception {
		configure(pkg);
		GenerateComponentXsd.generateXsdAssets(loader);

	}

	// genpalettejar
	public static void generatePaletteAssetsJar(String pkg, String jarName, List<String> connectors, ClassLoader loader) throws Exception {
		configure(pkg);
		List<Class<?>> listConnector = loadConnectorClasses(connectors, loader);
		GeneratePaletteClass.generatePaletteAssets(listConnector);
		GeneratePaletteJar.generatePaletteAssetsJar(jarName);
	}

	public static void configure(String pkg) throws Exception {
		Files.createDirectories(Paths.get(META_INF_DIR));
		SPRING_ASSET_PACKAGE_NAME = pkg.concat(".").concat("spring");
		SPRING_ASSET_PACKAGE_PATH = SPRING_ASSET_PACKAGE_NAME.replaceAll("\\.", "/") + "/";

		PALETTE_PACKAGE_NAME = pkg.concat(".").concat("palette");
		PALETTE_PACKAGE_PATH = PALETTE_PACKAGE_NAME.replaceAll("\\.", "/").concat("/");

		JAXB_PACKAGE_NAME = pkg.concat(".").concat("jaxb");
		JAXB_PACKAGE_PATH = JAXB_PACKAGE_NAME.replaceAll("\\.", "/").concat("/");

	}

	private static List<Class<?>> loadConnectorClasses(List<String> connectors, ClassLoader loader) throws Exception {
		List<Class<?>> listConnector = new ArrayList<>();
		for (String className : connectors) {
			Class<?> clazz = loader.loadClass(className);
			listConnector.add(clazz);
		}
		return listConnector;
	}
}
