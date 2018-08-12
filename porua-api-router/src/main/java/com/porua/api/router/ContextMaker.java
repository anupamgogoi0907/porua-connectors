package com.porua.api.router;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import com.porua.core.context.PoruaContext;
import com.porua.core.flow.Flow;
import com.porua.core.processor.MessageProcessor;

public class ContextMaker {

	private Flow flow;

	public ContextMaker() {
		super();
	}

	public ContextMaker(Flow flow) {
		this.flow = flow;
	}

	protected void makeContext(UriInfo uriInfo, HttpHeaders headers, Object payload, AsyncResponse response) {
		PoruaContext context = new PoruaContext(flow.getProcessors());
		context.setResponder(response);
		// Start the processor chain.
		MessageProcessor p = context.getProcessors().remove();
		p.process();
	}

	Map<String, Object> extractHeaders(HttpHeaders headers) {
		Map<String, Object> mapHeader = new HashMap<>();
		MultivaluedMap<String, String> map = headers.getRequestHeaders();
		map.forEach((key, values) -> {
			mapHeader.put(key, values);
		});
		return mapHeader;
	}

}
