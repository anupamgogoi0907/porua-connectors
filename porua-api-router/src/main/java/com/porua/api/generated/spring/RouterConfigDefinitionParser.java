package com.porua.api.generated.spring;

import com.porua.api.router.RouterConfig;
import com.porua.core.PoruaBeanDefinitionParser;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.w3c.dom.Element;

public class RouterConfigDefinitionParser extends PoruaBeanDefinitionParser {
  public RouterConfigDefinitionParser() {
    super(RouterConfig.class);
  }

  protected void doParse(Element element, BeanDefinitionBuilder builder) {
    builder.setScope(BeanDefinition.SCOPE_PROTOTYPE);String name= element.getAttribute("name");builder.addPropertyValue("name",name);String protocol= element.getAttribute("protocol");builder.addPropertyValue("protocol",protocol);String host= element.getAttribute("host");builder.addPropertyValue("host",host);String port= element.getAttribute("port");builder.addPropertyValue("port",port);String serverPath= element.getAttribute("serverPath");builder.addPropertyValue("serverPath",serverPath);}
}
