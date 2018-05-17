package porua.plugin.utility;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.URIUtil;
import org.eclipse.swt.graphics.ImageData;
import org.osgi.framework.Bundle;
import org.w3c.dom.Document;

import porua.plugin.Activator;
import porua.plugin.pojos.TagData;

@SuppressWarnings({ "deprecation" })
public class PluginUtility {
	private static int nsCount = 0;

	public static ImageData getImageByTag(String tagComponent) {
		ImageData imgData = null;
		try {
			TagData pojo = getTagDataByTagName(tagComponent);
			imgData = new ImageData(PluginUtility.getClassLoader().getResourceAsStream(pojo.getImageName()));
			imgData.scaledTo(100, 50);
		} catch (Exception e) {
			imgData = Activator.getImageDescriptor(PluginConstants.ICONS_PATH.concat("default.png")).getImageData();
		}
		return imgData;
	}

	public static ImageData getImageByTag(TagData tagData) {
		ImageData imgData = null;
		try {
			imgData = new ImageData(PluginUtility.getClassLoader().getResourceAsStream(tagData.getImageName()));
			imgData.scaledTo(100, 50);
		} catch (Exception e) {
			imgData = Activator.getImageDescriptor(PluginConstants.ICONS_PATH.concat("default.png")).getImageData();
		}
		return imgData;
	}

	public static Document xmlToDom(String xml) {
		Document xmlDoc = null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			xmlDoc = dBuilder.parse(new ByteArrayInputStream(xml.getBytes()));
			xmlDoc.getDocumentElement().normalize();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return xmlDoc;

	}

