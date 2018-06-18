package com.porua.component.generated.jaxb;

import java.lang.String;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(
    name = "set-variable",
    namespace = "http://www.porua.org/components"
)
public class VariableSetterJaxb {
  @XmlAttribute(
      name = "name"
  )
  private String name;

  @XmlAttribute(
      name = "value"
  )
  private String value;
}
