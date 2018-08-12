package com.porua.api.router;

import java.net.URI;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.grizzly.http.server.CLStaticHttpHandler;
import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import com.porua.api.utility.CorsHandler;
import com.porua.api.utility.RouterUtility;
import com.porua.core.flow.Flow;
import com.porua.core.listener.MessageListener;
import com.porua.core.tag.ConfigProperty;
import com.porua.core.tag.Connector;
import com.porua.core.tag.ConnectorConfig;

import io.swagger.jaxrs.config.BeanConfig;

@Connector(tagName = "api-router", tagNamespace = "http://www.porua.org/apirouter", tagSchemaLocation = "http://www.porua.org/apirouter/apirouter.xsd", imageName = "porua-api-router.png")
public class ApiRouter extends MessageListener {

	@ConfigProperty
	private String apiPath;

	@ConfigProperty
	private String consolePath;

	@ConfigProperty
	private String resources;

	@ConnectorConfig(configName = "config-ref", tagName = "router-config")
	private RouterConfig config;

	private Logger logger = LogManager.getLogger(ApiRouter.class);
	HttpServer server;

	@Override
	public void startListener(Flow flow) {
		try {
			// Bean Config
			configureSwagger();

			// Http Server
			URI uri = createURI();
			HttpServer server = GrizzlyHttpServerFactory.createHttpServer(createURI(), configureResource());

			// Swagger UI.
			HttpHandler docsHandler = new CLStaticHttpHandler(this.getClass().getClassLoader(), "dist/");
			server.getServerConfiguration().addHttpHandler(docsHandler, consolePath);

			server.start();
			logger.info("Listening on: " + uri.toString());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}

	/**
	 * Configure Swagger.
	 */
	private void configureSwagger() {
		logger.debug("Configuring Swagger...");
		BeanConfig beanConfig = new BeanConfig();
		beanConfig.setVersion("1.0.0");
		beanConfig.setSchemes(new String[] { "http" });
		beanConfig.setHost(config.getHost() + ":" + config.getPort());
		beanConfig.setBasePath(config.getServerPath());
		beanConfig.setResourcePackage(resources);
		beanConfig.setScan(true);
	}

	/**
	 * Configure server resource.
	 * 
	 * @return
	 */
	private ResourceConfig configureResource() {
		logger.debug("Configuring server resources...");
		ResourceConfig resourceConfig = new ResourceConfig();
		resourceConfig.packages(resources);
		resourceConfig.register(io.swagger.jaxrs.listing.ApiListingResource.class);
		resourceConfig.register(io.swagger.jaxrs.listing.SwaggerSerializers.class);
		resourceConfig.register(CorsHandler.class);
		return resourceConfig;
	}

	/**
	 * Create URI.
	 * 
	 * @return
	 * @throws Exception
	 */
	private URI createURI() throws Exception {
		String serverPath = RouterUtility.sanitizePath(config.getServerPath());
		String uri = "";
		if ("http".equalsIgnoreCase(config.getProtocol())) {
			uri = "http://".concat(config.getHost()).concat(":").concat(config.getPort() + "").concat(serverPath);
		} else if ("https".equalsIgnoreCase(config.getProtocol())) {
			uri = "https://".concat(config.getHost()).concat(":").concat(config.getPort() + "").concat(serverPath);
		}
		return URI.create(uri);
	}

	@Override
	public void stopListener() {
		logger.info("Shutting downn server:" + "http://" + config.getHost() + ":" + config.getPort());
		server.shutdown();
	}

	public String getApiPath() {
		return apiPath;
	}

	public void setApiPath(String apiPath) {
		this.apiPath = apiPath;
	}

	public String getConsolePath() {
		return consolePath;
	}

	public void setConsolePath(String consolePath) {
		this.consolePath = consolePath;
	}

	public String getResources() {
		return resources;
	}

	public void setResources(String resources) {
		this.resources = resources;
	}

	public RouterConfig getConfig() {
		return config;
	}

	public void setConfig(RouterConfig config) {
		this.config = config;
	}

}
