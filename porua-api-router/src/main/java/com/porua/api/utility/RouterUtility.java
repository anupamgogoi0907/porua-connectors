package com.porua.api.utility;

public class RouterUtility {
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
		String path = pathInConfig.concat(pathInConnector);
		return sanitizePath(path);
	}

	public static Object isNumeric(String str) {
		Integer intObj = isInteger(str);
		if (intObj != null) {
			return intObj;
		}
		Double dblObj = isDouble(str);
		if (dblObj != null) {
			return dblObj;
		}
		Float fltObj = isFloat(str);
		if (fltObj != null) {
			return fltObj;
		}
		return null;
	}

	public static Integer isInteger(String str) {
		try {
			Integer value = Integer.parseInt(str);
			return value;
		} catch (Exception e) {
			return null;
		}
	}

	public static Double isDouble(String str) {
		try {
			Double value = Double.parseDouble(str);
			return value;
		} catch (Exception e) {
			return null;
		}
	}

	public static Float isFloat(String str) {
		try {
			Float value = Float.parseFloat(str);
			return value;
		} catch (Exception e) {
			return null;
		}
	}
}
