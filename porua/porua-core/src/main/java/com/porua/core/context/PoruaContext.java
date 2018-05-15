package com.porua.core.context;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import com.porua.core.processor.MessageProcessor;

/**
 * It' not a Spring Bean.
 */
public class PoruaContext {
	private Object payload;
	private Object responder;
	private Map<String, Object> mapHeader;
	private Map<String, Object> mapParameter;
	private Map<String, Object> mapVariable;
	private Queue<MessageProcessor> processors = null;

	public PoruaContext(Queue<MessageProcessor> processors) {
		this.mapHeader = new HashMap<>();
		this.mapParameter = new HashMap<>();
		this.mapVariable = new HashMap<>();
		
		// Copy the original processors and set the PoruaContext.
		this.processors = new LinkedList<>(processors);
		for (MessageProcessor processor : this.processors) {
			processor.setPoruaContext(this);
		}
	}

	public Object getPayload() {
		return payload;
	}

	public void setPayload(Object payload) {
		this.payload = payload;
	}

	public Object getResponder() {
		return responder;
	}

	public void setResponder(Object responder) {
		this.responder = responder;
	}

	public Map<String, Object> getMapHeader() {
		return mapHeader;
	}

	public void setMapHeader(Map<String, Object> mapHeader) {
		this.mapHeader = mapHeader;
	}

	public Map<String, Object> getMapParameter() {
		return mapParameter;
	}

	public void setMapParameter(Map<String, Object> mapParameter) {
		this.mapParameter = mapParameter;
	}

	public Map<String, Object> getMapVariable() {
		return mapVariable;
	}

	public void setMapVariable(Map<String, Object> mapVariable) {
		this.mapVariable = mapVariable;
	}

	public Queue<MessageProcessor> getProcessors() {
		return processors;
	}

	public void setProcessors(Queue<MessageProcessor> processors) {
		this.processors = processors;
	}

}
