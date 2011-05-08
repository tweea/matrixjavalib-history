/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.servlet.session;

import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;
import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.matrix.data.BusinessDate;
import net.matrix.text.DateFormatHelper;
import net.matrix.util.ObjectUtil;

public abstract class WebProcess
{
	private static final Log LOG = LogFactory.getLog(WebProcess.class);

	private final static String ERROR_KEY = "error_key";

	private final static String MESSAGE_KEY = "message_key";

	private final static String BACK_URI_KEY = "back_uri";

	private final static String STORE_URI_KEY = "storeuri";

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
		return ObjectUtil.isNull(string, "");
	}

	public static <T> T fillObject(Class<T> tableClass, HttpServletRequest request)
		throws Exception
	{
		try{
			Constructor<T> ct = tableClass.getConstructor(new Class[]{});
			T retobj = ct.newInstance(new Object[]{});
			PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(tableClass);
			for(PropertyDescriptor pd : pds){
				String fieldName = pd.getName();
				String value = request.getParameter(fieldName);
				if(value == null)
					continue;
				PropertyEditor pe = pd.createPropertyEditor(retobj);
				String fieldType = pd.getPropertyType().getSimpleName();
				if("int".equals(fieldType)){
					pe.setValue(Integer.parseInt(value));
				}else if("long".equals(fieldType)){
					pe.setValue(new Long(value));
				}else if("float".equals(fieldType)){
					pe.setValue(Float.parseFloat(value));
				}else if("Calendar".equals(fieldType)){
					pe.setValue(BusinessDate.parse(value, DateFormatHelper.ISO_DATE_FORMAT));
				}else if("GregorianCalendar".equals(fieldType)){
					pe.setValue(BusinessDate.parse(value, DateFormatHelper.ISO_DATE_FORMAT));
				}else if("BigDecimal".equals(fieldType)){
					pe.setValue(new BigDecimal(value));
				}else{
					pe.setValue(value);
				}
			} // end for
			return retobj;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
