package com.porua.component.json;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.porua.core.processor.MessageProcessor;
import com.porua.core.tag.Connector;
import com.porua.core.utility.PoruaUtility;

@Connector(tagName = "java-to-json", tagNamespace = "http://www.porua.org/components", tagSchemaLocation = "http://www.porua.org/components/components.xsd", imageName = "core-java-to-json.png")
public class JavaToJson extends MessageProcessor {

	private static Logger logger = LogManager.getLogger(JavaToJson.class);

	@Override
	public void process() {
		try {
			logger.debug("Transforming Java to Json...");
			if (!super.springContext.containsBean("objectMapper")) {
				addObjectMapperToSpringContext();
			}
			ObjectMapper mapper = (ObjectMapper) super.springContext.getBean("objectMapper");
			String json = mapper.writeValueAsString(super.poruaContext.getPayload());
			logger.debug(json);
			super.poruaContext.setPayload(json);
			super.process();
		} catch (Exception e) {
			logger.error(e.getMessage());
			PoruaUtility.onError(poruaContext, e.getMessage());
		}
	}

	void addObjectMapperToSpringContext() {
		logger.debug("Adding ObjectMapper to Spring Context...");
		PoruaUtility.addBeanToSpringContext("objectMapper", ObjectMapper.class,
				(FileSystemXmlApplicationContext) super.springContext, new Object[] {});
	}
}
