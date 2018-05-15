package com.porua.core.namespace;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.w3c.dom.Element;

import com.porua.core.PoruaBeanDefinitionParser;
import com.porua.core.processor.component.PayloadSetter;

public class PayloadSetterBeanDefinitionParser extends PoruaBeanDefinitionParser {

	public PayloadSetterBeanDefinitionParser() {
		super(PayloadSetter.class);
	}

	@Override
	protected void doParse(Element element, BeanDefinitionBuilder builder) {
		builder.setScope(BeanDefinition.SCOPE_PROTOTYPE);
		Object payload = element.getAttribute("payload");
		String file = element.getAttribute("file");
		builder.addPropertyValue("payload", payload);
		builder.addPropertyValue("file", file);
	}

}
