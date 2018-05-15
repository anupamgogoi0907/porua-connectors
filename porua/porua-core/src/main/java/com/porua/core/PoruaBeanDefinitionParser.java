package com.porua.core;

import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

public class PoruaBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {
	private static int n = 0;
	private Class<?> clazz;

	protected PoruaBeanDefinitionParser(Class<?> clazz) {
		this.clazz = clazz;
	}

	@Override
	protected Class<?> getBeanClass(Element element) {
		return this.clazz;
	}

	@Override
	protected String resolveId(Element element, AbstractBeanDefinition definition, ParserContext parserContext)
			throws BeanDefinitionStoreException {
		String id = super.resolveId(element, definition, parserContext);
		if (StringUtils.hasText(id))
			return id;
		return this.clazz.getName().concat("#") + n++;
	}
}
