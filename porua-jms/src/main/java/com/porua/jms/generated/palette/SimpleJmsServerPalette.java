package com.porua.jms.generated.palette;

import com.porua.core.tag.ConfigProperty;
import com.porua.core.tag.Connector;
import java.lang.String;

@Connector(
    tagName = "listener",
    tagNamespace = "http://www.porua.org/jms",
    tagSchemaLocation = "http://www.porua.org/jms/jms.xsd",
    imageName = "jms.png"
)
public class SimpleJmsServerPalette {
  @ConfigProperty
  String url;

  @ConfigProperty
  String subject;
}
