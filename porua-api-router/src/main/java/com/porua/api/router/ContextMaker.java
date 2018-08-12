package com.porua.api.router;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.porua.core.context.PoruaContext;
import com.porua.core.flow.Flow;
import com.porua.core.processor.MessageProcessor;

public class ContextMaker {
	private Logger logger = LogManager.getLogger(ContextMaker.class);

	protected void makeContext(Configuration config, UriInfo uriInfo, HttpHeaders headers, Object payload,
			AsyncResponse asyncResponse) {
		logger.debug("Making context...");

		Flow flow = (Flow) config.getProperties().get(ApiRouter.FLOW_KEY);
		PoruaContext context = new PoruaContext(flow.getProcessors());
		context.setResponder(asyncResponse);

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
