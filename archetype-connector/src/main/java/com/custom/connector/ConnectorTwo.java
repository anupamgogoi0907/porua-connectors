package com.custom.connector;

import com.porua.core.processor.MessageProcessor;
import com.porua.core.tag.Connector;
import com.porua.core.tag.ConnectorConfig;

@Connector(tagName = "con-two", tagNamespace = "http://www.connector.com/custom", tagSchemaLocation = "http://www.connector.com/custom/custom.xsd", imageName = "")
public class ConnectorTwo extends MessageProcessor {
	private String name;

	@ConnectorConfig(configName = "config-ref", tagName = "con-two-config")
	private ConnectorTwoConfig config;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
