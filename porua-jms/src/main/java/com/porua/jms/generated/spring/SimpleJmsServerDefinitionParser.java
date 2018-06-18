package com.porua.jms.generated.spring;

import com.porua.core.PoruaBeanDefinitionParser;
import com.porua.jms.server.SimpleJmsServer;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.w3c.dom.Element;

public class SimpleJmsServerDefinitionParser extends PoruaBeanDefinitionParser {
	public SimpleJmsServerDefinitionParser() {
		super(SimpleJmsServer.class);
	}

	protected void doParse(Element element, BeanDefinitionBuilder builder) {
		builder.setScope(BeanDefinition.SCOPE_PROTOTYPE);
		String url = element.getAttribute("url");
		builder.addPropertyValue("url", url);
		String subject = element.getAttribute("subject");
		builder.addPropertyValue("subject", subject);
	}
}
