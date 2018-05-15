package com.porua.core.namespace;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.w3c.dom.Element;

import com.porua.core.PoruaBeanDefinitionParser;
import com.porua.core.processor.json.JsonToXml;

/**
 * Created by ac-agogoi on 10/23/17.
 */
public class JsonToXmlBeanDefinitionParser extends PoruaBeanDefinitionParser {
	public JsonToXmlBeanDefinitionParser() {
		super(JsonToXml.class);
	}

	@Override
	protected void doParse(Element element, BeanDefinitionBuilder builder) {
		builder.setScope(BeanDefinition.SCOPE_PROTOTYPE);
		String mimeType = element.getAttribute("mimeType");
		builder.addPropertyValue("mimeType", mimeType);
	}

}
