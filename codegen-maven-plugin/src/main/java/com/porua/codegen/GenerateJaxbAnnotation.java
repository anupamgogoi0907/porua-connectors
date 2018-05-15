package com.porua.codegen;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.porua.core.tag.Connector;
import com.porua.core.tag.ConnectorConfig;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.Loader;
import javassist.LoaderClassPath;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.StringMemberValue;

@Deprecated
public class GenerateJaxbAnnotation {

	public static Class<?>[] generateJaxbAssets(List<Class<?>> listConnector, ClassLoader loader) throws Exception {
		List<Class<?>> list = new ArrayList<>();
		for (Class<?> clazz : listConnector) {
			list.addAll(addAnnotation(clazz, createClassPool(loader)));
		}
		return list.toArray(new Class<?>[list.size()]);
	}

	private static List<Class<?>> addAnnotation(Class<?> clazz, ClassPool pool) throws Exception {
		List<Class<?>> list = new ArrayList<>();

		list.add(addAnnotationToConnector(clazz, pool));
		Class<?> configClass = addAnnotationToConfig(clazz, pool);
		if (configClass != null) {
			list.add(configClass);
		}
		return list;
	}

	private static Class<?> addAnnotationToConnector(Class<?> clazz, ClassPool pool) throws Exception {
		CtClass ctClass = pool.getCtClass(clazz.getName());
		addClassAnnotation(clazz, ctClass);
		addMethodAnnotation(clazz, ctClass);
		Class<?> c = new Loader(pool).loadClass(clazz.getName());
		return c;
	}

	private static Class<?> addAnnotationToConfig(Class<?> clazz, ClassPool pool) throws Exception {
		Field configField = Arrays.asList(clazz.getDeclaredFields()).stream().filter(f -> f.getAnnotation(ConnectorConfig.class) != null).findFirst().orElse(null);
		if (configField != null) {
			Connector con = clazz.getAnnotation(Connector.class);
			ConnectorConfig config = configField.getAnnotation(ConnectorConfig.class);

			Class<?> configClass = configField.getType();
			CtClass ctClassConfig = pool.getCtClass(configClass.getName());
			addClassAnnotation(ctClassConfig, config.tagName(), con.tagNamespace());
			addMethodAnnotation(configClass, ctClassConfig);
			Class<?> c = new Loader(pool).loadClass(configClass.getName());
			return c;
		}
		return null;
	}

	private static void addMethodAnnotation(CtClass ctClass, String methodName, String annotationName, Map<String, Object> map) throws Exception {
		ConstPool cPool = ctClass.getClassFile().getConstPool();
		CtMethod ctMethod = ctClass.getDeclaredMethod(methodName);
		AnnotationsAttribute attr = makeAnnotationAttribute(cPool, annotationName, map);
		ctMethod.getMethodInfo().addAttribute(attr);
	}

	private static void addMethodAnnotation(Class<?> clazz, CtClass ctClass) throws Exception {
		Map<String, Object> map = null;
		Map<String, Field> mapUpperField = getDeclaredFields(clazz);

		// Create annotation for Getters
		for (Method method : clazz.getDeclaredMethods()) {
			if (!method.getReturnType().getName().equals("void")) {
				map = new HashMap<>();
				String methodNameUpper = method.getName().toUpperCase();

				if (method.getReturnType().getPackage().getName().startsWith("java.lang")) {
					if (methodNameUpper.startsWith("GET")) {
						String fieldName = methodNameUpper.substring("GET".length());
						if (mapUpperField.keySet().contains(fieldName)) {
							map.put("name", mapUpperField.get(fieldName).getName());
							addMethodAnnotation(ctClass, method.getName(), XmlAttribute.class.getName(), map);
						}
					}

				} else {
					addMethodAnnotation(ctClass, method.getName(), XmlTransient.class.getName(), null);
				}
			}
		}

		// Generate Getter with name attribute and put XMLAttribute annotation.
		for (Field field : clazz.getDeclaredFields()) {
			ConnectorConfig annotation = field.getAnnotation(ConnectorConfig.class);
			if (annotation != null) {
				String name = annotation.configName();
				map.put("name", name);
				String methodName = "get" + name.replaceAll("[^a-zA-Z0-9]", "");
				CtMethod methodNew = CtNewMethod.make("public String " + methodName + " () { return \"\"; }", ctClass);
				ctClass.addMethod(methodNew);
				addMethodAnnotation(ctClass, methodName, XmlAttribute.class.getName(), map);
				break;
			}
		}
	}

	private static void addClassAnnotation(Class<?> clazz, CtClass ctClass) throws Exception {
		Connector annotation = clazz.getAnnotation(Connector.class);
		Map<String, Object> map = new HashMap<>();
		map.put("name", annotation.tagName());
		map.put("namespace", annotation.tagNamespace());
		addClassAnnotation(ctClass, XmlRootElement.class.getName(), map);
	}

	private static void addClassAnnotation(CtClass ctClass, String name, String namespace) throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("name", name);
		map.put("namespace", namespace);
		addClassAnnotation(ctClass, XmlRootElement.class.getName(), map);
	}

	private static void addClassAnnotation(CtClass ctClass, String annotationName, Map<String, Object> map) throws Exception {
		ConstPool cPool = ctClass.getClassFile().getConstPool();
		AnnotationsAttribute attr = makeAnnotationAttribute(cPool, annotationName, map);
		ctClass.getClassFile().addAttribute(attr);
	}

	private static AnnotationsAttribute makeAnnotationAttribute(ConstPool cPool, String annotationName, Map<String, Object> map) throws Exception {
		AnnotationsAttribute attr = new AnnotationsAttribute(cPool, AnnotationsAttribute.visibleTag);
		Annotation annot = new Annotation(annotationName, cPool);
		if (map != null) {
			for (String key : map.keySet()) {
				Object value = map.get(key);
				if (value instanceof String) {
					annot.addMemberValue(key, new StringMemberValue((String) value, cPool));
				}
			}
		}
		attr.addAnnotation(annot);
		return attr;
	}

	private static ClassPool createClassPool(ClassLoader loader) throws Exception {
		ClassPool pool = ClassPool.getDefault();
		pool.appendClassPath(new LoaderClassPath(loader));
		return pool;
	}

	private static Map<String, Field> getDeclaredFields(Class<?> clazz) throws Exception {
		Map<String, Field> mapUpperOriginal = new HashMap<>();
		for (Field field : clazz.getDeclaredFields()) {
			mapUpperOriginal.put(field.getName().toUpperCase(), field);
		}
		return mapUpperOriginal;
	}

}
