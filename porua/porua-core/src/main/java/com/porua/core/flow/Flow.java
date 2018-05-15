package com.porua.core.flow;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.porua.core.listener.MessageListener;
import com.porua.core.processor.MessageProcessor;
import com.porua.core.tag.Connector;

/**
 * Spring Bean. Populated by Spring.
 */
@Connector(tagName="porua-flow",tagNamespace="http://www.porua.org/core",tagSchemaLocation="http://www.porua.org/core/core.xsd",imageName="core-porua-flow.png")
public class Flow extends ParentFlow {

	// Configurable properties
	boolean active;
	private String name;
	private MessageListener listener;
	private Queue<MessageProcessor> processors;

	@Override
	public void startFlow() {
		System.out.println("Starting " + getName() + " on: " + Thread.currentThread().getName());
		listener.startListener(this);
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public MessageListener getListener() {
		return listener;
	}

	public void setListener(MessageListener listener) {
		this.listener = listener;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Queue<MessageProcessor> getProcessors() {
		return processors;
	}

	public void setProcessors(List<MessageProcessor> processors) {
		this.processors = new LinkedList<>(processors);
	}
}
