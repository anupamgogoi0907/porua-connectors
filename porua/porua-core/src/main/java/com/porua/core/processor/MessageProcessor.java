package com.porua.core.processor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.glassfish.grizzly.http.server.Response;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.porua.core.PoruaConstants;
import com.porua.core.context.PoruaContext;

/**
 * Created by ac-agogoi on 10/5/17.
 */
public abstract class MessageProcessor implements ApplicationContextAware {
	protected PoruaContext poruaContext;
	protected ApplicationContext springContext;

	@Override
	public void setApplicationContext(ApplicationContext springContext) throws BeansException {
		this.springContext = springContext;
	}

	public void process() {
		try {
			if (this.poruaContext.getProcessors().size() == 0) {
				System.out.println("Finished processing on " + Thread.currentThread().getName() + ", Result:\n " + getPoruaContext().getPayload());
				if (this.poruaContext.getResponder() instanceof Response) {
					Response response = (Response) this.poruaContext.getResponder();
					response.getWriter().write("Completed");
				}
			} else {
				MessageProcessor nextProcessor = this.poruaContext.getProcessors().remove();
				nextProcessor.process();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected void addBeanToSpringContext(Class<?> beanClass, Object[] constArgs, String beanName) {
		FileSystemXmlApplicationContext ctx = (FileSystemXmlApplicationContext) springContext;
		DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) ctx.getBeanFactory();
		GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
		beanDefinition.setBeanClass(beanClass);
		beanDefinition.setScope(BeanDefinition.SCOPE_SINGLETON);

		if (constArgs != null && constArgs.length != 0) {
			ConstructorArgumentValues cav = new ConstructorArgumentValues();
			for (int i = 0; i < constArgs.length; i++) {
				cav.addIndexedArgumentValue(i, constArgs[i]);
			}
			beanDefinition.setConstructorArgumentValues(cav);
		}
		beanFactory.registerBeanDefinition(beanName, beanDefinition);
	}

	protected String evaluateExpression(String query) {
		List<String> keyWords = Arrays.asList(PoruaConstants.PORUA_CONTEXT_VARIABLES, PoruaConstants.PORUA_CONTEXT_ATTRIBUTES, PoruaConstants.PORUA_PAYLOAD);
		for (String key : keyWords) {
			StringBuilder str = new StringBuilder();
			int i = query.indexOf(key);
			while (i >= 0) {
				for (int j = i; j < query.length(); j++) {
					str.append(query.charAt(j));
					if (Character.isWhitespace(query.charAt(j)) || (j + 1) == query.length()) {
						Object value = parseExpression(str);
						query = query.replace(str.toString().trim(), value.toString());
						// Reset.
						str = new StringBuilder();
						break;
					}

				}
				i = query.indexOf(key, i + key.length());

			}

		}
		return query;
	}

	protected Object parseExpression(StringBuilder str) {
		Object value = null;
		String[] arr = str.toString().trim().split(Pattern.quote("."));

		// Simple property.
		if (arr.length == 1) {
			value = getPoruaContextProperty(str.toString());
		} else {
			value = searchValueInMapOrList(getPoruaContextProperty(arr[0]), arr[1]);

			if (arr.length > 2) {
				for (int i = 2; i < arr.length; i++) {
					value = searchValueInMapOrList(value, arr[i]);
				}
			}
			if (value != null) {
				if (value instanceof String) {
					value = "\"" + value.toString() + "\"" + " ";
				} else {
					value = value + " ";
				}
			} else {
				value = " ";
			}
		}
		return value;
	}

	protected Object getPoruaContextProperty(String propName) {
		Object prop = null;
		switch (propName) {
		case PoruaConstants.PORUA_CONTEXT_VARIABLES:
			prop = this.poruaContext.getMapVariable();
			break;
		case PoruaConstants.PORUA_CONTEXT_ATTRIBUTES:
			prop = this.poruaContext.getMapHeader();
			break;
		case PoruaConstants.PORUA_PAYLOAD:
			prop = this.poruaContext.getPayload();
			break;
		default:
			break;
		}
		return prop;
	}

	protected Object searchValueInMapOrList(Object store, Object key) {
		Object value = null;
		if (store instanceof Map<?, ?>) {
			Map<?, ?> map = (Map<?, ?>) store;
			value = map.get(key);
		} else if (store instanceof List<?>) {
			List<?> list = (List<?>) store;
			value = list.get((int) key);
		}

		return value;
	}

	public PoruaContext getPoruaContext() {
		return poruaContext;
	}

	public void setPoruaContext(PoruaContext context) {
		this.poruaContext = context;
	}
}
