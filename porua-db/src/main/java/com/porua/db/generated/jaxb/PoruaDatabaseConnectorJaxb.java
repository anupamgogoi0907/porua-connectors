package com.porua.db.generated.jaxb;

import java.lang.String;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlEnum;
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
      name = "operation"
  )
  private OPERATIONS operation;

  @XmlAttribute(
      name = "config-ref"
  )
  String configref;

  @XmlEnum
  enum OPERATIONS {
    SELECT,

    INSERT,

    UPDATE
  }
}
