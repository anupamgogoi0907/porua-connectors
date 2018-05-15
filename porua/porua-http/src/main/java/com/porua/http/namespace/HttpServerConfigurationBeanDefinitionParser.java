package com.porua.http.namespace;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.w3c.dom.Element;

import com.porua.core.PoruaBeanDefinitionParser;
import com.porua.http.server.SimpleHttpServerConfiguration;

/**
 * Created by ac-agogoi on 10/25/17.
 */
public class HttpServerConfigurationBeanDefinitionParser extends PoruaBeanDefinitionParser {
	public HttpServerConfigurationBeanDefinitionParser() {
		super(SimpleHttpServerConfiguration.class);
	}

	@Override
	protected void doParse(Element element, BeanDefinitionBuilder builder) {
		builder.setScope(BeanDefinition.SCOPE_PROTOTYPE);
		String name = element.getAttribute("name");
		String method = element.getAttribute("method");
		String host = element.getAttribute("host");
		int port = Integer.parseInt(element.getAttribute("port"));
		String path = element.getAttribute("path");

		builder.addPropertyValue("name", name);
		builder.addPropertyValue("method", method);
		builder.addPropertyValue("host", host);
		builder.addPropertyValue("port", port);
		builder.addPropertyValue("path", path);
	}

}
