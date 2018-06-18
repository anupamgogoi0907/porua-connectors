package com.porua.component.generated.spring;

import com.porua.component.file.PoruaFileConnector;
import com.porua.core.PoruaBeanDefinitionParser;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.w3c.dom.Element;

public class PoruaFileConnectorDefinitionParser extends PoruaBeanDefinitionParser {
  public PoruaFileConnectorDefinitionParser() {
    super(PoruaFileConnector.class);
  }

  protected void doParse(Element element, BeanDefinitionBuilder builder) {
    builder.setScope(BeanDefinition.SCOPE_PROTOTYPE);String file= element.getAttribute("file");builder.addPropertyValue("file",file);String operation= element.getAttribute("operation");builder.addPropertyValue("operation",operation);}
}
