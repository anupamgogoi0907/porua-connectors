package com.porua.jms.server;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

import com.porua.core.flow.Flow;
import com.porua.core.listener.MessageListener;
import com.porua.core.tag.ConfigProperty;
import com.porua.core.tag.Connector;

/**
 * Created by ac-agogoi on 2/23/18.
 */

@Connector(tagName = "listener", tagNamespace = "http://www.porua.org/jms", tagSchemaLocation = "http://www.porua.org/jms/jms.xsd", imageName = "jms.png")
public class SimpleJmsServer extends MessageListener {

	@ConfigProperty
	String url;

	@ConfigProperty
	String subject;

	@Override
	public void startListener(Flow flow) {
		try {
			// Getting JMS connection from the server
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
			Connection connection = connectionFactory.createConnection();
			connection.start();

			// Creating session for seding messages
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			// Getting the queue 'VALLYSOFTQ'
			Destination destination = session.createQueue(subject);

			// MessageConsumer is used for receiving (consuming) messages
			MessageConsumer consumer = session.createConsumer(destination);
			consumer.setMessageListener(new JmsMessageListener(flow));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void stopListener() {
		// TODO Auto-generated method stub

	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

}
