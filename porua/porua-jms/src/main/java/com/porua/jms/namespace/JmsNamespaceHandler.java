package com.porua.jms.namespace;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class JmsNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser("listener", new JmsServerBeanDefinitionParser());

    }

}
