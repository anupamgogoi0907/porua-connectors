package com.porua.component.generated.spring;

import com.porua.component.json.JavaToJson;
import com.porua.core.PoruaBeanDefinitionParser;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.w3c.dom.Element;

public class JavaToJsonDefinitionParser extends PoruaBeanDefinitionParser {
  public JavaToJsonDefinitionParser() {
    super(JavaToJson.class);
  }

  protected void doParse(Element element, BeanDefinitionBuilder builder) {
    builder.setScope(BeanDefinition.SCOPE_PROTOTYPE);}
}
