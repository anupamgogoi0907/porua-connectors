package com.porua.component.generated.spring;

import com.porua.component.file.PoruaFilePoller;
import com.porua.core.PoruaBeanDefinitionParser;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.w3c.dom.Element;

public class PoruaFilePollerDefinitionParser extends PoruaBeanDefinitionParser {
  public PoruaFilePollerDefinitionParser() {
    super(PoruaFilePoller.class);
  }

  protected void doParse(Element element, BeanDefinitionBuilder builder) {
    builder.setScope(BeanDefinition.SCOPE_PROTOTYPE);String operation= element.getAttribute("operation");builder.addPropertyValue("operation",operation);String directory= element.getAttribute("directory");builder.addPropertyValue("directory",directory);String timeout= element.getAttribute("timeout");builder.addPropertyValue("timeout",timeout);}
}
