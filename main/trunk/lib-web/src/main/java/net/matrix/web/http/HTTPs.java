/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.web.http;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

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
	 * 组合参数生成查询字符串的参数部分，并在参数名上加上前缀。
	 * 
	 * @param params
	 *            参数
	 * @param prefix
	 *            前缀
	 * @return 参数字符串
	 */
	public static String encodeParameterStringWithPrefix(final Map<String, Object> params, final String prefix) {
		StringBuilder queryStringBuilder = new StringBuilder();

		Iterator<Entry<String, Object>> it = params.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Object> entry = it.next();
			queryStringBuilder.append(prefix).append(entry.getKey()).append("=").append(entry.getValue());
			if (it.hasNext()) {
				queryStringBuilder.append("&");
			}
		}
		return queryStringBuilder.toString();
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
