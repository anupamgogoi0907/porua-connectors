package com.porua.api.generated.jaxb;

import java.lang.String;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(
    name = "api-router",
    namespace = "http://www.porua.org/apirouter"
)
public class ApiRouterJaxb {
  @XmlAttribute(
      name = "apiPath"
  )
  private String apiPath;

  @XmlAttribute(
      name = "consolePath"
  )
  private String consolePath;

  @XmlAttribute(
      name = "config-ref"
  )
  String configref;
}
