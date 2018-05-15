package com.porua.core.processor.component;

import java.io.InputStream;

import com.porua.core.PoruaConstants;
import com.porua.core.context.PoruaClassLoader;
import com.porua.core.processor.MessageProcessor;
import com.porua.core.tag.Connector;
import com.porua.core.utility.PoruaUtility;

@Connector(tagName="set-payload",tagNamespace="http://www.porua.org/core",tagSchemaLocation="http://www.porua.org/core/core.xsd",imageName="core-payload-setter.png")
public class PayloadSetter extends MessageProcessor {
	private Object payload;
	private String file;

	@Override
	public void process() {
		// Processing the file input gets high priority when both properties are filled.
		if (file != null && !file.equals("")) {
			PoruaClassLoader loader = super.springContext.getBean(PoruaClassLoader.class);
			InputStream payloadStream = loader.getResourceAsStream(file);
			super.poruaContext.setPayload(payloadStream);
			payload = null;
		}
		if (payload != null) {
			Class<?> valueType = PoruaUtility.findDataType((String) payload);
			if (valueType.isAssignableFrom(String.class)) {
				payload = parsePayload((String) payload);
			} else {
				payload = PoruaUtility.convertData((String) payload);
			}
			super.poruaContext.setPayload(payload);
		}
		super.process();
	}

	private String parsePayload(String payload) {
		if (payload.contains(PoruaConstants.PORUA_CONTEXT_ATTRIBUTES)
				|| payload.contains(PoruaConstants.PORUA_CONTEXT_VARIABLES)
				|| payload.contains(PoruaConstants.PORUA_PAYLOAD)) {
			payload = (String) super.parseExpression(new StringBuilder(payload));
		} else {
			payload = (String) payload;
		}
		return payload;
	}

	public Object getPayload() {
		return payload;
	}

	public void setPayload(Object payload) {
		this.payload = payload;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}
}
