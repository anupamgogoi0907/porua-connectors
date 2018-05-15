package com.porua.core.processor.component;

import com.porua.core.PoruaConstants;
import com.porua.core.processor.MessageProcessor;
import com.porua.core.tag.Connector;
import com.porua.core.utility.PoruaUtility;
@Connector(tagName="set-variable",tagNamespace="http://www.porua.org/core",tagSchemaLocation="http://www.porua.org/core/core.xsd",imageName="core-variable-setter.png")
public class VariableSetter extends MessageProcessor {

	private String name;
	private Object value; // Value is always set as String in XML configuration.

	@Override
	public void process() {
		if (value != null) {
			Class<?> valueType = PoruaUtility.findDataType((String) value);
			if (valueType.isAssignableFrom(String.class)) {
				value = parseValue((String) value);
			} else {
				value = PoruaUtility.convertData((String) value);
			}
		}
		super.poruaContext.getMapVariable().put(name, value);
		super.process();
	}

	private String parseValue(String value) {
		if (value.contains(PoruaConstants.PORUA_CONTEXT_ATTRIBUTES)
				|| value.contains(PoruaConstants.PORUA_CONTEXT_VARIABLES)
				|| value.contains(PoruaConstants.PORUA_PAYLOAD)) {
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

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}
