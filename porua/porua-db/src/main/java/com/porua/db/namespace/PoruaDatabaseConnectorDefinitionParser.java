package com.porua.db.namespace;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.w3c.dom.Element;

import com.porua.core.PoruaBeanDefinitionParser;
import com.porua.db.component.PoruaDatabaseConnector;

public class PoruaDatabaseConnectorDefinitionParser extends PoruaBeanDefinitionParser {
	public PoruaDatabaseConnectorDefinitionParser() {
		super(PoruaDatabaseConnector.class);
	}

	@Override
	protected void doParse(Element element, BeanDefinitionBuilder builder) {
		builder.setScope(BeanDefinition.SCOPE_PROTOTYPE);
		String configRef = element.getAttribute("config-ref");
		String query = element.getAttribute("query");
		RuntimeBeanReference configRefBean = new RuntimeBeanReference(configRef);
		builder.addPropertyValue("config", configRefBean);
		builder.addPropertyValue("query", query);
	}

}
