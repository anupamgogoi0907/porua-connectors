package com.porua.amqp.subscriber;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.porua.core.context.PoruaContext;
import com.porua.core.flow.Flow;
import com.porua.core.processor.MessageProcessor;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class JmsMessageListener extends DefaultConsumer {
	private Flow flow;

	public JmsMessageListener(Channel channel) {
		super(channel);
	}

	@Override
	public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
			throws IOException {

		InputStream is = new ByteArrayInputStream(body);
		// Make poruaContext for each request and start processing them.
		if (flow != null) {
			PoruaContext context = new PoruaContext(flow.getProcessors());
			context.setPayload(is);

			// Start the processor chain.
			MessageProcessor p = context.getProcessors().remove();
			p.process();
		}
	}

	public void setFlow(Flow flow) {
		this.flow = flow;
	}

}
