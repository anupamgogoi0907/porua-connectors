package com.porua.db.component;

import java.sql.Connection;
import java.sql.SQLException;

import com.porua.core.context.PoruaClassLoader;
import com.porua.core.processor.MessageProcessor;
import com.porua.core.tag.ConfigProperty;
import com.porua.core.tag.Connector;
import com.porua.core.tag.ConnectorConfig;

@Connector(tagName = "db-connector", tagNamespace = "http://www.porua.org/db", tagSchemaLocation = "http://www.porua.org/db/db.xsd", imageName = "core-database.png")
public class PoruaDatabaseConnector extends MessageProcessor {

	@ConfigProperty
	private String query;

	@ConnectorConfig(configName = "config-ref", tagName = "db-config")
	private PoruaDatabaseConfiguration config;

	@Override
	public void process() {
		PoruaDataSource dataSource = null;
		Connection conn = null;
		try {
			if (!super.springContext.containsBean("poruaDataSource")) {
				addDatasourceToSpringContext();
			}
			dataSource = super.springContext.getBean(PoruaDataSource.class);
			conn = dataSource.getConnection();
			query = super.evaluateExpression(query);
			System.out.println(query);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				super.process();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Add the DataSource to Spring context so that for farther call the DS can be
	 * searched in Spring context.
	 */
	private void addDatasourceToSpringContext() {
		PoruaClassLoader loader = super.springContext.getBean(PoruaClassLoader.class);
		Object[] args = new Object[5];
		args[0] = config.getUrl();
		args[1] = config.getDriverClass();
		args[2] = config.getLogin();
		args[3] = config.getPassword();
		args[4] = loader;
		super.addBeanToSpringContext(PoruaDataSource.class, args, "poruaDataSource");

	}

	public PoruaDatabaseConfiguration getConfig() {
		return config;
	}

	public void setConfig(PoruaDatabaseConfiguration config) {
		this.config = config;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}
}
