package com.porua.component.variable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.porua.core.pel.PoruaExpressionEvaluator;
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

	/**
	 * Parse the value and evaluate it.
	 * 
	 * @param valueExp
	 */
	private void parseValue(String valueExp) {
		try {
			PoruaExpressionEvaluator evaluator = super.springContext.getBean(PoruaExpressionEvaluator.class);

			Object res = null;
			if (valueExp.startsWith("json:")) {
				res = evaluator.parseJsonExpression(valueExp, poruaContext);
				super.poruaContext.getMapVariable().put(name, res);
			} else {
				res = evaluator.parseValueExpression(valueExp, poruaContext);
				String exp = "mapVariable['" + name + "']";
				evaluator.parseExpression(exp).setValue(poruaContext, res);
			}
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
