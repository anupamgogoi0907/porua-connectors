package com.porua.http.server;

import com.porua.core.flow.Flow;
import com.porua.core.listener.MessageListener;
import com.porua.core.tag.Connector;
import com.porua.core.tag.ConnectorConfig;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.glassfish.grizzly.threadpool.ThreadPoolConfig;

/**
 * Spring Bean. Populated by Spring.
 */
@Connector(tagName="listener",tagNamespace="http://www.porua.org/http",tagSchemaLocation="http://www.porua.org/http/http.xsd",imageName="http-listener.png")
public class SimpleHttpServer extends MessageListener {
	
	@ConnectorConfig(configName="config-ref",tagName="listener-config")
	private SimpleHttpServerConfiguration config;
	
	private HttpServer server;

	public void configThread() {
		server = HttpServer.createSimpleServer(null, config.getHost(), config.getPort());
		ThreadPoolConfig threadPoolConfig = ThreadPoolConfig.defaultConfig().setPoolName("server").setCorePoolSize(20)
				.setMaxPoolSize(20);
		NetworkListener listener = server.getListeners().iterator().next();
		listener.getTransport().setWorkerThreadPoolConfig(threadPoolConfig);
	}

	@Override
	public void startListener(Flow flow) {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					configThread();
					server.getServerConfiguration().addHttpHandler(new RequestHandler(flow), config.getPath());
					server.start();
					System.out.println("Listening on: " + "http://" + config.getHost() + ":" + config.getPort()
							+ config.getPath());
					System.in.read();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}, "Thread-" + config.getHost() + "-" + config.getPort());
		t.start();
	}

	public SimpleHttpServerConfiguration getConfig() {
		return config;
	}

	public void setConfig(SimpleHttpServerConfiguration config) {
		this.config = config;
	}

}
