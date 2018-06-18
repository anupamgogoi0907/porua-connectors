package com.porua.amqp.generated.jaxb;

import java.lang.Integer;
import java.lang.String;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(
    name = "jms-config",
    namespace = "http://www.porua.org/jms"
)
public class SimpleJmsServerConfigurationJaxb {
  @XmlAttribute(
      name = "name"
  )
  String name;

  @XmlAttribute(
      name = "login"
  )
  String login;

  @XmlAttribute(
      name = "password"
  )
  String password;

  @XmlAttribute(
      name = "vhost"
  )
  String vhost;

  @XmlAttribute(
      name = "host"
  )
  String host;

  @XmlAttribute(
      name = "port"
  )
  Integer port;
}
