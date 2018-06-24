package com.porua.http.generated.spring;

import com.porua.core.PoruaBeanDefinitionParser;
import com.porua.http.request.SimpleHttpRequester;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.w3c.dom.Element;

public class SimpleHttpRequesterDefinitionParser extends PoruaBeanDefinitionParser {
  public SimpleHttpRequesterDefinitionParser() {
    super(SimpleHttpRequester.class);
  }

  protected void doParse(Element element, BeanDefinitionBuilder builder) {
    builder.setScope(BeanDefinition.SCOPE_PROTOTYPE);String path= element.getAttribute("path");builder.addPropertyValue("path",path);String method= element.getAttribute("method");builder.addPropertyValue("method",method);String configRef=element.getAttribute("config-ref");RuntimeBeanReference configRefBean = new RuntimeBeanReference(configRef);builder.addPropertyValue("config",configRefBean);}
}
