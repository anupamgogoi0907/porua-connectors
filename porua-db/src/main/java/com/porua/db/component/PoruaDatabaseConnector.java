package com.porua.db.component;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.porua.core.PoruaConstants;
import com.porua.core.context.PoruaClassLoader;
import com.porua.core.processor.MessageProcessor;
import com.porua.core.tag.ConfigProperty;
import com.porua.core.tag.Connector;
import com.porua.core.tag.ConnectorConfig;
import com.porua.core.utility.PoruaUtility;

@Connector(tagName = "db-connector", tagNamespace = "http://www.porua.org/db", tagSchemaLocation = "http://www.porua.org/db/db.xsd", imageName = "core-database.png")
public class PoruaDatabaseConnector extends MessageProcessor {

	@ConfigProperty
	private String query;

	@ConnectorConfig(configName = "config-ref", tagName = "db-config")
	private PoruaDatabaseConfiguration config;

	private static Logger logger = LogManager.getLogger(PoruaDatabaseConnector.class);

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
			query = evaluateQuery(query);
			logger.info("Query processed: " + query);
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
		logger.info("Adding datasource to spring context.");
		PoruaClassLoader loader = super.springContext.getBean(PoruaClassLoader.class);
		Object[] args = new Object[5];
		args[0] = config.getUrl();
		args[1] = config.getDriverClass();
		args[2] = config.getLogin();
		args[3] = config.getPassword();
		args[4] = loader;
		PoruaUtility.addBeanToSpringContext("poruaDataSource", PoruaDataSource.class, (FileSystemXmlApplicationContext) super.springContext, args);

	}

	/**
	 * Search the query for possible expressions and evaluate the expression.
	 * 
	 * @param query
	 * @return
	 */
	protected String evaluateQuery(String query) {
		List<String> keyWords = Arrays.asList(PoruaConstants.PORUA_CONTEXT_VARIABLES, PoruaConstants.PORUA_CONTEXT_ATTRIBUTES, PoruaConstants.PORUA_PAYLOAD);
		for (String key : keyWords) {
			StringBuilder str = new StringBuilder();
			int i = query.indexOf(key);
			while (i >= 0) {
				for (int j = i; j < query.length(); j++) {
					str.append(query.charAt(j));
					if (Character.isWhitespace(query.charAt(j)) || (j + 1) == query.length()) {
						// Object value = parseExpression(str);
						// query = query.replace(str.toString().trim(), value.toString());
						// Reset.
						str = new StringBuilder();
						break;
					}

				}
				i = query.indexOf(key, i + key.length());

			}

		}
		return query;
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
