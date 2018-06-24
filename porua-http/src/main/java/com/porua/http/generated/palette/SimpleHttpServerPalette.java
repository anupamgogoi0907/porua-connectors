package com.porua.http.generated.palette;

import com.porua.core.tag.ConfigProperty;
import com.porua.core.tag.Connector;
import com.porua.core.tag.ConnectorConfig;
import java.lang.String;

@Connector(
    tagName = "listener",
    tagNamespace = "http://www.porua.org/http",
    tagSchemaLocation = "http://www.porua.org/http/http.xsd",
    imageName = "http-listener.png"
)
public class SimpleHttpServerPalette {
  @ConfigProperty
  String path;

  @ConfigProperty(
      enumClass = HTTP_SERVER_METHODS.class
  )
  private String method;

  @ConnectorConfig(
      configName = "config-ref",
      tagName = "listener-config"
  )
  SimpleHttpServerConfigurationPalette config;

  enum HTTP_SERVER_METHODS {
    GET,

    POST,

    PUT,

    DELETE
  }
}
