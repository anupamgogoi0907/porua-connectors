package com.porua.http.server;

import com.porua.core.tag.ConfigProperty;

/**
 * Spring Bean. Populated by Spring.
 */
public class SimpleHttpServerConfiguration {

	@ConfigProperty
	private String name;

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
