package com.porua.jms.generated.jaxb;

import java.lang.String;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(
    name = "listener",
    namespace = "http://www.porua.org/jms"
)
public class SimpleJmsServerJaxb {
  @XmlAttribute(
      name = "url"
  )
  private String url;

  @XmlAttribute(
      name = "subject"
  )
  private String subject;
}
