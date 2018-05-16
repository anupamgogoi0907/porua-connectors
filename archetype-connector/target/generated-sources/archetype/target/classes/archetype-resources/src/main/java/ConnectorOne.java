#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import ${groupId}.core.processor.MessageProcessor;
import ${groupId}.core.tag.Connector;
import ${groupId}.core.tag.ConnectorConfig;

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
