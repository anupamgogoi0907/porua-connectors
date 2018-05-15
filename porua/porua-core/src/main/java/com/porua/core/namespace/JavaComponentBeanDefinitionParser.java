package com.porua.core.namespace;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.w3c.dom.Element;

import com.porua.core.PoruaBeanDefinitionParser;
import com.porua.core.processor.component.JavaComponent;

public class JavaComponentBeanDefinitionParser extends PoruaBeanDefinitionParser {

	public JavaComponentBeanDefinitionParser() {
		super(JavaComponent.class);
	}

	@Override
	protected void doParse(Element element, BeanDefinitionBuilder builder) {
		builder.setScope(BeanDefinition.SCOPE_PROTOTYPE);
		String className = element.getAttribute("class");
		builder.addPropertyValue("className", className);
	}

}
