/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.web.http;

import org.apache.commons.codec.binary.Base64;


/**
 * HTTP 工具类。
 */
public final class HTTPs {
	/**
	 * 阻止实例化。
	 */
	private HTTPs() {
	}

	/**
	 * 客户端对 Http Basic 验证的 Header 进行编码。
	 * 
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return 编码字符串
	 */
	public static String encodeHttpBasic(final String username, final String password) {
		String encode = username + ":" + password;
		return "Basic " + Base64.encodeBase64String(encode.getBytes());
	}
}
