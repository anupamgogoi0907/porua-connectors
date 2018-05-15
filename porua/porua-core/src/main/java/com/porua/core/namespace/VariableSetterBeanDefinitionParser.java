package com.porua.core.namespace;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.w3c.dom.Element;

import com.porua.core.PoruaBeanDefinitionParser;
import com.porua.core.processor.component.VariableSetter;

public class VariableSetterBeanDefinitionParser extends PoruaBeanDefinitionParser {

	public VariableSetterBeanDefinitionParser() {
		super(VariableSetter.class);
	}

	@Override
	protected void doParse(Element element, BeanDefinitionBuilder builder) {
		builder.setScope(BeanDefinition.SCOPE_PROTOTYPE);
		String name = element.getAttribute("name");
		Object value = element.getAttribute("value");
		builder.addPropertyValue("name", name);
		builder.addPropertyValue("value", value);
	}

}
