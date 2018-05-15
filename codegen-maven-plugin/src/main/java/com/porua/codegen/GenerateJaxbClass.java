package com.porua.codegen;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Modifier;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.porua.core.tag.Connector;
import com.porua.core.tag.ConnectorConfig;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

public class GenerateJaxbClass {

	public static List<String> generateJaxbAssets(List<Class<?>> listConnector) throws Exception {
		List<String> list = new ArrayList<>();
		for (Class<?> clazz : listConnector) {
			List<String> listTemp = createConnectorJaxbClass(clazz, new ArrayList<>());
			list.addAll(listTemp);
		}
		return list;
	}

	private static List<String> createConnectorJaxbClass(Class<?> clazz, List<String> listClassName) throws Exception {
		Connector connectAnnot = clazz.getAnnotation(Connector.class);

		AnnotationSpec.Builder as = AnnotationSpec.builder(XmlRootElement.class);
		as.addMember("name", CodeBlock.of("$S", connectAnnot.tagName()));
		as.addMember("namespace", CodeBlock.of("$S", connectAnnot.tagNamespace()));

		List<FieldSpec> listFieldSpec = new ArrayList<>();
		for (Field field : clazz.getDeclaredFields()) {
			FieldSpec.Builder fs = null;
			AnnotationSpec.Builder asAttribute = null;

			if (field.getType().isPrimitive() || field.getType().getPackage().getName().startsWith("java.lang")) {
				asAttribute = AnnotationSpec.builder(XmlAttribute.class);
				asAttribute.addMember("name", CodeBlock.of("$S", field.getName()));
				fs = FieldSpec.builder(field.getType(), field.getName(), Modifier.PRIVATE).addAnnotation(asAttribute.build());
				getDefaultValue(fs, field.getType().getName());
			} else {
				ConnectorConfig configAnnot = field.getAnnotation(ConnectorConfig.class);
				if (configAnnot != null) {
					asAttribute = AnnotationSpec.builder(XmlAttribute.class);
					asAttribute.addMember("name", CodeBlock.of("$S", configAnnot.configName()));
					fs = FieldSpec.builder(String.class, configAnnot.configName().replaceAll("[^a-zA-Z0-9]", "")).addAnnotation(asAttribute.build());
					getDefaultValue(fs, String.class.getName());
					createConnectorConfigJaxbClass(field.getType(), configAnnot.tagName(), connectAnnot.tagNamespace(), listClassName);
				}
			}
			if (fs != null) {
				listFieldSpec.add(fs.build());
			}
		}
		String className = clazz.getSimpleName() + "Jaxb";
		TypeSpec typeSpec = TypeSpec.classBuilder(className).addModifiers(Modifier.PUBLIC).addAnnotation(as.build()).addFields(listFieldSpec).build();
		createFile(typeSpec);

		ClassName cn = ClassName.get(GenerateCode.JAXB_PACKAGE_NAME, className);
		listClassName.add(cn.reflectionName());
		return listClassName;
	}

	private static void createConnectorConfigJaxbClass(Class<?> clazz, String name, String namespace, List<String> listClassName) throws Exception {
		List<FieldSpec> listFieldSpec = new ArrayList<>();
		AnnotationSpec.Builder asClass = AnnotationSpec.builder(XmlRootElement.class);
		asClass.addMember("name", CodeBlock.of("$S", name));
		asClass.addMember("namespace", CodeBlock.of("$S", namespace));

		for (Field field : clazz.getDeclaredFields()) {
			AnnotationSpec.Builder asAttribute = AnnotationSpec.builder(XmlAttribute.class);
			asAttribute.addMember("name", CodeBlock.of("$S", field.getName()));
			FieldSpec.Builder fs = FieldSpec.builder(field.getType(), field.getName()).addAnnotation(asAttribute.build());
			getDefaultValue(fs, field.getType().getName());
			listFieldSpec.add(fs.build());
		}
		String className = clazz.getSimpleName() + "Jaxb";
		TypeSpec typeSpec = TypeSpec.classBuilder(className).addModifiers(Modifier.PUBLIC).addFields(listFieldSpec).addAnnotation(asClass.build()).build();
		createFile(typeSpec);

		ClassName cn = ClassName.get(GenerateCode.JAXB_PACKAGE_NAME, className);
		listClassName.add(cn.simpleName());
	}

	private static void createFile(TypeSpec className) throws Exception {
		JavaFile javaFile = JavaFile.builder(GenerateCode.JAXB_PACKAGE_NAME, className).build();
		File file = new File(GenerateCode.SRC_PATH);
		javaFile.writeTo(System.out);
		javaFile.writeTo(file);
	}

	private static FieldSpec.Builder getDefaultValue(FieldSpec.Builder fs, String type) throws Exception {
		if (type.contains("int") || type.contains("Integer")) {
			fs.initializer(CodeBlock.of("$L", 1));
			return fs;
		} else if (type.contains("String")) {
			fs.initializer(CodeBlock.of("$S", "somevalue"));
			return fs;
		} else if (type.contains("float") || type.equals("Float")) {
			fs.initializer(CodeBlock.of("$L", 1));
			return fs;
		} else {
			return fs;
		}
	}

}
