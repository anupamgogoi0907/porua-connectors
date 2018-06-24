package com.porua.http.generated.palette;

import com.porua.core.tag.ConfigProperty;
import java.lang.Integer;
import java.lang.String;

public class SimpleHttpRequesterConfigurationPalette {
  @ConfigProperty
  String name;

  @ConfigProperty(
      enumClass = HTTP_REQUESTER_PROTOCOLS.class
  )
  private String protocol;

  @ConfigProperty
  String host;

  @ConfigProperty
  Integer port;

  @ConfigProperty
  String path;

  @ConfigProperty
  String parmsfile;

  @ConfigProperty
  String headersfile;

  enum HTTP_REQUESTER_PROTOCOLS {
    HTTP,

    HTTPS
  }
}
