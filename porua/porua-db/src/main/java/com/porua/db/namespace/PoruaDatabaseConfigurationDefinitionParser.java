package com.porua.db.namespace;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.w3c.dom.Element;

import com.porua.core.PoruaBeanDefinitionParser;
import com.porua.db.component.PoruaDatabaseConfiguration;

public class PoruaDatabaseConfigurationDefinitionParser extends PoruaBeanDefinitionParser {
	public PoruaDatabaseConfigurationDefinitionParser() {
		super(PoruaDatabaseConfiguration.class);
	}

	@Override
	protected void doParse(Element element, BeanDefinitionBuilder builder) {
		builder.setScope(BeanDefinition.SCOPE_PROTOTYPE);
		String url = element.getAttribute("url");
		String driverClass = element.getAttribute("driverClass");
		String login = element.getAttribute("login");
		String password = element.getAttribute("password");

		builder.addPropertyValue("url", url);
		builder.addPropertyValue("driverClass", driverClass);
		builder.addPropertyValue("login", login);
		builder.addPropertyValue("password", password);
	}

}
