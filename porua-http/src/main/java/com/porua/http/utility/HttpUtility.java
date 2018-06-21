package com.porua.http.utility;

public class HttpUtility {

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
}
