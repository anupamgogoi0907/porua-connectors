package com.porua.db.component;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.porua.core.PoruaConstants;
import com.porua.core.context.PoruaClassLoader;
import com.porua.core.pel.PoruaExpressionEvaluator;
import com.porua.core.processor.MessageProcessor;
import com.porua.core.tag.ConfigProperty;
import com.porua.core.tag.Connector;
import com.porua.core.tag.ConnectorConfig;
import com.porua.core.utility.PoruaUtility;

@Connector(tagName = "db-connector", tagNamespace = "http://www.porua.org/db", tagSchemaLocation = "http://www.porua.org/db/db.xsd", imageName = "core-database.png")
public class PoruaDatabaseConnector extends MessageProcessor {

	private enum OPERATIONS {
		SELECT, INSERT, UPDATE
	}

	@ConfigProperty
	private String query;

	@ConfigProperty(enumClass = OPERATIONS.class)
	private String operation;

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
			if (query.endsWith(".sql")) {
				query = readSqlFile(query);
			}
			query = evaluateQuery(query);

			dataSource = super.springContext.getBean(PoruaDataSource.class);
			conn = dataSource.getConnection();
			if ("SELECT".equalsIgnoreCase(this.operation)) {
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(query);
				List<Map<String, Object>> listRow = resultToMap(rs);
				super.poruaContext.setPayload(listRow);
			} else if ("INSERT".equalsIgnoreCase(this.operation) || "UPDATE".equalsIgnoreCase(this.operation)) {
				PreparedStatement pst = conn.prepareStatement(query);
				pst.executeUpdate();
			}
			logger.info("Query processed: " + query);
			super.process();
		} catch (Exception e) {
			logger.error(e.getMessage());
			PoruaUtility.onError(poruaContext, e.getMessage());
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				logger.error(e.getMessage());
				PoruaUtility.onError(poruaContext, e.getMessage());
			}
		}

	}

	/**
	 * Read sql file.
	 * 
	 * @param fileName
	 * @return
	 */
	String readSqlFile(String fileName) {
		try {
			logger.debug("Reading query from: " + fileName);
			PoruaClassLoader loader = super.springContext.getBean(PoruaClassLoader.class);
			URL url = loader.getResource(fileName);
			String sql = new String(Files.readAllBytes(Paths.get(url.toURI())));
			return sql;
		} catch (Exception e) {
			logger.error(e.getMessage());
			PoruaUtility.onError(poruaContext, e.getMessage());
			return null;
		}

	}

	/**
	 * Convert ResultSet to List<Map>.
	 * 
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> resultToMap(ResultSet rs) throws Exception {
		List<Map<String, Object>> listRow = new ArrayList<>();
		ResultSetMetaData md = rs.getMetaData();
		while (rs.next()) {
			Map<String, Object> row = new HashMap<>();
			IntStream.rangeClosed(1, md.getColumnCount()).boxed().forEach((i) -> {
				try {
					row.put(md.getColumnName(i), rs.getObject(i));
				} catch (SQLException e) {
					logger.error(e.getMessage());
					PoruaUtility.onError(poruaContext, e.getMessage());
				}
			});
			listRow.add(row);
		}
		return listRow;
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
		PoruaUtility.addBeanToSpringContext("poruaDataSource", PoruaDataSource.class,
				(FileSystemXmlApplicationContext) super.springContext, args);

	}

	/**
	 * Search the query for possible expressions and evaluate the expression.
	 * 
	 * @param query
	 * @return
	 */
	protected String evaluateQuery(String query) {
		logger.debug("Evaluating " + query);
		PoruaExpressionEvaluator evaluator = super.springContext.getBean(PoruaExpressionEvaluator.class);
		Map<String, Object> mapExpValue = new HashMap<>();

		// Search for the keywords.
		PoruaConstants.CONTEXT_PROPS.forEach((String key) -> {
			StringBuilder strExp = new StringBuilder();
			int iCur = query.indexOf(key);
			while (iCur >= 0) {
				int iNext = 0;
				// Extract the substring untill a space occurs.
				for (iNext = iCur; iNext < query.length(); iNext++) {
					try {
						if (Character.isWhitespace(query.charAt(iNext)) || query.charAt(iNext) == ',') {
							Object value = evaluator.parseValueExpression(strExp.toString().trim(), poruaContext);
							mapExpValue.put(strExp.toString().trim(), value);
							strExp = new StringBuilder();
							break;
						} else {
							strExp.append(query.charAt(iNext));
						}
					} catch (Exception e) {
						logger.error(e.getMessage());
						PoruaUtility.onError(poruaContext, e.getMessage());
					}

				}
				iCur = query.indexOf(key, iNext);
			}
		});

		// Replace expression by its value.
		String resQuery = query;
		for (String strExp : mapExpValue.keySet()) {
			Object expValue = mapExpValue.get(strExp);
			if (Number.class.isAssignableFrom(expValue.getClass())) {
				resQuery = resQuery.replace(strExp, expValue.toString());
			} else {
				resQuery = resQuery.replace(strExp, "'" + expValue.toString() + "'");
			}
		}
		logger.info(resQuery);
		return resQuery;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public PoruaDatabaseConfiguration getConfig() {
		return config;
	}

	public void setConfig(PoruaDatabaseConfiguration config) {
		this.config = config;
	}

}
