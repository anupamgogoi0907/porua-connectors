package com.porua.http.generated.jaxb;

import java.lang.String;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(
    name = "listener",
    namespace = "http://www.porua.org/http"
)
public class SimpleHttpServerJaxb {
  @XmlAttribute(
      name = "path"
  )
  private String path;

  @XmlAttribute(
      name = "method"
  )
  private HTTP_SERVER_METHODS method;

  @XmlAttribute(
      name = "config-ref"
  )
  String configref;

  @XmlEnum
  enum HTTP_SERVER_METHODS {
    GET,

    POST,

    PUT,

    DELETE
  }
}
