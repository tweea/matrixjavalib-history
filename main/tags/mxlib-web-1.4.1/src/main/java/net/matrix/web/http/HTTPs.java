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
	public static final String USER_AGENT_HEADER = "user-agent";

	public static final String AUTHENTICATION_HEADER = "Authorization";

	public static final String EXPIRES_HEADER = "Expires";

	public static final String PRAGMA_HEADER = "Pragma";

	public static final String CACHE_CONTROL_HEADER = "Cache-Control";

	public static final String LAST_MODIFIED_HEADER = "Last-Modified";

	public static final String IF_MODIFIED_SINCE_HEADER = "If-Modified-Since";

	public static final String E_TAG_HEADER = "ETag";

	public static final String IF_NONE_MATCH_HEADER = "If-None-Match";

	public static final String CONTENT_DISPOSITION_HEADER = "Content-Disposition";

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
