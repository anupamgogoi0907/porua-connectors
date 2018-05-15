package com.porua.core.processor.expression;

import com.porua.core.processor.MessageProcessor;

public class ExpressionProcessor extends MessageProcessor {

	private String expression;

	@Override
	public void process() {
		super.process();
	}

	Object findValue() {
		Object root = null;
		String[] arr = expression.split(".");
		if (arr != null) {
			if (arr[0].equalsIgnoreCase("poruaContext")) {
				root = super.getPoruaContext();
			}
		} else {
			root = expression;
		}
		return root;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

}
