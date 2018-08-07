package com.porua.component.java;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;

import com.porua.core.context.PoruaClassLoader;
import com.porua.core.context.PoruaContext;
import com.porua.core.processor.MessageProcessor;
import com.porua.core.tag.ConfigProperty;
import com.porua.core.tag.Connector;

@Connector(tagName = "java-component", tagNamespace = "http://www.porua.org/components", tagSchemaLocation = "http://www.porua.org/components/components.xsd", imageName = "core-java-component.png")
public class JavaComponent extends MessageProcessor {

	@ConfigProperty
	private String className;

	private Logger logger = LogManager.getLogger(JavaComponent.class);

	@Override
	public void process() {
		try {
			logger.debug("Executing JavaComponent class: " + className);
			PoruaClassLoader loader = super.springContext.getBean(PoruaClassLoader.class);
			Class<?> clazz = loader.loadClass(className);
			if (Pluggable.class.isAssignableFrom(clazz)) {
				if (super.poruaContext.getPayload() instanceof InputStream) {
					BufferedInputStream bis = new BufferedInputStream((InputStream) super.poruaContext.getPayload());
					bis.mark(bis.available());
					processComponentLogic(clazz);
					bis.reset();
				} else {
					processComponentLogic(clazz);
				}
				super.process();
			} else {
				throw new Exception("Java Component must implement Pluggable interface.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

	}

	/**
	 * Process the custom component.
	 * 
	 * @param clazz
	 */
	private void processComponentLogic(Class<?> clazz) {
		try {
			logger.info("Processing " + className);
			Object obj = clazz.newInstance();
			clazz.getDeclaredMethod("onCall", ApplicationContext.class, PoruaContext.class, Object.class).invoke(obj,
					super.springContext, super.poruaContext, clonePayload());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

	}

	/**
	 * We have to clone the Payload if it's a InputStream. Otherwise if the custom
	 * component closes the stream exception will be thrown.
	 * 
	 * @return
	 */
	private Object clonePayload() {
		try {
			logger.info("Cloning payload... ");
			if (super.poruaContext.getPayload() instanceof InputStream) {
				InputStream is = (InputStream) super.poruaContext.getPayload();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();

				byte[] buffer = new byte[1024];
				int len;
				while ((len = is.read(buffer)) > -1) {
					baos.write(buffer, 0, len);
				}
				baos.flush();

				InputStream isClone = new ByteArrayInputStream(baos.toByteArray());
				return isClone;
			} else {
				return super.poruaContext.getPayload();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return null;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

}
