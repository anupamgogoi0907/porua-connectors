package com.porua.core.flow;

import java.net.URL;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.porua.core.context.PoruaClassLoader;

public abstract class ParentFlow implements ApplicationContextAware {
	protected ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public abstract void startFlow();

	public void addPoruaClassLoader(URL url) {
		FileSystemXmlApplicationContext ctx = (FileSystemXmlApplicationContext) applicationContext;
		DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) ctx.getBeanFactory();
		GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
		beanDefinition.setBeanClass(PoruaClassLoader.class);
		beanDefinition.setScope(BeanDefinition.SCOPE_SINGLETON);
		ConstructorArgumentValues cav = new ConstructorArgumentValues();
		cav.addGenericArgumentValue(url);
		beanDefinition.setConstructorArgumentValues(cav);
		beanFactory.registerBeanDefinition("poruaClassLoader", beanDefinition);

	}
}
