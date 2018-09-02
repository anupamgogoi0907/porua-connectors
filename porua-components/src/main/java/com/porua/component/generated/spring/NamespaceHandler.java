package com.porua.component.generated.spring;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class NamespaceHandler extends NamespaceHandlerSupport {
	public void init() {
		registerBeanDefinitionParser("file-connector", new PoruaFileConnectorDefinitionParser());
		registerBeanDefinitionParser("java-component", new JavaComponentDefinitionParser());
		registerBeanDefinitionParser("json-to-java", new JsonToJavaDefinitionParser());
		registerBeanDefinitionParser("json-to-xml", new JsonToXmlDefinitionParser());
		registerBeanDefinitionParser("set-payload", new PayloadSetterDefinitionParser());
		registerBeanDefinitionParser("set-variable", new VariableSetterDefinitionParser());
		registerBeanDefinitionParser("porua-logger", new PoruaLoggerDefinitionParser());
		registerBeanDefinitionParser("java-to-json", new JavaToJsonDefinitionParser());
		registerBeanDefinitionParser("file-poller", new PoruaFilePollerDefinitionParser());
		;
	}
}
