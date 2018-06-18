package com.porua.amqp.generated.spring;

import com.porua.amqp.subscriber.SimpleJmsServer;
import com.porua.core.PoruaBeanDefinitionParser;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.w3c.dom.Element;

public class SimpleJmsServerDefinitionParser extends PoruaBeanDefinitionParser {
  public SimpleJmsServerDefinitionParser() {
    super(SimpleJmsServer.class);
  }

  protected void doParse(Element element, BeanDefinitionBuilder builder) {
    builder.setScope(BeanDefinition.SCOPE_PROTOTYPE);String exchange= element.getAttribute("exchange");builder.addPropertyValue("exchange",exchange);String exchangetype= element.getAttribute("exchangetype");builder.addPropertyValue("exchangetype",exchangetype);String queue= element.getAttribute("queue");builder.addPropertyValue("queue",queue);String durable= element.getAttribute("durable");builder.addPropertyValue("durable",durable);String routekey= element.getAttribute("routekey");builder.addPropertyValue("routekey",routekey);String configRef=element.getAttribute("config-ref");RuntimeBeanReference configRefBean = new RuntimeBeanReference(configRef);builder.addPropertyValue("config",configRefBean);}
}
