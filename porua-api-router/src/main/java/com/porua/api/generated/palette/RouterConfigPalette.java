package com.porua.api.generated.palette;

import com.porua.core.tag.ConfigProperty;
import java.lang.String;

public class RouterConfigPalette {
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
  String serverPath;

  enum HTTP_SERVER_PROTOCOLS {
    HTTP,

    HTTPS
  }
}
