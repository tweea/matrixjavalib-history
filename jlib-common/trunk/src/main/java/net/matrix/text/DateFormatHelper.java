/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import net.matrix.util.Calendars;

/**
 * 日期工具方法
 * @author T.A.Tang
 * @since 2005.03.09
 */
public class DateFormatHelper
{
	/**
	 * ISO 标准中日期时间格式
	 */
	public static final String ISO_DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

	/**
	 * ISO 标准中日期格式
	 */
	public static final String ISO_DATE_FORMAT = "yyyy-MM-dd";

	/**
	 * ISO 标准中时间格式
	 */
	public static final String ISO_TIME_FORMAT = "'T'HH:mm:ss";

	private static final ThreadLocal<Map<String, DateFormat>> FORMATS = new ThreadLocal<Map<String, DateFormat>>();

	private DateFormatHelper()
	{
	}

	/**
	 * 获得一个 DateFormat 实例
	 * @param format 日期格式。
	 * @return 一个 DateFormat 实例。
	 */
	public static DateFormat getFormat(String format)
	{
		Map<String, DateFormat> localFormats = FORMATS.get();
		if(localFormats == null){
			localFormats = new HashMap<String, DateFormat>();
			FORMATS.set(localFormats);
		}
		DateFormat formatObject = localFormats.get(format);
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

	/**
	 * 转换日期值到字符串
	 * @param time 日期值。
	 * @return 字符串，形式见 SimpleDateFormat。
	 * @see java.text.SimpleDateFormat
	 */
	public static String formatTime(long time, String format)
	{
		return format(new Date(time), format);
	}

	/**
	 * 根据字符串构造实例。
	 * 格式为 yyyy-MM-dd'T'HH:mm:ss。
	 * @param date 日期字符串。
	 */
	public static GregorianCalendar parse(String date)
		throws ParseException
	{
		return parse(date, DateFormatHelper.ISO_DATETIME_FORMAT);
	}

	/**
	 * 根据字符串构造实例。
	 * @param dateString 日期字符串。
	 */
	public static GregorianCalendar parse(String dateString, String format)
		throws ParseException
	{
		Date date = DateFormatHelper.getFormat(format).parse(dateString);
		return Calendars.create(date);
	}

	public static String toString(GregorianCalendar date)
	{
		return DateFormatHelper.format(date, DateFormatHelper.ISO_DATETIME_FORMAT);
	}

	/**
	 * 格式化为字符串
	 * @param format 格式
	 * @return 格式化结果
	 */
	public static String toString(GregorianCalendar date, String format)
	{
		return DateFormatHelper.format(date, format);
	}

	/**
	 * 转换日期字符串
	 * @param year 年。
	 * @param month 月。
	 * @param day 日。
	 * @return 目标字符串，形式为 yyyy(year)MM(month)dd(date)。
	 */
	public static String toDisplayString(GregorianCalendar date, String year, String month, String day)
	{
		return toString(date, "yyyy") + year + toString(date, "MM") + month + toString(date, "dd") + day;
	}

	/**
	 * 转换日期字符串
	 * @return 目标字符串，形式为 yyyy-MM-dd。
	 */
	public static String toDisplayString(GregorianCalendar date)
	{
		return toString(date, DateFormatHelper.ISO_DATE_FORMAT);
	}

	/**
	 * 转换日期字符串
	 * @return 目标字符串，形式为 yyyy年MM月dd日。
	 */
	public static String toChineseString(GregorianCalendar date)
	{
		return toDisplayString(date, "年", "月", "日");
	}
}
