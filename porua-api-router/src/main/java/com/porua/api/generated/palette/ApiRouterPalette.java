package com.porua.api.generated.palette;

import com.porua.core.tag.ConfigProperty;
import com.porua.core.tag.Connector;
import com.porua.core.tag.ConnectorConfig;
import java.lang.String;

@Connector(
    tagName = "api-router",
    tagNamespace = "http://www.porua.org/apirouter",
    tagSchemaLocation = "http://www.porua.org/apirouter/apirouter.xsd",
    imageName = "porua-api-router.png"
)
public class ApiRouterPalette {
  @ConfigProperty
  String apiPath;

  @ConfigProperty
  String consolePath;

  @ConnectorConfig(
      configName = "config-ref",
      tagName = "router-config"
  )
  RouterConfigPalette config;
}
