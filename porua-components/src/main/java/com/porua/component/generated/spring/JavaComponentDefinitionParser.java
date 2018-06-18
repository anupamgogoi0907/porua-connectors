package com.porua.component.generated.spring;

import com.porua.component.java.JavaComponent;
import com.porua.core.PoruaBeanDefinitionParser;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.w3c.dom.Element;

public class JavaComponentDefinitionParser extends PoruaBeanDefinitionParser {
  public JavaComponentDefinitionParser() {
    super(JavaComponent.class);
  }

  protected void doParse(Element element, BeanDefinitionBuilder builder) {
    builder.setScope(BeanDefinition.SCOPE_PROTOTYPE);String className= element.getAttribute("className");builder.addPropertyValue("className",className);}
}
