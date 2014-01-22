/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.servlet.session;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

/**
 * 在会话中设置标示。
 * 
 * @since 2005.11.10
 */
public final class Tokens {
	/**
	 * 阻止实例化。
	 */
	private Tokens() {
	}

	public static String generateToken(HttpServletRequest request, String key) {
		String token = UUID.randomUUID().toString();
		request.getSession(true).setAttribute(key, token);
		return token;
	}

	public static String getToken(HttpServletRequest request, String key) {
		return (String) request.getSession(true).getAttribute(key);
	}

	public static boolean isTokenValid(HttpServletRequest request, String key) {
		String token = request.getParameter(key);
		if (StringUtils.isBlank(token)) {
			return false;
		}
		String session = (String) request.getSession(true).getAttribute(key);
		return token.equals(session);
	}

	public static void removeToken(HttpServletRequest request, String key) {
		request.getSession(true).removeAttribute(key);
	}
}
