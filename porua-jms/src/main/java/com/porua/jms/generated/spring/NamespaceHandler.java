package com.porua.jms.generated.spring;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class NamespaceHandler extends NamespaceHandlerSupport {
  public void init() {
    registerBeanDefinitionParser("listener",new SimpleJmsServerDefinitionParser());;
  }
}
