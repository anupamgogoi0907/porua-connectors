package com.porua.http.server;

import org.apache.log4j.Logger;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.glassfish.grizzly.threadpool.ThreadPoolConfig;

import com.porua.core.flow.Flow;
import com.porua.core.listener.MessageListener;
import com.porua.core.tag.ConfigProperty;
import com.porua.core.tag.Connector;
import com.porua.core.tag.ConnectorConfig;
import com.porua.http.utility.HttpUtility;

/**
 * Spring Bean. Populated by Spring.
 */
@Connector(tagName = "listener", tagNamespace = "http://www.porua.org/http", tagSchemaLocation = "http://www.porua.org/http/http.xsd", imageName = "http-listener.png")
public class SimpleHttpServer extends MessageListener {

	@ConfigProperty
	private String path;

	@ConnectorConfig(configName = "config-ref", tagName = "listener-config")
	private SimpleHttpServerConfiguration config;

	private HttpServer server;
	
	private Logger logger=Logger.getLogger(SimpleHttpServer.class);

	/**
	 * Starts the Http Listener.
	 */
	@Override
	public void startListener(Flow flow) {
		try {
			configThread();
			path = (path == null || path.equals("")) ? config.getPath() : config.getPath().concat(path);
			path = HttpUtility.sanitizePath(path);
			server.getServerConfiguration().addHttpHandler(new RequestHandler(flow), path);
			server.start();
			logger.info("Listening on: " + "http://" + config.getHost() + ":" + config.getPort() + path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Stops the Http Listener.
	 */
	@Override
	public void stopListener() {
		logger.info("Shutting downn server:" + "http://" + config.getHost() + ":" + config.getPort() + path);
		server.shutdown();
	}

	/**
	 * Thread configuration for Http listener.
	 */
	public void configThread() {
		server = HttpServer.createSimpleServer(null, config.getHost(), config.getPort());
		ThreadPoolConfig threadPoolConfig = ThreadPoolConfig.defaultConfig().setPoolName("server").setCorePoolSize(20).setMaxPoolSize(20);
		NetworkListener listener = server.getListeners().iterator().next();
		listener.getTransport().setWorkerThreadPoolConfig(threadPoolConfig);
	}

	public SimpleHttpServerConfiguration getConfig() {
		return config;
	}

	public void setConfig(SimpleHttpServerConfiguration config) {
		this.config = config;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
