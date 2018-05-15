package com.porua.core.processor.component;

import java.lang.reflect.Method;
import java.util.Map;

import com.porua.core.context.PoruaClassLoader;
import com.porua.core.processor.MessageProcessor;
import com.porua.core.tag.Connector;

@SuppressWarnings("unchecked")
@Connector(tagName="java-component",tagNamespace="http://www.porua.org/core",tagSchemaLocation="http://www.porua.org/core/core.xsd",imageName="core-java-component.png")
public class JavaComponent extends MessageProcessor {

	private String className;

	@Override
	public void process() {
		try {
			PoruaClassLoader loader = super.springContext.getBean(PoruaClassLoader.class);
			Class<?> clasz = loader.loadClass(className);
			Object obj = clasz.newInstance();
			Method method = clasz.getMethod("variable", new Class<?>[] {});
			Map<String, Object> map = (Map<String, Object>) method.invoke(obj, new Object[] {});
			updateContextVaraibles(map);
			super.process();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	void updateContextVaraibles(Map<String, Object> map) {
		for (String key : map.keySet()) {
			if (!super.getPoruaContext().getMapVariable().keySet().contains(key)) {
				super.getPoruaContext().getMapVariable().put(key, map.get(key));
			}
		}
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

}
