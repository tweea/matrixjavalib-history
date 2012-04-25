/*
 * $Id$
 * Copyright(C) 2011 Matrix
 * All right reserved.
 */
package net.matrix.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.commons.lang3.Validate;

/**
 * 封装各种格式的编码解码工具类。
 * 1.自行编写的，将 long 进行 base62 编码以缩短其长度
 * 2.JDK 提供的 URLEncoder
 */
public final class Encodes {
	/**
	 * Base 编码字母表。
	 */
	private static final String BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

	/**
	 * 默认 URL 编码。
	 */
	private static final String DEFAULT_URL_ENCODING = "UTF-8";

	/**
	 * 阻止实例化。
	 */
	private Encodes() {
	}

	/**
	 * Base62(0_9A_Za_z) 编码数字，long->String。
	 * 
	 * @param num
	 *            数字
	 * @return 编码结果
	 */
	public static String encodeBase62(final long num) {
		long index = Math.abs(num);
		StringBuilder sb = new StringBuilder();
		for (; index > 0; index /= 62) {
			sb.append(BASE62.charAt((int) (index % 62)));
		}

		return sb.toString();
	}

	/**
	 * Base62(0_9A_Za_z) 解码数字，String->long。
	 * 
	 * @param str
	 *            编码结果
	 * @return 数字
	 */
	public static long decodeBase62(final String str) {
		Validate.notBlank(str);

		long result = 0;
		for (int i = 0; i < str.length(); i++) {
			result += BASE62.indexOf(str.charAt(i)) * Math.pow(62, i);
		}

		return result;
	}

	/**
	 * URL 编码，默认编码为 UTF-8。
	 * 
	 * @throws UnsupportedEncodingException
	 *             编码错误
	 */
	public static String urlEncode(final String part)
		throws UnsupportedEncodingException {
		return URLEncoder.encode(part, DEFAULT_URL_ENCODING);
	}

	/**
	 * URL 解码，默认编码为 UTF-8。
	 * 
	 * @throws UnsupportedEncodingException
	 *             编码错误
	 */
	public static String urlDecode(final String part)
		throws UnsupportedEncodingException {
		return URLDecoder.decode(part, DEFAULT_URL_ENCODING);
	}
}
