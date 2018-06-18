package com.porua.component.generated.spring;

import com.porua.component.payload.PayloadSetter;
import com.porua.core.PoruaBeanDefinitionParser;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.w3c.dom.Element;

public class PayloadSetterDefinitionParser extends PoruaBeanDefinitionParser {
  public PayloadSetterDefinitionParser() {
    super(PayloadSetter.class);
  }

  protected void doParse(Element element, BeanDefinitionBuilder builder) {
    builder.setScope(BeanDefinition.SCOPE_PROTOTYPE);String payload= element.getAttribute("payload");builder.addPropertyValue("payload",payload);String file= element.getAttribute("file");builder.addPropertyValue("file",file);}
}
