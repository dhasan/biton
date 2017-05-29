package com.tr.biton.app;

import javax.servlet.http.HttpServletRequest;

public class ELUtils {
	public static String getURLWithContextPath(HttpServletRequest request) {
		return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	}
}
