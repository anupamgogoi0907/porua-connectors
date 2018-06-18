package com.porua.component.generated.jaxb;

import java.lang.String;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(
    name = "set-payload",
    namespace = "http://www.porua.org/components"
)
public class PayloadSetterJaxb {
  @XmlAttribute(
      name = "payload"
  )
  private String payload;

  @XmlAttribute(
      name = "file"
  )
  private String file;
}
