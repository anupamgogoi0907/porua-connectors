package com.porua.db.generated.spring;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class NamespaceHandler extends NamespaceHandlerSupport {
  public void init() {
    registerBeanDefinitionParser("db-connector",new PoruaDatabaseConnectorDefinitionParser());registerBeanDefinitionParser("db-config",new PoruaDatabaseConfigurationDefinitionParser());;
  }
}
