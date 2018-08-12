package com.porua.api.generated.spring;

import com.porua.api.router.ApiRouter;
import com.porua.core.PoruaBeanDefinitionParser;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.w3c.dom.Element;

public class ApiRouterDefinitionParser extends PoruaBeanDefinitionParser {
	public ApiRouterDefinitionParser() {
		super(ApiRouter.class);
	}

	protected void doParse(Element element, BeanDefinitionBuilder builder) {
		builder.setScope(BeanDefinition.SCOPE_PROTOTYPE);
		String apiPath = element.getAttribute("apiPath");
		builder.addPropertyValue("apiPath", apiPath);
		String consolePath = element.getAttribute("consolePath");
		builder.addPropertyValue("consolePath", consolePath);
		String resources = element.getAttribute("resources");
		builder.addPropertyValue("resources", resources);
		String configRef = element.getAttribute("config-ref");
		RuntimeBeanReference configRefBean = new RuntimeBeanReference(configRef);
		builder.addPropertyValue("config", configRefBean);
	}
}
