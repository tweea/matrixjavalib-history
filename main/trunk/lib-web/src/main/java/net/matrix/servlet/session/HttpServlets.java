/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.servlet.session;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import net.matrix.text.DateFormatHelper;
import net.matrix.util.IterableEnumeration;

public final class HttpServlets {
	private static final String ERROR_KEY = "error_key";

	private static final String MESSAGE_KEY = "message_key";

	private static final String BACK_URI_KEY = "back_uri";

	private static final String STORE_URI_KEY = "store_uri";

	/**
	 * 阻止实例化。
	 */
	private HttpServlets() {
	}

	// /////////////////////////////////////////////////////////////////////////////////////
	// 消息处理方法
	// /////////////////////////////////////////////////////////////////////////////////////
	public static void addMessage(HttpServletRequest request, String msg) {
		StringBuilder sb = (StringBuilder) request.getAttribute(MESSAGE_KEY);
		if (sb == null) {
			sb = new StringBuilder();
			request.setAttribute(MESSAGE_KEY, sb);
		}
		sb.append(msg);
	}

	public static String getMessage(HttpServletRequest request) {
		StringBuilder sb = (StringBuilder) request.getAttribute(MESSAGE_KEY);
		if (sb == null) {
			return "";
		}
		return sb.toString();
	}

	public static void addError(HttpServletRequest request, String errorMsg) {
		StringBuilder sb = (StringBuilder) request.getAttribute(ERROR_KEY);
		if (sb == null) {
			sb = new StringBuilder();
			request.setAttribute(ERROR_KEY, sb);
		}
		sb.append(errorMsg);
	}

	public static String getError(HttpServletRequest request) {
		StringBuilder sb = (StringBuilder) request.getAttribute(ERROR_KEY);
		if (sb == null) {
			return "";
		}
		return sb.toString();
	}

	public static void setBackURI(HttpServletRequest request, String uri) {
		request.setAttribute(BACK_URI_KEY, uri);
	}

	public static String getBackURI(HttpServletRequest request) {
		return (String) request.getAttribute(BACK_URI_KEY);
	}

	public static void setRequestURI(HttpServletRequest request, String requestURI) {
		request.getSession(true).setAttribute(STORE_URI_KEY, requestURI);
	}

	public static void storeRequestURI(HttpServletRequest request) {
		request.getSession(true).setAttribute(STORE_URI_KEY, request.getRequestURI());
	}

	public static String getStoredRequestURI(HttpServletRequest request) {
		return (String) request.getSession(true).getAttribute(STORE_URI_KEY);
	}

	// /////////////////////////////////////////////////////////////////////////////////////
	// 参数获取方法
	// /////////////////////////////////////////////////////////////////////////////////////
	public static String getParameter(HttpServletRequest request, String property) {
		String value = request.getParameter(property);
		if (value == null) {
			return "";
		}
		return value;
	}

	public static int getIntParameter(HttpServletRequest request, String property) {
		String value = request.getParameter(property);
		if (StringUtils.isBlank(value)) {
			return 0;
		}
		return Integer.parseInt(value);
	}

	public static long getLongParameter(HttpServletRequest request, String property) {
		String value = request.getParameter(property);
		if (StringUtils.isBlank(value)) {
			return 0L;
		}
		return Long.parseLong(value);
	}

	public static BigDecimal getBigDecimalParameter(HttpServletRequest request, String property) {
		String value = request.getParameter(property);
		if (StringUtils.isBlank(value)) {
			return BigDecimal.ZERO;
		}
		return new BigDecimal(value);
	}

	public static BigDecimal getBigDecimalParameter(HttpServletRequest request, String property, MathContext mc) {
		String value = request.getParameter(property);
		if (StringUtils.isBlank(value)) {
			return new BigDecimal(0, mc);
		}
		return new BigDecimal(value, mc);
	}

	public static GregorianCalendar getGregorianCalendarParameter(HttpServletRequest request, String property, String format) {
		String value = request.getParameter(property);
		if (StringUtils.isBlank(value)) {
			return null;
		}
		return (GregorianCalendar) DateFormatHelper.parse(value, format);
	}

	/**
	 * 取得带相同前缀的 Request Parameters, copy from spring。
	 * 返回的结果的 Parameter 名已去除前缀。
	 */
	public static Map<String, Object> getParametersStartingWith(HttpServletRequest request, String prefix) {
		if (prefix == null) {
			prefix = "";
		}
		Enumeration paramNames = request.getParameterNames();
		Map<String, Object> params = new TreeMap<String, Object>();
		for (String paramName : new IterableEnumeration<String>(paramNames)) {
			if ("".equals(prefix) || paramName.startsWith(prefix)) {
				String unprefixed = paramName.substring(prefix.length());
				String[] values = request.getParameterValues(paramName);
				if (values == null || values.length == 0) {
					// Do nothing, no values found at all.
				} else if (values.length > 1) {
					params.put(unprefixed, values);
				} else {
					params.put(unprefixed, values[0]);
				}
			}
		}
		return params;
	}
}
