package com.porua.http.generated.spring;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class NamespaceHandler extends NamespaceHandlerSupport {
  public void init() {
    registerBeanDefinitionParser("listener",new SimpleHttpServerDefinitionParser());registerBeanDefinitionParser("listener-config",new SimpleHttpServerConfigurationDefinitionParser());registerBeanDefinitionParser("requestor",new SimpleHttpRequesterDefinitionParser());registerBeanDefinitionParser("requestor-config",new SimpleHttpRequesterConfigurationDefinitionParser());;
  }
}
