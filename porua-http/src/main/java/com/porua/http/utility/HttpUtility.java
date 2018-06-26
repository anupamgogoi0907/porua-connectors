package com.porua.http.utility;

public class HttpUtility {

	public static String QUERY_PARAM_PREFIX = "query.param";
	public static String HEADER_NAME_PREFIX = "header.param";

	public static String sanitizePath(String path) throws Exception {
		if (path != null && !"".equals(path)) {
			path = path.replaceAll("//", "/");
			if (!path.startsWith("/")) {
				path = "/".concat(path);
			}
			return path;
		} else {
			return "/";
		}
	}

	public static String resolvePath(String pathInConnector, String pathInConfig) throws Exception {
		pathInConnector = sanitizePath(pathInConnector);
		pathInConfig = sanitizePath(pathInConfig);
		return pathInConfig.concat(pathInConnector);
	}
}
