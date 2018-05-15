package com.porua.http.namespace;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class HttpNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser("listener", new HttpServerBeanDefinitionParser());
        registerBeanDefinitionParser("listener-config", new HttpServerConfigurationBeanDefinitionParser());
        registerBeanDefinitionParser("requester",new HttpRequesterBeanDefinitionParser());
        registerBeanDefinitionParser("requester-config",new HttpRequesterConfigurationBeanDefinitionParser());
    }

}
