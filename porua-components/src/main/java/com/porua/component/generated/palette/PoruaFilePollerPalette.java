package com.porua.component.generated.palette;

import com.porua.core.tag.ConfigProperty;
import com.porua.core.tag.Connector;
import java.lang.Long;
import java.lang.String;

@Connector(
    tagName = "file-poller",
    tagNamespace = "http://www.porua.org/components",
    tagSchemaLocation = "http://www.porua.org/components/components.xsd",
    imageName = "core-file-poller.png"
)
public class PoruaFilePollerPalette {
  @ConfigProperty(
      enumClass = FILE_POLL_OPERATIONS.class
  )
  private String operation;

  @ConfigProperty
  String directory;

  @ConfigProperty
  Long timeout;

  enum FILE_POLL_OPERATIONS {
    CREATE,

    MODIFY,

    DELETE,

    ALL
  }
}
