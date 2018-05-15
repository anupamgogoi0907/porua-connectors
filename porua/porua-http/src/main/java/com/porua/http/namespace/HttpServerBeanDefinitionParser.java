package com.porua.http.namespace;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import com.porua.core.PoruaBeanDefinitionParser;
import com.porua.http.server.SimpleHttpServer;

public class HttpServerBeanDefinitionParser extends PoruaBeanDefinitionParser {

	public HttpServerBeanDefinitionParser() {
		super(SimpleHttpServer.class);
	}

	@Override
	protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
		builder.setScope(BeanDefinition.SCOPE_PROTOTYPE);
		String configRef = element.getAttribute("config-ref");
		RuntimeBeanReference configRefBean = new RuntimeBeanReference(configRef);
		builder.addPropertyValue("config", configRefBean);
	}

}
