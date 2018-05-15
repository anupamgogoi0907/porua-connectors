package com.porua.core.namespace;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Handles namespaces specific to Core components,processors.
 */
public class PoruaCoreNamespaceHandler extends NamespaceHandlerSupport {
	@Override
	public void init() {
		registerBeanDefinitionParser("porua-flow", new FlowBeanDefinitionParser());
		registerBeanDefinitionParser("json-to-java", new JsonToJavaBeanDefinitionParser());
		registerBeanDefinitionParser("json-to-xml", new JsonToXmlBeanDefinitionParser());
		registerBeanDefinitionParser("java-component", new JavaComponentBeanDefinitionParser());
		registerBeanDefinitionParser("set-variable", new VariableSetterBeanDefinitionParser());
		registerBeanDefinitionParser("set-payload", new PayloadSetterBeanDefinitionParser());
		registerBeanDefinitionParser("file-connector", new PoruaFileConnectorBeanDefinitionParser());
	}
}
