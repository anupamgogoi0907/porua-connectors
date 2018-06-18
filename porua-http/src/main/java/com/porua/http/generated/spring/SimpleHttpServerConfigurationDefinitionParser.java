package com.porua.http.generated.spring;

import com.porua.core.PoruaBeanDefinitionParser;
import com.porua.http.server.SimpleHttpServerConfiguration;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.w3c.dom.Element;

public class SimpleHttpServerConfigurationDefinitionParser extends PoruaBeanDefinitionParser {
  public SimpleHttpServerConfigurationDefinitionParser() {
    super(SimpleHttpServerConfiguration.class);
  }

  protected void doParse(Element element, BeanDefinitionBuilder builder) {
    builder.setScope(BeanDefinition.SCOPE_PROTOTYPE);String name= element.getAttribute("name");builder.addPropertyValue("name",name);String method= element.getAttribute("method");builder.addPropertyValue("method",method);String host= element.getAttribute("host");builder.addPropertyValue("host",host);String port= element.getAttribute("port");builder.addPropertyValue("port",port);String path= element.getAttribute("path");builder.addPropertyValue("path",path);}
}
