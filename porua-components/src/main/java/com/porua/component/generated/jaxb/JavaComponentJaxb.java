package com.porua.component.generated.jaxb;

import java.lang.String;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(
    name = "java-component",
    namespace = "http://www.porua.org/components"
)
public class JavaComponentJaxb {
  @XmlAttribute(
      name = "className"
  )
  private String className;
}
