package com.porua.amqp.generated.spring;

import com.porua.amqp.subscriber.SimpleJmsServerConfiguration;
import com.porua.core.PoruaBeanDefinitionParser;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.w3c.dom.Element;

public class SimpleJmsServerConfigurationDefinitionParser extends PoruaBeanDefinitionParser {
  public SimpleJmsServerConfigurationDefinitionParser() {
    super(SimpleJmsServerConfiguration.class);
  }

  protected void doParse(Element element, BeanDefinitionBuilder builder) {
    builder.setScope(BeanDefinition.SCOPE_PROTOTYPE);String name= element.getAttribute("name");builder.addPropertyValue("name",name);String login= element.getAttribute("login");builder.addPropertyValue("login",login);String password= element.getAttribute("password");builder.addPropertyValue("password",password);String vhost= element.getAttribute("vhost");builder.addPropertyValue("vhost",vhost);String host= element.getAttribute("host");builder.addPropertyValue("host",host);String port= element.getAttribute("port");builder.addPropertyValue("port",port);}
}
