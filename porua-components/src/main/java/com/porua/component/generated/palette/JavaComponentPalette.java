package com.porua.component.generated.palette;

import com.porua.core.tag.ConfigProperty;
import com.porua.core.tag.Connector;
import java.lang.String;

@Connector(
    tagName = "java-component",
    tagNamespace = "http://www.porua.org/components",
    tagSchemaLocation = "http://www.porua.org/components/components.xsd",
    imageName = "core-java-component.png"
)
public class JavaComponentPalette {
  @ConfigProperty
  String className;
}
