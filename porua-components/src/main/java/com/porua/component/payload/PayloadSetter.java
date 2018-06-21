package com.porua.component.payload;

import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.porua.component.variable.VariableSetter;
import com.porua.core.PoruaConstants;
import com.porua.core.context.PoruaClassLoader;
import com.porua.core.processor.MessageProcessor;
import com.porua.core.tag.ConfigProperty;
import com.porua.core.tag.Connector;

@Connector(tagName = "set-payload", tagNamespace = "http://www.porua.org/components", tagSchemaLocation = "http://www.porua.org/components/components.xsd", imageName = "core-payload-setter.png")
public class PayloadSetter extends MessageProcessor {

	@ConfigProperty
	private String payload;

	@ConfigProperty
	private String file;

	private static Logger logger = LogManager.getLogger(VariableSetter.class);

	@Override
	public void process() {
		logger.info("Setting payload: " + payload);
		// Processing the file input gets high priority when both properties are filled.
		if (file != null && !file.equals("")) {
			PoruaClassLoader loader = super.springContext.getBean(PoruaClassLoader.class);
			InputStream payloadStream = loader.getResourceAsStream(file);
			super.poruaContext.setPayload(payloadStream);
			payload = null;
		}
		if (payload != null) {
			payload = parsePayload(payload);
			super.poruaContext.setPayload(payload);
		}
		super.process();
	}

	private String parsePayload(String payload) {
		if (payload.contains(PoruaConstants.PORUA_CONTEXT_ATTRIBUTES) || payload.contains(PoruaConstants.PORUA_CONTEXT_VARIABLES) || payload.contains(PoruaConstants.PORUA_PAYLOAD)) {
			payload = (String) super.parseExpression(new StringBuilder(payload));
		} else {
			payload = (String) payload;
		}
		return payload;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

}
