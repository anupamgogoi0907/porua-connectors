package com.porua.jms.server;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import com.porua.core.context.PoruaContext;
import com.porua.core.flow.Flow;
import com.porua.core.processor.MessageProcessor;

public class JmsMessageListener implements MessageListener {
	private Flow flow;

	public JmsMessageListener(Flow flow) {
		this.flow = flow;
	}

	@Override
	public void onMessage(Message message) {
		try {
			TextMessage text = (TextMessage) message;
			// Make poruaContext for each request and start processing them.
			PoruaContext context = new PoruaContext(flow.getProcessors());
			context.setPayload(text.getText());

			// Start the processor chain.
			MessageProcessor p = context.getProcessors().remove();
			p.process();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
