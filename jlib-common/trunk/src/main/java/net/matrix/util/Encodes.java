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
 * 封装各种格式的编码解码工具类.
 * 1.自行编写的，将long进行base62编码以缩短其长度
 * 2.JDK提供的URLEncoder
 */
public class Encodes
{
	private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

	private static final String DEFAULT_URL_ENCODING = "UTF-8";

	private Encodes()
	{
	}

	/**
	 * Base62(0_9A_Za_z)编码数字, long->String.
	 */
	public static String encodeBase62(long num)
	{
		return alphabetEncode(num, 62);
	}

	/**
	 * Base62(0_9A_Za_z)解码数字, String->long.
	 */
	public static long decodeBase62(String str)
	{
		return alphabetDecode(str, 62);
	}

	private static String alphabetEncode(long num, int base)
	{
		num = Math.abs(num);
		StringBuilder sb = new StringBuilder();
		for(; num > 0; num /= base){
			sb.append(ALPHABET.charAt((int)(num % base)));
		}

		return sb.toString();
	}

	private static long alphabetDecode(String str, int base)
	{
		Validate.notBlank(str);

		long result = 0;
		for(int i = 0; i < str.length(); i++){
			result += ALPHABET.indexOf(str.charAt(i)) * Math.pow(base, i);
		}

		return result;
	}

	/**
	 * URL 编码, Encode默认为UTF-8.
	 * @throws UnsupportedEncodingException 编码错误
	 */
	public static String urlEncode(String part)
		throws UnsupportedEncodingException
	{
		return URLEncoder.encode(part, DEFAULT_URL_ENCODING);
	}

	/**
	 * URL 解码, Encode默认为UTF-8.
	 * @throws UnsupportedEncodingException 编码错误
	 */
	public static String urlDecode(String part)
		throws UnsupportedEncodingException
	{
		return URLDecoder.decode(part, DEFAULT_URL_ENCODING);
	}
}
