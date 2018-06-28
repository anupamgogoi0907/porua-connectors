package com.porua.component.payload;

import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.porua.core.context.PoruaClassLoader;
import com.porua.core.pel.PoruaExpressionEvaluator;
import com.porua.core.processor.MessageProcessor;
import com.porua.core.tag.ConfigProperty;
import com.porua.core.tag.Connector;

@Connector(tagName = "set-payload", tagNamespace = "http://www.porua.org/components", tagSchemaLocation = "http://www.porua.org/components/components.xsd", imageName = "core-payload-setter.png")
public class PayloadSetter extends MessageProcessor {

	private static Logger logger = LogManager.getLogger(PayloadSetter.class);

	@ConfigProperty
	private String payload;

	@ConfigProperty
	private String file;

	@Override
	public void process() {
		try {
			logger.debug("Setting payload...");
			PoruaExpressionEvaluator evaluator = super.springContext.getBean(PoruaExpressionEvaluator.class);

			// Processing the file input gets high priority when both properties are filled.
			if (file != null && !file.equals("")) {
				PoruaClassLoader loader = super.springContext.getBean(PoruaClassLoader.class);
				InputStream payloadStream = loader.getResourceAsStream(file);
				super.poruaContext.setPayload(payloadStream);
				payload = null;
			}
			if (payload != null) {
				Object res = evaluator.parseValueExpression(payload, poruaContext);
				super.poruaContext.setPayload(res);
			}
			super.process();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

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
