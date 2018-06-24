package com.porua.http.request;

import com.porua.core.tag.ConfigProperty;

/**
 * Created by ac-agogoi on 12/12/17.
 */
public class SimpleHttpRequesterConfiguration {

	enum HTTP_REQUESTER_PROTOCOLS {
		HTTP, HTTPS
	}

	@ConfigProperty
	private String name;

	@ConfigProperty(enumClass = HTTP_REQUESTER_PROTOCOLS.class)
	private String protocol;

	@ConfigProperty
	private String host;

	@ConfigProperty
	private Integer port;

	@ConfigProperty
	private String path;

	@ConfigProperty
	private String parmsfile;

	@ConfigProperty
	private String headersfile;

	public String getName() {
		return name;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getParmsfile() {
		return parmsfile;
	}

	public void setParmsfile(String parmsfile) {
		this.parmsfile = parmsfile;
	}

	public String getHeadersfile() {
		return headersfile;
	}

	public void setHeadersfile(String headersfile) {
		this.headersfile = headersfile;
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

}
