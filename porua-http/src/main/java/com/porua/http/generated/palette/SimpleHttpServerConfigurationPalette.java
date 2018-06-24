package com.porua.http.generated.palette;

import com.porua.core.tag.ConfigProperty;
import java.lang.String;

public class SimpleHttpServerConfigurationPalette {
  @ConfigProperty
  String name;

  @ConfigProperty(
      enumClass = HTTP_SERVER_PROTOCOLS.class
  )
  private String protocol;

  @ConfigProperty
  String host;

  @ConfigProperty
  int port;

  @ConfigProperty
  String path;

  enum HTTP_SERVER_PROTOCOLS {
    HTTP,

    HTTPS
  }
}
