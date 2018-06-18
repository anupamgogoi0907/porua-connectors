package com.porua.db.generated.spring;

import com.porua.core.PoruaBeanDefinitionParser;
import com.porua.db.component.PoruaDatabaseConfiguration;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.w3c.dom.Element;

public class PoruaDatabaseConfigurationDefinitionParser extends PoruaBeanDefinitionParser {
  public PoruaDatabaseConfigurationDefinitionParser() {
    super(PoruaDatabaseConfiguration.class);
  }

  protected void doParse(Element element, BeanDefinitionBuilder builder) {
    builder.setScope(BeanDefinition.SCOPE_PROTOTYPE);String name= element.getAttribute("name");builder.addPropertyValue("name",name);String url= element.getAttribute("url");builder.addPropertyValue("url",url);String driverClass= element.getAttribute("driverClass");builder.addPropertyValue("driverClass",driverClass);String login= element.getAttribute("login");builder.addPropertyValue("login",login);String password= element.getAttribute("password");builder.addPropertyValue("password",password);}
}
