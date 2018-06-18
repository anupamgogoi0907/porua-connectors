package com.porua.http.generated.palette;

import com.porua.core.tag.ConfigProperty;
import java.lang.String;

public class SimpleHttpServerConfigurationPalette {
  @ConfigProperty
  String name;

  @ConfigProperty(
      enumClass = METHODS.class
  )
  private String method;

  @ConfigProperty
  String host;

  @ConfigProperty
  int port;

  @ConfigProperty
  String path;

  enum METHODS {
    GET,

    POST,

    PUT,

    DELETE
  }
}
