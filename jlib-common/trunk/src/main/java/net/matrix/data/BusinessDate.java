/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.data;

import java.text.ParseException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import net.matrix.text.DateFormatHelper;

/**
 * 工作日
 * @author Tweea
 * @since 2005.10.28
 */
public class BusinessDate
	extends GregorianCalendar
{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5431733259456190646L;

	/**
	 * 使用当前时间和默认时区生成新实例
	 */
	public BusinessDate()
	{
		super();
	}

	/**
	 * 使用指定日期和默认时区生成新实例
	 * @param year 年
	 * @param month 月
	 * @param dayOfMonth 日
	 */
	public BusinessDate(int year, int month, int dayOfMonth)
	{
		super(year, month, dayOfMonth);
	}

	/**
	 * 使用指定日期时间和默认时区生成新实例
	 * @param year 年
	 * @param month 月
	 * @param dayOfMonth 日
	 * @param hourOfDay 时
	 * @param minute 分
	 */
	public BusinessDate(int year, int month, int dayOfMonth, int hourOfDay, int minute)
	{
		super(year, month, dayOfMonth, hourOfDay, minute);
	}

	/**
	 * 使用指定日期时间和默认时区生成新实例
	 * @param year 年
	 * @param month 月
	 * @param dayOfMonth 日
	 * @param hourOfDay 时
	 * @param minute 分
	 * @param second 秒
	 */
	public BusinessDate(int year, int month, int dayOfMonth, int hourOfDay, int minute, int second)
	{
		super(year, month, dayOfMonth, hourOfDay, minute, second);
	}

	/**
	 * 使用当前时间、默认时区和指定地区生成新实例
	 * @param aLocale 地区
	 */
	public BusinessDate(Locale aLocale)
	{
		super(aLocale);
	}

	/**
	 * 使用当前时间、默认地区和指定时区生成新实例
	 * @param zone 时区
	 */
	public BusinessDate(TimeZone zone)
	{
		super(zone);
	}

	/**
	 * 使用当前时间、指定时区和指定地区生成新实例
	 * @param zone 时区
	 * @param aLocale 地区
	 */
	public BusinessDate(TimeZone zone, Locale aLocale)
	{
		super(zone, aLocale);
	}

	/**
	 * 使用已有日期生成新实例
	 * @param date 日期
	 */
	public BusinessDate(GregorianCalendar date)
	{
		super(date.getTimeZone());
		setTimeInMillis(date.getTimeInMillis());
	}

	/**
	 * 使用已有日期生成新实例
	 * @param date 日期
	 */
	public BusinessDate(Date date)
	{
		super();
		setTime(date);
	}

	/**
	 * 使用日期值生成新实例
	 * @param date 日期值
	 */
	public BusinessDate(long date)
	{
		super();
		setTimeInMillis(date);
	}

	/**
	 * 根据字符串构造实例。
	 * 格式为 yyyy-MM-dd'T'HH:mm:ss。
	 * @param date 日期字符串。
	 */
	public static BusinessDate parse(String date)
		throws ParseException
	{
		return parse(date, DateFormatHelper.ISO_DATETIME_FORMAT);
	}

	/**
	 * 根据字符串构造实例。
	 * @param dateString 日期字符串。
	 */
	public static BusinessDate parse(String dateString, String format)
		throws ParseException
	{
		Date date = DateFormatHelper.getFormat(format).parse(dateString);
		return new BusinessDate(date);
	}

	@Override
	public String toString()
	{
		return DateFormatHelper.format(this, DateFormatHelper.ISO_DATETIME_FORMAT);
	}

	/**
	 * 格式化为字符串
	 * @param format 格式
	 * @return 格式化结果
	 */
	public String toString(String format)
	{
		return DateFormatHelper.format(this, format);
	}

	/**
	 * 转换日期字符串
	 * @param year 年。
	 * @param month 月。
	 * @param day 日。
	 * @return 目标字符串，形式为 yyyy(year)MM(month)dd(date)。
	 */
	public String toDisplayString(String year, String month, String day)
	{
		return toString("yyyy") + year + toString("MM") + month + toString("dd") + day;
	}

	/**
	 * 转换日期字符串
	 * @return 目标字符串，形式为 yyyy-MM-dd。
	 */
	public String toDisplayString()
	{
		return toString(DateFormatHelper.ISO_DATE_FORMAT);
	}

	/**
	 * 转换日期字符串
	 * @return 目标字符串，形式为 yyyy年MM月dd日。
	 */
	public String toChineseString()
	{
		return toDisplayString("年", "月", "日");
	}
}
