package com.porua.db.generated.spring;

import com.porua.core.PoruaBeanDefinitionParser;
import com.porua.db.component.PoruaDatabaseConnector;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.w3c.dom.Element;

public class PoruaDatabaseConnectorDefinitionParser extends PoruaBeanDefinitionParser {
	public PoruaDatabaseConnectorDefinitionParser() {
		super(PoruaDatabaseConnector.class);
	}

	protected void doParse(Element element, BeanDefinitionBuilder builder) {
		builder.setScope(BeanDefinition.SCOPE_PROTOTYPE);
		String query = element.getAttribute("query");
		builder.addPropertyValue("query", query);
		String operation = element.getAttribute("operation");
		builder.addPropertyValue("operation", operation);
		String configRef = element.getAttribute("config-ref");
		RuntimeBeanReference configRefBean = new RuntimeBeanReference(configRef);
		builder.addPropertyValue("config", configRefBean);
	}
}
