package com.custom.connector;

import com.porua.core.processor.MessageProcessor;
import com.porua.core.tag.Connector;
import com.porua.core.tag.ConnectorConfig;

@Connector(tagName = "con-one", tagNamespace = "http://www.connector.com/custom", tagSchemaLocation = "http://www.connector.com/custom/custom.xsd", imageName = "")
public class ConnectorOne extends MessageProcessor {
	private String name;

	@ConnectorConfig(configName="config-ref",tagName="con-one-fonfig")
	private ConnectorOneConfig config;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
