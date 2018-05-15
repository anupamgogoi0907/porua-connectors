package com.porua.db.component;

import org.apache.commons.dbcp2.BasicDataSource;

public class PoruaDataSource extends BasicDataSource {

	public PoruaDataSource(String url, String driverClass, String login, String password,ClassLoader loader) {
		super.setDriverClassLoader(loader);
		super.setUrl(url);
		super.setDriverClassName(driverClass);
		super.setUsername(login);
		super.setPassword(password);
	}

}
