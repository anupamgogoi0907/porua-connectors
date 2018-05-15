package com.porua.core.utility;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class PoruaUtility {

	public static Object convertData(String data) {
		if (isInteger(data)) {
			return Integer.parseInt((String) data);
		} else if (isLong(data)) {
			return Long.parseLong(data);
		} else if (isDouble(data)) {
			return Long.parseLong(data);
		} else {
			return data;
		}
	}

	public static Class<?> findDataType(String data) {
		if (isInteger(data)) {
			return Integer.class;
		} else if (isLong(data)) {
			return Long.class;
		} else if (isDouble(data)) {
			return Long.class;
		} else {
			return String.class;
		}
	}

	public static boolean isInteger(String data) {
		try {
			Integer.parseInt(data);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isLong(String data) {
		try {
			Long.parseLong(data);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isDouble(String data) {
		try {
			Double.parseDouble(data);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static String convertStreamToString(InputStream inputStream) {
		String res = "";
		try {
			ByteArrayOutputStream result = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int length;
			while ((length = inputStream.read(buffer)) != -1) {
				result.write(buffer, 0, length);
			}
			res = result.toString("UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;

	}
}
