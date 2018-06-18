package com.porua.amqp.generated.spring;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class NamespaceHandler extends NamespaceHandlerSupport {
  public void init() {
    registerBeanDefinitionParser("listener",new SimpleJmsServerDefinitionParser());registerBeanDefinitionParser("jms-config",new SimpleJmsServerConfigurationDefinitionParser());;
  }
}
