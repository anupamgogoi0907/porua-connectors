package com.porua.component.generated.jaxb;

import java.lang.String;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(
    name = "file-connector",
    namespace = "http://www.porua.org/components"
)
public class PoruaFileConnectorJaxb {
  @XmlAttribute(
      name = "file"
  )
  private String file;

  @XmlAttribute(
      name = "operation"
  )
  private OPERATIONS operation;

  @XmlEnum
  enum OPERATIONS {
    READ,

    WRITE
  }
}
