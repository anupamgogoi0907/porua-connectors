package com.porua.http.request;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.porua.core.processor.MessageProcessor;
import com.porua.core.tag.ConfigProperty;
import com.porua.core.tag.Connector;
import com.porua.core.tag.ConnectorConfig;

@Connector(tagName = "requestor", tagNamespace = "http://www.porua.org/http", tagSchemaLocation = "http://www.porua.org/http/http.xsd", imageName = "http-requestor.png")
public class SimpleHttpRequester extends MessageProcessor {

	@ConfigProperty
	private String path;
	
	@ConnectorConfig(configName = "config-ref", tagName = "requestor-config")
	private SimpleHttpRequesterConfiguration config;

	private static Logger logger = LogManager.getLogger(SimpleHttpRequester.class);

	public SimpleHttpRequester() {
	}

	@Override
	public void process() {
		logger.debug("Receiving request...");
		super.process();
	}

	public SimpleHttpRequesterConfiguration getConfig() {
		return config;
	}

	public void setConfig(SimpleHttpRequesterConfiguration config) {
		this.config = config;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
