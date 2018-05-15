package com.porua.codegen;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.lang.model.element.Modifier;

import com.porua.core.tag.Connector;
import com.porua.core.tag.ConnectorConfig;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

public class GeneratePaletteClass {

	public static void generatePaletteAssets(List<Class<?>> listConnector) {
		listConnector.forEach(clazz -> {
			try {
				createConnectorPalette(clazz);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	private static void createConnectorPalette(Class<?> clazz) throws Exception {
		Connector connectAnnot = clazz.getAnnotation(Connector.class);

		// Class Annotation
		AnnotationSpec.Builder as = AnnotationSpec.builder(Connector.class);
		as.addMember("tagName", CodeBlock.of("$S", connectAnnot.tagName()));
		as.addMember("tagNamespace", CodeBlock.of("$S", connectAnnot.tagNamespace()));
		as.addMember("tagSchemaLocation", CodeBlock.of("$S", connectAnnot.tagSchemaLocation()));
		as.addMember("imageName", CodeBlock.of("$S", connectAnnot.imageName()));

		// Fields
		List<FieldSpec> listFields = new ArrayList<>();
		for (Field field : clazz.getDeclaredFields()) {
			FieldSpec fs = null;
			ConnectorConfig configAnnot = field.getAnnotation(ConnectorConfig.class);
			if (configAnnot != null) {
				AnnotationSpec.Builder asConfig = AnnotationSpec.builder(ConnectorConfig.class);
				asConfig.addMember("configName", CodeBlock.of("$S", configAnnot.configName()));
				asConfig.addMember("tagName", CodeBlock.of("$S", configAnnot.tagName()));

				TypeName tn = createConnectorConfigPalette(field.getType());
				fs = FieldSpec.builder(tn, field.getName()).addAnnotation(asConfig.build()).build();
			} else if (field.getType().isPrimitive() || field.getType().getPackage().getName().startsWith("java.lang")) {
				fs = FieldSpec.builder(field.getType(), field.getName()).build();
			}
			if (fs != null) {
				listFields.add(fs);
			}
		}

		TypeSpec typeSpec = TypeSpec.classBuilder(clazz.getSimpleName() + "Palette").addModifiers(Modifier.PUBLIC).addAnnotation(as.build()).addFields(listFields).build();
		createFile(typeSpec);
	}

	private static TypeName createConnectorConfigPalette(Class<?> clazz) throws Exception {
		List<FieldSpec> listField = new ArrayList<>();
		Arrays.asList(clazz.getDeclaredFields()).forEach(f -> {
			FieldSpec fs = FieldSpec.builder(f.getType(), f.getName()).build();
			listField.add(fs);
		});

		String className = clazz.getSimpleName() + "Palette";
		TypeSpec typeSpec = TypeSpec.classBuilder(className).addModifiers(Modifier.PUBLIC).addFields(listField).build();
		createFile(typeSpec);
		ClassName cn = ClassName.get(GenerateCode.PALETTE_PACKAGE_NAME, className);
		return cn.box();
	}

	private static void createFile(TypeSpec className) throws Exception {
		JavaFile javaFile = JavaFile.builder(GenerateCode.PALETTE_PACKAGE_NAME, className).build();
		File file = new File(GenerateCode.SRC_PATH);
		javaFile.writeTo(System.out);
		javaFile.writeTo(file);
	}
}
