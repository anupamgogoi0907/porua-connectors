package com.porua.jms.namespace;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.w3c.dom.Element;

import com.porua.core.PoruaBeanDefinitionParser;
import com.porua.jms.server.SimpleJmsServer;

/**
 * Created by ac-agogoi on 2/23/18.
 */
public class JmsServerBeanDefinitionParser extends PoruaBeanDefinitionParser {
	public JmsServerBeanDefinitionParser() {
		super(SimpleJmsServer.class);
	}

	@Override
	protected void doParse(Element element, BeanDefinitionBuilder builder) {
		builder.setScope(BeanDefinition.SCOPE_PROTOTYPE);
		String url = element.getAttribute("url");
		String subject = element.getAttribute("subject");
		builder.addPropertyValue("url", url);
		builder.addPropertyValue("subject", subject);
	}

}
