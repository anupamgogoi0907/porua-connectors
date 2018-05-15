package com.porua.db.namespace;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class PoruaDatabaseNamespaceHandler extends NamespaceHandlerSupport {

	@Override
	public void init() {
		registerBeanDefinitionParser("db-connector", new PoruaDatabaseConnectorDefinitionParser());
		registerBeanDefinitionParser("db-config", new PoruaDatabaseConfigurationDefinitionParser());
	}

}
