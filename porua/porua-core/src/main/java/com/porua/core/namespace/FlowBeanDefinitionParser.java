package com.porua.core.namespace;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

import com.porua.core.flow.Flow;
import com.porua.core.listener.MessageListener;

public class FlowBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {
	private static Set<String> flowNames;

	public FlowBeanDefinitionParser() {
		flowNames = new HashSet<>();
	}

	@Override
	protected Class<Flow> getBeanClass(Element element) {
		return Flow.class;
	}

	@Override
	protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
		builder.setScope(BeanDefinition.SCOPE_SINGLETON);
		parseFlow(element, parserContext, builder);
	}

	private void parseFlow(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
		String id = element.getAttribute("id");
		builder.addPropertyValue("name", id);

		GenericBeanDefinition beanDefinition = null;

		// Listener
		List<BeanDefinition> processors = new ManagedList<>();
		List<Element> elements = DomUtils.getChildElements(element);

		// Extract first component.
		Element listener = elements.get(0);
		beanDefinition = (GenericBeanDefinition) parserContext.getDelegate().parseCustomElement(listener);

		// Check if it's MessageListener.
		Class<?> cl = beanDefinition.getBeanClass();
		if (MessageListener.class.isAssignableFrom(cl)) {
			builder.addPropertyValue("listener", beanDefinition);
		} else {
			processors.add(beanDefinition);
		}
		elements.remove(0);

		// Processors
		for (Element el : elements) {
			beanDefinition = (GenericBeanDefinition) parserContext.getDelegate().parseCustomElement(el);
			processors.add(beanDefinition);
		}
		builder.addPropertyValue("processors", processors);
	}

	@Override
	protected String resolveId(Element element, AbstractBeanDefinition definition, ParserContext parserContext)
			throws BeanDefinitionStoreException {
		String id = super.resolveId(element, definition, parserContext);
		if (flowNames.contains(id)) {
			throw new BeanDefinitionStoreException("Duplicate flow name found.");
		} else {
			flowNames.add(id);
		}
		return id;
	}

}
