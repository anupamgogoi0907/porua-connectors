package com.porua.api.generated.spring;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class NamespaceHandler extends NamespaceHandlerSupport {
	public void init() {
		registerBeanDefinitionParser("api-router", new ApiRouterDefinitionParser());
		registerBeanDefinitionParser("router-config", new RouterConfigDefinitionParser());
		;
	}
}
