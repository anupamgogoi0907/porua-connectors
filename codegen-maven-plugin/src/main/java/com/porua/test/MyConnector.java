package com.porua.test;

import com.porua.core.processor.MessageProcessor;
import com.porua.core.tag.Connector;

@Connector(tagName = "my", tagNamespace = "http://www.porua.org/skype", tagSchemaLocation = "http://www.porua.org/skype/skype.xsd", imageName = "")
public class MyConnector extends MessageProcessor {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
