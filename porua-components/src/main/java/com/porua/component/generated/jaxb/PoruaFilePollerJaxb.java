package com.porua.component.generated.jaxb;

import java.lang.Long;
import java.lang.String;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(
    name = "file-poller",
    namespace = "http://www.porua.org/components"
)
public class PoruaFilePollerJaxb {
  @XmlAttribute(
      name = "operation"
  )
  private FILE_POLL_OPERATIONS operation;

  @XmlAttribute(
      name = "directory"
  )
  private String directory;

  @XmlAttribute(
      name = "timeout"
  )
  private Long timeout;

  @XmlEnum
  enum FILE_POLL_OPERATIONS {
    CREATE,

    MODIFY,

    DELETE,

    ALL
  }
}
