package com.porua.amqp.subscriber;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.porua.core.flow.Flow;
import com.porua.core.listener.MessageListener;
import com.porua.core.tag.ConfigProperty;
import com.porua.core.tag.Connector;
import com.porua.core.tag.ConnectorConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Created by ac-agogoi on 2/23/18.
 */

@Connector(tagName = "listener", tagNamespace = "http://www.porua.org/jms", tagSchemaLocation = "http://www.porua.org/jms/jms.xsd", imageName = "jms.png")
public class SimpleJmsServer extends MessageListener {
	enum DURABLE {
		TRUE, FALSE;
	}

	enum EXCHANGETYPE {
		DIRECT, TOPIC, FANOUT, HEADERS
	}

	@ConfigProperty
	private String exchange;

	@ConfigProperty(enumClass = EXCHANGETYPE.class)
	private String exchangetype;

	@ConfigProperty
	private String queue;

	@ConfigProperty(enumClass = DURABLE.class)
	private String durable;

	@ConfigProperty
	private String routekey;

	@ConnectorConfig(configName = "config-ref", tagName = "jms-config")
	private SimpleJmsServerConfiguration config;

	private Logger logger = LogManager.getLogger(SimpleJmsServer.class);
	private Connection conn = null;

	@Override
	public void startListener(Flow flow) {
		logger.info("Starting JMS subscriber...");
		try {
			conn = getJmsConnection();
			Channel channel = conn.createChannel();
			switch (exchangetype) {
			case "DIRECT":
				channel.exchangeDeclare(exchange, "direct", true);
				break;
			case "TOPIC":
				channel.exchangeDeclare(exchange, "topic", true);
				break;
			case "FANOUT":
				channel.exchangeDeclare(exchange, "fanout", true);
				break;
			case "HEADERS":
				channel.exchangeDeclare(exchange, "headers", true);
				break;
			default:
				channel.exchangeDeclare(exchange, "direct", true);
				break;
			}
			switch (durable) {
			case "TRUE":
				channel.queueDeclare(queue, true, false, false, null);
				break;
			case "FALSE":
				channel.queueDeclare(queue, false, false, false, null);
				break;
			default:
				channel.queueDeclare(queue, true, false, false, null);
				break;
			}
			channel.queueBind(queue, exchange, routekey);

			// Jms listener.
			JmsMessageListener listener = new JmsMessageListener(channel);
			listener.setFlow(flow);
			channel.basicConsume(queue, listener);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}

	@Override
	public void stopListener() {
		try {
			if (conn != null) {
				logger.info("Stopping JMS subscriber...");
				conn.close();
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	public Connection getJmsConnection() throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setUsername(config.getLogin());
		factory.setPassword(config.getPassword());
		factory.setVirtualHost(config.getVhost());
		factory.setHost(config.getHost());
		factory.setPort(config.getPort() == null ? 5672 : config.getPort());

		Connection conn = factory.newConnection();
		logger.info("JMS connection established...");
		return conn;
	}

	public String getExchange() {
		return exchange;
	}

	public void setExchange(String exchange) {
		this.exchange = exchange;
	}

	public String getQueue() {
		return queue;
	}

	public void setQueue(String queue) {
		this.queue = queue;
	}

	public String getRoutekey() {
		return routekey;
	}

	public void setRoutekey(String routekey) {
		this.routekey = routekey;
	}

	public SimpleJmsServerConfiguration getConfig() {
		return config;
	}

	public void setConfig(SimpleJmsServerConfiguration config) {
		this.config = config;
	}

	public String getExchangetype() {
		return exchangetype;
	}

	public void setExchangetype(String exchangetype) {
		this.exchangetype = exchangetype;
	}

	public String getDurable() {
		return durable;
	}

	public void setDurable(String durable) {
		this.durable = durable;
	}

}
