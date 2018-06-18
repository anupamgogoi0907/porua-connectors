package com.porua.component.generated.palette;

import com.porua.core.tag.ConfigProperty;
import com.porua.core.tag.Connector;
import java.lang.String;

@Connector(
    tagName = "set-payload",
    tagNamespace = "http://www.porua.org/components",
    tagSchemaLocation = "http://www.porua.org/components/components.xsd",
    imageName = "core-payload-setter.png"
)
public class PayloadSetterPalette {
  @ConfigProperty
  String payload;

  @ConfigProperty
  String file;
}
