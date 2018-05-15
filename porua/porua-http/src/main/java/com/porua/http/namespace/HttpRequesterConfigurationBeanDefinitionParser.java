package com.porua.http.namespace;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.w3c.dom.Element;

import com.porua.core.PoruaBeanDefinitionParser;
import com.porua.http.request.SimpleHttpRequesterConfiguration;

/**
 * Created by ac-agogoi on 12/12/17.
 */
public class HttpRequesterConfigurationBeanDefinitionParser extends PoruaBeanDefinitionParser {
	public HttpRequesterConfigurationBeanDefinitionParser() {
		super(SimpleHttpRequesterConfiguration.class);
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
