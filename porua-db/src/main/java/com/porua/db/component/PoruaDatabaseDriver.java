package com.porua.db.component;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

public class PoruaDatabaseDriver implements Driver {
	private Driver driver;

	public PoruaDatabaseDriver(Driver driver) {
		this.driver = driver;
	}

	@Override
	public Connection connect(String url, Properties info) throws SQLException {
		return this.driver.connect(url, info);
	}

	@Override
	public boolean acceptsURL(String url) throws SQLException {
		return this.driver.acceptsURL(url);
	}

	@Override
	public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
		return this.driver.getPropertyInfo(url, info);
	}

	@Override
	public int getMajorVersion() {
		return this.driver.getMajorVersion();
	}

	@Override
	public int getMinorVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean jdbcCompliant() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

}
