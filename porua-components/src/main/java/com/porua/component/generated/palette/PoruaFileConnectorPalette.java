package com.porua.component.generated.palette;

import com.porua.core.tag.ConfigProperty;
import com.porua.core.tag.Connector;
import java.lang.String;

@Connector(
    tagName = "file-connector",
    tagNamespace = "http://www.porua.org/components",
    tagSchemaLocation = "http://www.porua.org/components/components.xsd",
    imageName = "core-file-connector.png"
)
public class PoruaFileConnectorPalette {
  @ConfigProperty
  String file;

  @ConfigProperty(
      enumClass = OPERATIONS.class
  )
  private String operation;

  enum OPERATIONS {
    READ,

    WRITE
  }
}
