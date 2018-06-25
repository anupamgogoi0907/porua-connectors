package com.porua.http.request;

import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.Response;
import com.porua.core.processor.MessageProcessor;

public class RequestHandler extends AsyncCompletionHandler<Response> {

	MessageProcessor currentProcessor;

	/**
	 * 
	 * 
	 * @param poruaContext
	 */
	public RequestHandler(MessageProcessor currentProcessor) {
		this.currentProcessor = currentProcessor;
	}

	/**
	 * Process response from Http client in different thread.
	 * 
	 */
	@Override
	public Response onCompleted(Response response) throws Exception {
		if (this.currentProcessor.getPoruaContext().getProcessors().isEmpty()) {
			Object res = this.currentProcessor.getPoruaContext().getResponder();
			if (res instanceof org.glassfish.grizzly.http.server.Response) {
				org.glassfish.grizzly.http.server.Response responder = (org.glassfish.grizzly.http.server.Response) res;
				responder.getWriter().write(response.getResponseBody());
				responder.resume();
			}
		} else {
			this.currentProcessor.getPoruaContext().setPayload(response.getResponseBodyAsStream());
			MessageProcessor nextProcessor = this.currentProcessor.getPoruaContext().getProcessors().remove();
			nextProcessor.process();
		}
		return null;
	}

}
