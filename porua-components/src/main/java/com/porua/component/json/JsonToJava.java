package com.porua.component.json;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.porua.core.processor.MessageProcessor;
import com.porua.core.tag.Connector;

/**
 * Spring Bean. Populated by Spring.
 */
@Connector(tagName = "json-to-java", tagNamespace = "http://www.porua.org/components", tagSchemaLocation = "http://www.porua.org/components/components.xsd", imageName = "core-json-to-java.png")
public class JsonToJava extends MessageProcessor {

	private String mimeType;

	public JsonToJava() {
	}

	@Override
	public void process() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			Map<?, ?> map = null;
			if (super.poruaContext.getPayload() instanceof InputStream) {
				InputStream payload = (InputStream) super.poruaContext.getPayload();
				if (payload.available() != 0) {
					map = mapper.readValue(payload, HashMap.class);
				}
			} else if (super.poruaContext.getPayload() instanceof String) {
				String payload = (String) super.poruaContext.getPayload();
				if (payload != null && !payload.equals("")) {
					map = mapper.readValue(payload, HashMap.class);
				}
			} else {
				throw new Exception("Wrong input format.");
			}
			super.getPoruaContext().setPayload(map);
			super.process();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
}
