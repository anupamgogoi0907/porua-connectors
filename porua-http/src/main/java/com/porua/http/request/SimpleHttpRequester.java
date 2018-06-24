package com.porua.http.request;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.porua.core.processor.MessageProcessor;
import com.porua.core.tag.ConfigProperty;
import com.porua.core.tag.Connector;
import com.porua.core.tag.ConnectorConfig;
import com.porua.http.utility.HttpUtility;

@Connector(tagName = "requestor", tagNamespace = "http://www.porua.org/http", tagSchemaLocation = "http://www.porua.org/http/http.xsd", imageName = "http-requestor.png")
public class SimpleHttpRequester extends MessageProcessor {

	enum HTTP_REQUESETR_METHODS {
		GET, POST, DELETE, PUT
	}

	@ConfigProperty
	private String path;

	@ConfigProperty(enumClass = HTTP_REQUESETR_METHODS.class)
	private String method;

	@ConnectorConfig(configName = "config-ref", tagName = "requestor-config")
	private SimpleHttpRequesterConfiguration config;

	private static Logger logger = LogManager.getLogger(SimpleHttpRequester.class);

	@Override
	public void process() {
		try {
			logger.debug("Receiving request...");
			StringBuilder url = new StringBuilder();
			url.append("http//").append(config.getHost());
			url.append(config.getPort() == null ? "" : ":" + config.getPort());
			url.append(HttpUtility.sanitizePath(config.getPath()));

			super.process();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public SimpleHttpRequesterConfiguration getConfig() {
		return config;
	}

	public void setConfig(SimpleHttpRequesterConfiguration config) {
		this.config = config;
	}

}
