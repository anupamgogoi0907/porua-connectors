package com.porua.component.generated.spring;

import com.porua.component.variable.VariableSetter;
import com.porua.core.PoruaBeanDefinitionParser;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.w3c.dom.Element;

public class VariableSetterDefinitionParser extends PoruaBeanDefinitionParser {
  public VariableSetterDefinitionParser() {
    super(VariableSetter.class);
  }

  protected void doParse(Element element, BeanDefinitionBuilder builder) {
    builder.setScope(BeanDefinition.SCOPE_PROTOTYPE);String name= element.getAttribute("name");builder.addPropertyValue("name",name);String value= element.getAttribute("value");builder.addPropertyValue("value",value);}
}
