package com.porua.amqp.generated.palette;

import com.porua.core.tag.ConfigProperty;
import com.porua.core.tag.Connector;
import com.porua.core.tag.ConnectorConfig;
import java.lang.String;

@Connector(
    tagName = "listener",
    tagNamespace = "http://www.porua.org/jms",
    tagSchemaLocation = "http://www.porua.org/jms/jms.xsd",
    imageName = "jms.png"
)
public class SimpleJmsServerPalette {
  @ConfigProperty
  String exchange;

  @ConfigProperty(
      enumClass = EXCHANGETYPE.class
  )
  private String exchangetype;

  @ConfigProperty
  String queue;

  @ConfigProperty(
      enumClass = DURABLE.class
  )
  private String durable;

  @ConfigProperty
  String routekey;

  @ConnectorConfig(
      configName = "config-ref",
      tagName = "jms-config"
  )
  SimpleJmsServerConfigurationPalette config;

  enum EXCHANGETYPE {
    DIRECT,

    TOPIC,

    FANOUT,

    HEADERS
  }

  enum DURABLE {
    TRUE,

    FALSE
  }
}
