package com.porua.component.generated.palette;

import com.porua.core.tag.ConfigProperty;
import com.porua.core.tag.Connector;
import java.lang.String;

@Connector(
    tagName = "set-variable",
    tagNamespace = "http://www.porua.org/components",
    tagSchemaLocation = "http://www.porua.org/components/components.xsd",
    imageName = "core-variable-setter.png"
)
public class VariableSetterPalette {
  @ConfigProperty
  String name;

  @ConfigProperty
  String value;
}
