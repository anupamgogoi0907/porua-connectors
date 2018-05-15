package com.porua.core.listener;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.porua.core.flow.Flow;

/**
 * Created by ac-agogoi on 10/5/17.
 */
public abstract class MessageListener implements ApplicationContextAware {
	protected ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public abstract void startListener(Flow flow);

}
