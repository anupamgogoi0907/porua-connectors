package com.porua.component.variable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.porua.core.PoruaConstants;
import com.porua.core.processor.MessageProcessor;
import com.porua.core.tag.ConfigProperty;
import com.porua.core.tag.Connector;

@Connector(tagName = "set-variable", tagNamespace = "http://www.porua.org/components", tagSchemaLocation = "http://www.porua.org/components/components.xsd", imageName = "core-variable-setter.png")
public class VariableSetter extends MessageProcessor {

	@ConfigProperty
	private String name;

	@ConfigProperty
	private String value; // Value is always set as String in XML configuration.

	private static Logger logger = LogManager.getLogger(VariableSetter.class);

	@Override
	public void process() {
		if (value != null) {
			logger.info("Setting variable...");
			value = parseValue(value);
			super.poruaContext.getMapVariable().put(name, value);
		}

		super.process();
	}

	private String parseValue(String value) {
		if (value.contains(PoruaConstants.PORUA_CONTEXT_ATTRIBUTES) || value.contains(PoruaConstants.PORUA_CONTEXT_VARIABLES) || value.contains(PoruaConstants.PORUA_PAYLOAD)) {
			value = (String) super.parseExpression(new StringBuilder(value));
		} else {
			value = (String) value;
		}
		return value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
