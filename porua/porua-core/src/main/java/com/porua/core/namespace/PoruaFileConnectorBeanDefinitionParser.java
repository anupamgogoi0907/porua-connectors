package com.porua.core.namespace;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.w3c.dom.Element;

import com.porua.core.PoruaBeanDefinitionParser;
import com.porua.core.file.PoruaFileConnector;

public class PoruaFileConnectorBeanDefinitionParser extends PoruaBeanDefinitionParser {

	protected PoruaFileConnectorBeanDefinitionParser() {
		super(PoruaFileConnector.class);
	}

	@Override
	protected void doParse(Element element, BeanDefinitionBuilder builder) {
		builder.setScope(BeanDefinition.SCOPE_PROTOTYPE);
		String operation = element.getAttribute("operation");
		String file = element.getAttribute("file");
		builder.addPropertyValue("operation", operation);
		builder.addPropertyValue("file", file);
	}

}
