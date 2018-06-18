package com.porua.amqp.subscriber;

import com.porua.core.tag.ConfigProperty;

public class SimpleJmsServerConfiguration {

	@ConfigProperty
	private String name;

	@ConfigProperty
	private String login;

	@ConfigProperty
	private String password;

	@ConfigProperty
	private String vhost;

	@ConfigProperty
	private String host;

	@ConfigProperty
	private Integer port;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getVhost() {
		return vhost;
	}

	public void setVhost(String vhost) {
		this.vhost = vhost;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
