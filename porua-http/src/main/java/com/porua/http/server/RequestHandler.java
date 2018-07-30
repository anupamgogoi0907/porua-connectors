package com.porua.http.server;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;

import com.porua.core.context.PoruaContext;
import com.porua.core.flow.Flow;
import com.porua.core.processor.MessageProcessor;

/**
 * Created by ac-agogoi on 11/8/17.
 */
public class RequestHandler extends HttpHandler {
	private Flow flow;

	private static Logger logger = LogManager.getLogger(RequestHandler.class);

	public RequestHandler(Flow flow) {
		this.flow = flow;
	}

	public void service(Request request, Response response) throws Exception {
		synchronized (this) {
			logger.info(SimpleHttpServer.class.getSimpleName() + " received request on: "
					+ Thread.currentThread().getName());

			// Suspend the Response till processing is done.
			response.suspend();

			// Make poruaContext for each request and start processing them.
			PoruaContext context = new PoruaContext(flow.getProcessors());
			context.setResponder(response);
			context.setPayload(request.getInputStream());
			context.setMapHeader(extractHeaders(request));
			context.setMapParameter(extractParameters(request));

			// Start the processor chain.
			MessageProcessor p = context.getProcessors().remove();
			p.process();
		}
	}

	/**
	 * Extract Headers from the Request
	 * 
	 * @param request
	 * @return
	 */
	Map<String, Object> extractHeaders(Request request) {
		Map<String, Object> mapHeader = new HashMap<>();
		Iterable<String> headers = request.getHeaderNames();
		for (String h : headers) {
			mapHeader.put(h, request.getHeader(h));
		}
		return mapHeader;
	}

	/**
	 * Extract Parameters from the Request
	 * 
	 * @param request
	 * @return
	 */
	Map<String, Object> extractParameters(Request request) {
		Map<String, Object> mapParameter = new HashMap<>();
		Map<String, String[]> mapParam = request.getParameterMap();

		for (String p : mapParam.keySet()) {
			String[] values = mapParam.get(p);
			if (values != null && values.length != 0) {
				if (NumberUtils.isCreatable(values[0])) {
					Number n = NumberUtils.createNumber(values[0]);
					mapParameter.put(p, n);
				} else {
					mapParameter.put(p, values[0]);
				}
			}
		}
		return mapParameter;
	}
}
