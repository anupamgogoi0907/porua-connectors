package com.porua.codegen;

import java.io.File;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.impl.inst2xsd.Inst2Xsd;
import org.apache.xmlbeans.impl.inst2xsd.Inst2XsdOptions;
import org.apache.xmlbeans.impl.xb.xsdschema.SchemaDocument;

public class GenerateComponentXsd {

	public static void generateXsdAssets(ClassLoader loader) throws Exception {
		String path = GenerateCode.TARGET_PATH.concat(GenerateCode.JAXB_PACKAGE_PATH);
		File[] files = new File(path).listFiles();
		List<Class<?>> listClass = new ArrayList<>();
		for (File file : files) {
			String fileName = file.getPath().substring(GenerateCode.TARGET_PATH.length()).replaceAll("/", "\\.");
			fileName = fileName.substring(0, fileName.length() - ".class".length());
			Class<?> clazz = loader.loadClass(fileName);
			listClass.add(clazz);
		}
		xsdGen(listClass.toArray(new Class<?>[listClass.size()]));

	}

	public static void xsdGen(Class<?>... classes) throws Exception {
		JAXBContext context = JAXBContext.newInstance(classes);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		List<String> listXml = new ArrayList<>();
		for (Class<?> clazz : classes) {
			StringWriter writer = new StringWriter();
			m.marshal(clazz.newInstance(), writer);
			String xml = writer.toString();
			listXml.add(xml);
		}
		parseXsd(listXml);
	}

	private static void parseXsd(List<String> listXml) throws Exception {

		XmlObject[] xmlInsances = new XmlObject[listXml.size()];
		for (int i = 0; i < listXml.size(); i++) {
			xmlInsances[i] = XmlObject.Factory.parse(listXml.get(i));
		}

		Inst2XsdOptions inst2XsdOptions = new Inst2XsdOptions();
		inst2XsdOptions.setDesign(Inst2XsdOptions.DESIGN_VENETIAN_BLIND);
		SchemaDocument[] docArr = Inst2Xsd.inst2xsd(xmlInsances, inst2XsdOptions);

		if (docArr != null) {
			String filePath = GenerateCode.SRC_PATH.concat(GenerateCode.SPRING_ASSET_PACKAGE_PATH).concat(GenerateCode.SPRING_CONNECTOR_SCHEMA_NAME);
			Files.createDirectories(Paths.get(filePath).getParent());
			File file = new File(filePath);
			docArr[0].save(file);
		}

	}
}
