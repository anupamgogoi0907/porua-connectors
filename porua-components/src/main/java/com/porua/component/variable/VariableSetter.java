package com.porua.component.variable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

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
			parseValue(value);
		}
		super.process();
	}

	private void parseValue(String value) {
		try {
			logger.debug("Parsing value: " + this.value);
			ExpressionParser parser = new SpelExpressionParser();
			Object res = parser.parseExpression(this.value).getValue();
			String exp = "mapVariable['" + name + "']";
			parser.parseExpression(exp).setValue(poruaContext, res);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
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
