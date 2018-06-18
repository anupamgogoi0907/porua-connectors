package com.porua.http.generated.spring;

import com.porua.core.PoruaBeanDefinitionParser;
import com.porua.http.server.SimpleHttpServer;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.w3c.dom.Element;

public class SimpleHttpServerDefinitionParser extends PoruaBeanDefinitionParser {
  public SimpleHttpServerDefinitionParser() {
    super(SimpleHttpServer.class);
  }

  protected void doParse(Element element, BeanDefinitionBuilder builder) {
    builder.setScope(BeanDefinition.SCOPE_PROTOTYPE);String path= element.getAttribute("path");builder.addPropertyValue("path",path);String configRef=element.getAttribute("config-ref");RuntimeBeanReference configRefBean = new RuntimeBeanReference(configRef);builder.addPropertyValue("config",configRefBean);}
}
