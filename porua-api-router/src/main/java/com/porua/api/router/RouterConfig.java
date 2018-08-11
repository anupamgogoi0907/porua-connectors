package com.porua.api.router;

import com.porua.core.tag.ConfigProperty;

public class RouterConfig {
	enum HTTP_SERVER_PROTOCOLS {
		HTTP, HTTPS
	}

	@ConfigProperty
	private String name;

	@ConfigProperty(enumClass = HTTP_SERVER_PROTOCOLS.class)
	private String protocol;

	@ConfigProperty
	private String host;

	@ConfigProperty
	private int port;

	@ConfigProperty
	private String serverPath;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
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

	public String getServerPath() {
		return serverPath;
	}

	public void setServerPath(String serverPath) {
		this.serverPath = serverPath;
	}

}
