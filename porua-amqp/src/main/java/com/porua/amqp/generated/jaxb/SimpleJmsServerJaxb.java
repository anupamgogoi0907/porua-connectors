package com.porua.amqp.generated.jaxb;

import java.lang.String;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(
    name = "listener",
    namespace = "http://www.porua.org/jms"
)
public class SimpleJmsServerJaxb {
  @XmlAttribute(
      name = "exchange"
  )
  private String exchange;

  @XmlAttribute(
      name = "exchangetype"
  )
  private EXCHANGETYPE exchangetype;

  @XmlAttribute(
      name = "queue"
  )
  private String queue;

  @XmlAttribute(
      name = "durable"
  )
  private DURABLE durable;

  @XmlAttribute(
      name = "routekey"
  )
  private String routekey;

  @XmlAttribute(
      name = "config-ref"
  )
  String configref;

  @XmlEnum
  enum EXCHANGETYPE {
    DIRECT,

    TOPIC,

    FANOUT,

    HEADERS
  }

  @XmlEnum
  enum DURABLE {
    TRUE,

    FALSE
  }
}
