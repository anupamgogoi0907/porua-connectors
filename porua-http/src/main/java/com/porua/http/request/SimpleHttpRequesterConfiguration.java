package com.porua.http.request;

import com.porua.core.tag.ConfigProperty;

/**
 * Created by ac-agogoi on 12/12/17.
 */
public class SimpleHttpRequesterConfiguration {

	@ConfigProperty
	private String name;

	@ConfigProperty
	private String method;

	@ConfigProperty
	private String host;

	@ConfigProperty
	private int port;

	@ConfigProperty
	private String path;

	@ConfigProperty
	private String parmsfile;

	@ConfigProperty
	private String headersfile;

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

}
