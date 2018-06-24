package com.porua.http.generated.spring;

import com.porua.core.PoruaBeanDefinitionParser;
import com.porua.http.request.SimpleHttpRequesterConfiguration;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.w3c.dom.Element;

public class SimpleHttpRequesterConfigurationDefinitionParser extends PoruaBeanDefinitionParser {
  public SimpleHttpRequesterConfigurationDefinitionParser() {
    super(SimpleHttpRequesterConfiguration.class);
  }

  protected void doParse(Element element, BeanDefinitionBuilder builder) {
    builder.setScope(BeanDefinition.SCOPE_PROTOTYPE);String name= element.getAttribute("name");builder.addPropertyValue("name",name);String host= element.getAttribute("host");builder.addPropertyValue("host",host);String port= element.getAttribute("port");builder.addPropertyValue("port",port);String path= element.getAttribute("path");builder.addPropertyValue("path",path);String parmsfile= element.getAttribute("parmsfile");builder.addPropertyValue("parmsfile",parmsfile);String headersfile= element.getAttribute("headersfile");builder.addPropertyValue("headersfile",headersfile);}
}
