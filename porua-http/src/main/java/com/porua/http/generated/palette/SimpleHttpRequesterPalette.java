package com.porua.http.generated.palette;

import com.porua.core.tag.ConfigProperty;
import com.porua.core.tag.Connector;
import com.porua.core.tag.ConnectorConfig;
import java.lang.String;

@Connector(
    tagName = "requestor",
    tagNamespace = "http://www.porua.org/http",
    tagSchemaLocation = "http://www.porua.org/http/http.xsd",
    imageName = "http-requestor.png"
)
public class SimpleHttpRequesterPalette {
  @ConfigProperty
  String path;

  @ConnectorConfig(
      configName = "config-ref",
      tagName = "requestor-config"
  )
  SimpleHttpRequesterConfigurationPalette config;
}
