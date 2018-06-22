package com.porua.http.generated.jaxb;

import java.lang.String;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(
    name = "requestor",
    namespace = "http://www.porua.org/http"
)
public class SimpleHttpRequesterJaxb {
  @XmlAttribute(
      name = "path"
  )
  private String path;

  @XmlAttribute(
      name = "config-ref"
  )
  String configref;
}
