/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 日期工具方法
 * @author T.A.Tang
 * @since 2005.03.09
 */
public abstract class DateFormatHelper
{
	public final static String ISO_DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

	public final static String ISO_DATE_FORMAT = "yyyy-MM-dd";

	public final static String ISO_TIME_FORMAT = "'T'HH:mm:ss";

	private static final ThreadLocal<Map<String, SimpleDateFormat>> formats = new ThreadLocal<Map<String, SimpleDateFormat>>();

	/**
	 * 获得一个 SimpleDateFormat 实例
	 * @param format 日期格式。
	 * @return 一个 SimpleDateFormat 实例。
	 */
	public static SimpleDateFormat getFormat(String format)
	{
		Map<String, SimpleDateFormat> localFormats = formats.get();
		if(localFormats == null){
			localFormats = new HashMap<String, SimpleDateFormat>();
			formats.set(localFormats);
		}
		SimpleDateFormat formatObject = localFormats.get(format);
		if(formatObject == null){
			formatObject = new SimpleDateFormat(format);
			localFormats.put(format, formatObject);
		}
		return formatObject;
	}

	/**
	 * 转换日期到字符串
	 * @param date 日期。
	 * @return 字符串，形式见 SimpleDateFormat。
	 * @see java.text.SimpleDateFormat
	 */
	public static String format(Date date, String format)
	{
		return getFormat(format).format(date);
	}

	/**
	 * 转换日期到字符串
	 * @param date 日期。
	 * @return 字符串，形式见 SimpleDateFormat。
	 * @see java.text.SimpleDateFormat
	 */
	public static String format(Calendar date, String format)
	{
		return format(date.getTime(), format);
	}

	public static String formatTime(long time, String format)
	{
		return format(new Date(time), format);
	}
}
