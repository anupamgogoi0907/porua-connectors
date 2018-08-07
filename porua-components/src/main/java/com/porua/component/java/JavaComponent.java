package com.porua.component.java;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.porua.component.logger.PoruaLogger;
import com.porua.core.context.PoruaClassLoader;
import com.porua.core.processor.MessageProcessor;
import com.porua.core.tag.ConfigProperty;
import com.porua.core.tag.Connector;

@Connector(tagName = "java-component", tagNamespace = "http://www.porua.org/components", tagSchemaLocation = "http://www.porua.org/components/components.xsd", imageName = "core-java-component.png")
public class JavaComponent extends MessageProcessor {

	@ConfigProperty
	private String className;

	private Logger logger = LogManager.getLogger(PoruaLogger.class);

	@Override
	public void process() {
		try {
			logger.debug("Executing JavaComponent class: " + className);
			PoruaClassLoader loader = super.springContext.getBean(PoruaClassLoader.class);
			Class<?> clasz = loader.loadClass(className);
			Object obj = clasz.newInstance();
			logger.info(obj);
			super.process();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

}
