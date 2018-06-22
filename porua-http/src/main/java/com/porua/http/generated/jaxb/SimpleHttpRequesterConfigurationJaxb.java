package com.porua.http.generated.jaxb;

import java.lang.String;
import javax.xml.bind.annotation.XmlAttribute;
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
      name = "method"
  )
  String method;

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

  @XmlAttribute(
      name = "parmsfile"
  )
  String parmsfile;

  @XmlAttribute(
      name = "headersfile"
  )
  String headersfile;
}
