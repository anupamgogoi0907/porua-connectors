package com.porua.http.request;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Param;
import com.ning.http.client.RequestBuilder;
import com.porua.core.context.PoruaClassLoader;
import com.porua.core.processor.MessageProcessor;
import com.porua.core.tag.ConfigProperty;
import com.porua.core.tag.Connector;
import com.porua.core.tag.ConnectorConfig;
import com.porua.http.utility.HttpUtility;

@Connector(tagName = "requestor", tagNamespace = "http://www.porua.org/http", tagSchemaLocation = "http://www.porua.org/http/http.xsd", imageName = "http-requestor.png")
public class SimpleHttpRequester extends MessageProcessor {

	enum HTTP_REQUESETR_METHODS {
		GET, POST, DELETE, PUT
	}

	@ConfigProperty
	private String path;

	@ConfigProperty(enumClass = HTTP_REQUESETR_METHODS.class)
	private String method;

	@ConnectorConfig(configName = "config-ref", tagName = "requestor-config")
	private SimpleHttpRequesterConfiguration config;

	private static Logger logger = LogManager.getLogger(SimpleHttpRequester.class);
	private AsyncHttpClient asyncHttpClient;

	@Override
	public void process() {
		try {
			logger.debug("Receiving request...");
			StringBuilder url = new StringBuilder();
			url.append("http".equals(config.getProtocol().toLowerCase()) ? "http" : "https").append("://").append(config.getHost());
			url.append((config.getPort() == null || config.getPort() == 0) ? "" : ":" + config.getPort());
			url.append(HttpUtility.resolvePath(path, config.getPath()));

			// Make request.
			RequestBuilder rb = new RequestBuilder().setMethod(this.getMethod()).setUrl(url.toString());
			if (config.getParmsfile() != null && !"".equals(config.getParmsfile())) {
				rb.setQueryParams(makeQueryParams());
			}

			// Check if http client is already in Spring Context.If not add it.
			if (!super.springContext.containsBean("asyncHttpClient")) {
				addBeanToSpringContext(AsyncHttpClient.class, null, "asyncHttpClient");
			}
			asyncHttpClient = super.springContext.getBean(AsyncHttpClient.class);
			asyncHttpClient.prepareRequest(rb.build()).execute(new RequestHandler(this));

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

	}

	/**
	 * Read query params from property file.
	 * 
	 * @return
	 * @throws Exception
	 */
	private List<Param> makeQueryParams() throws Exception {
		Properties props = loadPropertyFile(config.getParmsfile());
		List<Param> queryParams = new ArrayList<>();
		if (!props.isEmpty()) {
			props.keySet().stream().forEach(objKey -> {
				String key = (String) objKey;
				queryParams.add(new Param(key, props.getProperty(key)));
			});
		}
		return queryParams;
	}

	/**
	 * Load property file.
	 * 
	 * @param propFile
	 * @return
	 * @throws Exception
	 */
	private Properties loadPropertyFile(String propFile) throws Exception {
		ClassLoader loader = super.springContext.getBean(PoruaClassLoader.class);
		InputStream is = loader.getResourceAsStream(propFile);
		Properties props = new Properties();
		props.load(is);
		return props;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public SimpleHttpRequesterConfiguration getConfig() {
		return config;
	}

	public void setConfig(SimpleHttpRequesterConfiguration config) {
		this.config = config;
	}

}
