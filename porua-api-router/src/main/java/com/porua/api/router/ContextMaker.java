package com.porua.api.router;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.UriInfo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.server.ContainerRequest;

import com.porua.api.utility.RouterUtility;
import com.porua.core.context.PoruaContext;
import com.porua.core.control.PoruaSwitch;
import com.porua.core.flow.Flow;
import com.porua.core.processor.MessageProcessor;

public class ContextMaker {
	private Logger logger = LogManager.getLogger(ContextMaker.class);

	protected void makeContext(Configuration config, UriInfo uriInfo, ContainerRequest request, Object payload,
			AsyncResponse asyncResponse) {
		logger.debug("Making context...");

		Flow flow = (Flow) config.getProperties().get(ApiRouter.KEY_KEY);
		PoruaContext context = new PoruaContext(flow.getProcessors());
		context.setResponder(asyncResponse);
		context.setPayload(payload == null ? "" : payload);
		context.setMapHeader(extractHeaders(request));
		context.setMapParameter(extractParameters(uriInfo));

		// Start the processor chain.ContainerRequest
		MessageProcessor p = context.getProcessors().remove();
		if (p instanceof PoruaSwitch) {
			context.getMapVariable().put("path", request.getMethod() + ":/" + uriInfo.getPath());
		}
		p.process();
	}

	Map<String, Object> extractHeaders(ContainerRequest request) {
		logger.debug("Extracting headers...");
		Map<String, Object> mapHeader = new HashMap<>();
		request.getHeaders().keySet().forEach((h) -> {
			List<String> listHeader = request.getHeaders().get(h);
			String value = listHeader.stream().map(Object::toString).collect(Collectors.joining(","));
			Object numericValue = RouterUtility.isNumeric(value);
			if (numericValue != null) {
				mapHeader.put(h, numericValue);
			} else {
				mapHeader.put(h, value);
			}
		});
		return mapHeader;
	}

	/**
	 * Extract Query params.
	 * 
	 * @param uriInfo
	 * @return
	 */
	Map<String, Object> extractParameters(UriInfo uriInfo) {
		logger.debug("Extracting query params...");
		Map<String, Object> mapParameter = new HashMap<>();
		uriInfo.getQueryParameters().keySet().forEach((param) -> {
			List<String> listValue = uriInfo.getQueryParameters().get(param);
			String value = listValue.stream().map(Object::toString).collect(Collectors.joining(","));
			Object numericValue = RouterUtility.isNumeric(value);
			if (numericValue != null) {
				mapParameter.put(param, numericValue);
			} else {
				mapParameter.put(param, value);
			}
		});
		return mapParameter;
	}

}
