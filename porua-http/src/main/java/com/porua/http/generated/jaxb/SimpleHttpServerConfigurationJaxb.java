package com.porua.http.generated.jaxb;

import java.lang.String;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(
    name = "listener-config",
    namespace = "http://www.porua.org/http"
)
public class SimpleHttpServerConfigurationJaxb {
  @XmlAttribute(
      name = "name"
  )
  String name;

  @XmlAttribute(
      name = "protocol"
  )
  private HTTP_SERVER_PROTOCOLS protocol;

  @XmlAttribute(
      name = "host"
  )
  String host;

  @XmlAttribute(
      name = "port"
  )
  int port;

  @XmlAttribute(
      name = "path"
  )
  String path;

  @XmlEnum
  enum HTTP_SERVER_PROTOCOLS {
    HTTP,

    HTTPS
  }
}
