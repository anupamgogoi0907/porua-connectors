package com.porua.db.generated.jaxb;

import java.lang.String;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(
    name = "db-config",
    namespace = "http://www.porua.org/db"
)
public class PoruaDatabaseConfigurationJaxb {
  @XmlAttribute(
      name = "name"
  )
  String name;

  @XmlAttribute(
      name = "url"
  )
  String url;

  @XmlAttribute(
      name = "driverClass"
  )
  String driverClass;

  @XmlAttribute(
      name = "login"
  )
  String login;

  @XmlAttribute(
      name = "password"
  )
  String password;
}