	public static String domToXml(Document doc) {
		try {
			DOMSource domSource = new DOMSource(doc);
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);

			TransformerFactory tf = TransformerFactory.newInstance();
			tf.setAttribute("indent-number", new Integer(2));
			Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(domSource, result);
			return writer.toString();
		} catch (TransformerException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static File readBundleResource(String filePath) {
		File file = null;
		try {
			Bundle bundle = Platform.getBundle(Activator.PLUGIN_ID);
			URL fileURL = bundle.getEntry(filePath);
			fileURL=FileLocator.toFileURL(fileURL);
			file = URIUtil.toFile(URIUtil.toURI(fileURL));
			System.out.println(file.getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;
	}

	public static Properties readPropertyFile() {
		Properties props = new Properties();
		try {
			FileInputStream is = new FileInputStream(readBundleResource("resources/config/init.properties"));
			props.load(is);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return props;
	}

	public static URLClassLoader getClassLoader() {
		URLClassLoader loader = null;
		try {
			File[] jars = new File(PluginConstants.LIB_PATH).listFiles();
			List<URL> urls = new ArrayList<>();
			for (File jar : jars) {
				if (jar.getName().endsWith(".jar")) {
					urls.add(jar.toURI().toURL());
				}
			}
			loader = URLClassLoader.newInstance(urls.toArray(new URL[urls.size()]));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loader;
	}

	public static void loadTags() throws Exception {
		URLClassLoader loader = getClassLoader();
		for (URL url : loader.getURLs()) {
			JarFile jarFile = new JarFile(new File(url.toURI()));
			Enumeration<JarEntry> entries = jarFile.entries();
			while (entries.hasMoreElements()) {
				JarEntry en = entries.nextElement();
				if (en.getName().endsWith(".class")) {
					String className = en.getName().substring(0, en.getName().length() - 6);
					className = className.replace('/', '.');
					Class<?> clazz = loader.loadClass(className);
					TagData tagData = makeTagData(clazz);
					if (tagData != null) {
						mapNamespaceToTag(tagData);
					}
				}
			}
			jarFile.close();
		}
		generatePrefixForTag();
		System.out.println();

	}

	public static TagData makeTagData(Class<?> clazz) throws Exception {
		Map<String, Object> mapConnector = checkForConnector(clazz);
		if (mapConnector != null) {
			String connTagName = (String) mapConnector.get("tagName");
			String connTagNamespace = (String) mapConnector.get("tagNamespace");
			String connTagSchemaLocation = (String) mapConnector.get("tagSchemaLocation");
			String connImageName = (String) mapConnector.get("imageName");
			TagData tagData = new TagData(connTagName, connTagNamespace, connTagSchemaLocation, connImageName);

			// Fields
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				Map<String, Object> mapConfig = checkForConnectorConfig(field);
				if (mapConfig == null) {
					tagData.getProps().add(field.getName());
				} else {
					String configTagName = (String) mapConfig.get("tagName");
					String configName = (String) mapConfig.get("configName");
					TagData tagConfigData = new TagData(configTagName, connTagNamespace, connTagSchemaLocation, "");
					for (Field f : field.getType().getDeclaredFields()) {
						tagConfigData.getProps().add(f.getName());
					}
					tagData.getConfig().put(configName, tagConfigData);
				}
			}
			return tagData;
		} else {
			return null;
		}
	}

	public static void mapNamespaceToTag(TagData tagData) {
		List<TagData> listTag = PluginConstants.mapNsTags.get(tagData.getTagNamespace());
		listTag = (listTag == null) ? new ArrayList<>() : listTag;
		listTag.add(tagData);
		PluginConstants.mapNsTags.put(tagData.getTagNamespace(), listTag);

		if (tagData.getConfig() != null && tagData.getConfig().size() != 0) {
			mapNamespaceToTag((TagData) tagData.getConfig().values().toArray()[0]);
		}
	}

	public static void generatePrefixForTag() {
		for (String ns : PluginConstants.mapNsTags.keySet()) {
			String prefix = "ns" + (++nsCount);
			List<TagData> list = PluginConstants.mapNsTags.get(ns);
			list.stream().forEach(t -> t.setTagNamespacePrefix(prefix));

			PluginConstants.mapPrefixTags.put(prefix, list);
		}
	}

	public static TagData getTagDataByTagName(String tagComponent) {
		String[] arr = tagComponent.split(":"); // prefix:component
		List<TagData> listTag = PluginConstants.mapPrefixTags.get(arr[0]);
		TagData tagData = listTag.stream().filter(t -> t.getTag().equals(arr[1])).findFirst().get();
		return tagData;
	}

	public static List<TagData> getAllTags() {
		List<TagData> listAllTags = new ArrayList<>();
		for (String ns : PluginConstants.mapNsTags.keySet()) {
			List<TagData> listTag = PluginConstants.mapNsTags.get(ns);
			listAllTags.addAll(listTag);
		}
		return listAllTags;
	}

	/**
	 * I am not sure why I'm doing this. Inside Eclipse I could not use
	 * clazz.getAnnotation(Connector.class)
	 * 
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> checkForConnector(Class<?> clazz) throws Exception {
		Map<String, Object> map = null;
		Annotation[] arr = clazz.getAnnotations();
		if (arr.length != 0) {
			Annotation annot = arr[0];
			Class<? extends Annotation> type = annot.annotationType();
			if (type.getName().equals("com.porua.core.tag.Connector")) {
				map = new HashMap<>();
				String tagName = (String) type.getMethod("tagName").invoke(annot);
				String tagNamespace = (String) type.getMethod("tagNamespace").invoke(annot);
				String tagSchemaLocation = (String) type.getMethod("tagSchemaLocation").invoke(annot);
				String imageName = (String) type.getMethod("imageName").invoke(annot);

				map.put("tagName", tagName);
				map.put("tagNamespace", tagNamespace);
				map.put("tagSchemaLocation", tagSchemaLocation);
				map.put("imageName", imageName);
				return map;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public static Map<String, Object> checkForConnectorConfig(Field field) throws Exception {
		Map<String, Object> map = null;
		Annotation[] arr = field.getAnnotations();
		if (arr.length != 0) {
			Annotation annot = arr[0];
			Class<? extends Annotation> type = annot.annotationType();
			if (type.getName().equals("com.porua.core.tag.ConnectorConfig")) {
				map = new HashMap<>();
				String tagName = (String) type.getMethod("tagName").invoke(annot);
				String configName = (String) type.getMethod("configName").invoke(annot);

				map.put("tagName", tagName);
				map.put("configName", configName);
				return map;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
}
