package com.porua.http.generated.jaxb;

import java.lang.Integer;
import java.lang.String;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(
    name = "requestor-config",
    namespace = "http://www.porua.org/http"
)
public class SimpleHttpRequesterConfigurationJaxb {
  @XmlAttribute(
      name = "name"
  )
  String name;

  @XmlAttribute(
      name = "protocol"
  )
  private HTTP_REQUESTER_PROTOCOLS protocol;

  @XmlAttribute(
      name = "host"
  )
  String host;

  @XmlAttribute(
      name = "port"
  )
  Integer port;

  @XmlAttribute(
      name = "path"
  )
  String path;

  @XmlAttribute(
      name = "parmsfile"
  )
  String parmsfile;

  @XmlAttribute(
      name = "headersfile"
  )
  String headersfile;

  @XmlEnum
  enum HTTP_REQUESTER_PROTOCOLS {
    HTTP,

    HTTPS
  }
}
