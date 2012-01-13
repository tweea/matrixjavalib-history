/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.servlet.session;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.matrix.lang.Objects;
import net.matrix.text.DateFormatHelper;

public abstract class WebProcess
{
	private static final Logger LOG = LoggerFactory.getLogger(WebProcess.class);

	private static final String ERROR_KEY = "error_key";

	private static final String MESSAGE_KEY = "message_key";

	private static final String BACK_URI_KEY = "back_uri";

	private static final String STORE_URI_KEY = "storeuri";

	// /////////////////////////////////////////////////////////////////////////////////////
	// 消息处理方法
	// /////////////////////////////////////////////////////////////////////////////////////
	public static void addMessage(HttpServletRequest request, String msg)
	{
		StringBuffer sb = (StringBuffer)request.getAttribute(MESSAGE_KEY);
		if(sb == null){
			sb = new StringBuffer(255);
			request.setAttribute(MESSAGE_KEY, sb);
		}
		sb.append(msg);
	}

	public static String getMessage(HttpServletRequest request)
	{
		StringBuffer sb = (StringBuffer)request.getAttribute(MESSAGE_KEY);
		return sb != null ? sb.toString() : "";
	}

	public static void addError(HttpServletRequest request, String errorMsg)
	{
		StringBuffer sb = (StringBuffer)request.getAttribute(ERROR_KEY);
		if(sb == null){
			sb = new StringBuffer(255);
			request.setAttribute(ERROR_KEY, sb);
		}
		sb.append(errorMsg);
	}

	public static String getError(HttpServletRequest request)
	{
		StringBuffer sb = (StringBuffer)request.getAttribute(ERROR_KEY);
		return sb != null ? sb.toString() : "";
	}

	public static void addBackURI(HttpServletRequest request, String uri)
	{
		request.setAttribute(BACK_URI_KEY, uri);
	}

	public static String getBackURI(HttpServletRequest request)
	{
		return (String)request.getAttribute(BACK_URI_KEY);
	}

	public static void setRequestURI(HttpServletRequest request, String requestURI)
	{
		request.getSession(true).setAttribute(STORE_URI_KEY, requestURI);
	}

	public static void storeRequestURI(HttpServletRequest request)
	{
		request.getSession(true).setAttribute(STORE_URI_KEY, request.getRequestURI());
	}

	public static String getStoredRequestURI(HttpServletRequest request)
	{
		return (String)request.getSession(true).getAttribute(STORE_URI_KEY);
	}

	// /////////////////////////////////////////////////////////////////////////////////////
	// 参数获取方法
	// /////////////////////////////////////////////////////////////////////////////////////
	public static String getParameter(HttpServletRequest request, String property)
	{
		String v = request.getParameter(property);
		if(v == null)
			return "";
		return v;
	}

	public static int getIntParameter(HttpServletRequest request, String property)
	{
		String value = getParameter(request, property);
		if("".equals(value))
			return 0;
		return Integer.parseInt(value);
	}

	public static long getLongParameter(HttpServletRequest request, String property)
	{
		String value = getParameter(request, property);
		if("".equals(value))
			return 0;
		return Long.parseLong(value);
	}

	public static BigDecimal getBigDecimalParameter(HttpServletRequest request, String property)
	{
		String value = getParameter(request, property);
		if("".equals(value))
			return new BigDecimal(0);
		return new BigDecimal(value);
	}

	public static BigDecimal getBigDecimalParameter(HttpServletRequest request, String property, MathContext mc)
	{
		String value = getParameter(request, property);
		if("".equals(value))
			return new BigDecimal(0, mc);
		return new BigDecimal(value, mc);
	}

	public static GregorianCalendar getGregorianCalendarParameter(HttpServletRequest request, String property, String format)
	{
		String value = getParameter(request, property);
		if("".equals(value))
			return null;
		DateFormat df = DateFormatHelper.getFormat(format);
		GregorianCalendar gc = new GregorianCalendar(1900, 1, 1);
		try{
			gc.setTime(df.parse(value));
		}catch(ParseException e){
			LOG.warn("参数 " + property + "=" + value + " 无法解析为日期", e);
			return null;
		}
		return gc;
	}

	// /////////////////////////////////////////////////////////////////////////////////////
	// 其它
	// /////////////////////////////////////////////////////////////////////////////////////
	public static String getDisplayString(String string)
	{
		return Objects.isNull(string, "");
	}
}
