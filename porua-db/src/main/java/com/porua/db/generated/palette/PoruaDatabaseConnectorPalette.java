package com.porua.db.generated.palette;

import com.porua.core.tag.ConfigProperty;
import com.porua.core.tag.Connector;
import com.porua.core.tag.ConnectorConfig;
import java.lang.String;

@Connector(
    tagName = "db-connector",
    tagNamespace = "http://www.porua.org/db",
    tagSchemaLocation = "http://www.porua.org/db/db.xsd",
    imageName = "core-database.png"
)
public class PoruaDatabaseConnectorPalette {
  @ConfigProperty
  String query;

  @ConfigProperty(
      enumClass = OPERATIONS.class
  )
  private String operation;

  @ConnectorConfig(
      configName = "config-ref",
      tagName = "db-config"
  )
  PoruaDatabaseConfigurationPalette config;

  enum OPERATIONS {
    SELECT,

    INSERT,

    UPDATE
  }
}
