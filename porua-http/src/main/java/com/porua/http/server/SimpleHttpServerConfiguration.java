package com.porua.http.server;

import com.porua.core.tag.ConfigProperty;

/**
 * Spring Bean. Populated by Spring.
 */
public class SimpleHttpServerConfiguration {

	enum METHODS {
		GET, POST, PUT, DELETE
	}

	@ConfigProperty
	private String name;

	@ConfigProperty(enumClass = METHODS.class)
	private String method;

	@ConfigProperty
	private String host;

	@ConfigProperty
	private int port;

	@ConfigProperty
	private String path;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
