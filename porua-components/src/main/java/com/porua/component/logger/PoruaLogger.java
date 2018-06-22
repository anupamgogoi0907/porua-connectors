package com.porua.component.logger;

import java.io.BufferedInputStream;
import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.porua.core.processor.MessageProcessor;
import com.porua.core.tag.Connector;
import com.porua.core.utility.PoruaUtility;

@Connector(tagName = "porua-logger", tagNamespace = "http://www.porua.org/components", tagSchemaLocation = "http://www.porua.org/components/components.xsd", imageName = "core-porua-logger.png")
public class PoruaLogger extends MessageProcessor {

	private Logger logger = LogManager.getLogger(PoruaLogger.class);

	@Override
	public void process() {
		try {
			BufferedInputStream bis = null;
			if (super.poruaContext.getPayload() != null) {
				if (super.poruaContext.getPayload() instanceof InputStream) {
					bis = new BufferedInputStream((InputStream) super.poruaContext.getPayload());
					bis.mark(bis.available());
					String payload = PoruaUtility.convertStreamToString(bis);
					logger.info("Porua message payload\n\n" + payload);
					bis.reset();
					super.poruaContext.setPayload(bis);
				} else {
					logger.info("Porua message payload\n\n" + super.poruaContext.getPayload().toString());
				}
			} else {
				logger.info("Porua message payload\\n\\n" + "");
			}
			super.process();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}
}
