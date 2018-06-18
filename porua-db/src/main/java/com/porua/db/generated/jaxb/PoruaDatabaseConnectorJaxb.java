package com.porua.db.generated.jaxb;

import java.lang.String;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(
    name = "db-connector",
    namespace = "http://www.porua.org/db"
)
public class PoruaDatabaseConnectorJaxb {
  @XmlAttribute(
      name = "query"
  )
  private String query;

  @XmlAttribute(
      name = "config-ref"
  )
  String configref;
}
