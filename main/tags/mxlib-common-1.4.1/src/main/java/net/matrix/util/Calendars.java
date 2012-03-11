/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

/**
 * 日期工具类。
 */
public final class Calendars {
	/**
	 * 阻止实例化。
	 */
	private Calendars() {
	}

	/**
	 * 使用已有日期生成新实例。
	 * 
	 * @param date
	 *            日期
	 * @return 新实例
	 */
	public static GregorianCalendar create(final GregorianCalendar date) {
		DateTime dateTime = new DateTime(date);
		return dateTime.toGregorianCalendar();
	}

	/**
	 * 使用已有日期生成新实例。
	 * 
	 * @param date
	 *            日期
	 * @return 新实例
	 */
	public static GregorianCalendar create(final Date date) {
		DateTime dateTime = new DateTime(date);
		return dateTime.toGregorianCalendar();
	}

	/**
	 * 使用日期值生成新实例。
	 * 
	 * @param date
	 *            日期值
	 * @return 新实例
	 */
	public static GregorianCalendar create(final long date) {
		DateTime dateTime = new DateTime(date);
		return dateTime.toGregorianCalendar();
	}

	/**
	 * 建立 Date 实例。
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月 1-12
	 * @param date
	 *            日
	 * @param hrs
	 *            时
	 * @param min
	 *            分
	 * @param sec
	 *            秒
	 * @return 日期
	 */
	public static Date newDate(final int year, final int month, final int date, final int hrs, final int min, final int sec) {
		return new DateTime(year, month, date, hrs, min, sec).toDate();
	}

	/**
	 * 校验日期。
	 * 
	 * @param y
	 *            年
	 * @param m
	 *            月 1-12
	 * @param d
	 *            日
	 * @return true 日期正确
	 */
	public static boolean isValidDate(final int y, final int m, final int d) {
		GregorianCalendar date = new GregorianCalendar(y, m - 1, d);
		return date.get(Calendar.YEAR) == y && date.get(Calendar.MONTH) == m - 1 && date.get(Calendar.DAY_OF_MONTH) == d;
	}

	public static boolean isLeapYear(final int year) {
		return new GregorianCalendar().isLeapYear(year);
	}

	public static int getDateRange(final GregorianCalendar date1, final GregorianCalendar date2) {
		int y1 = date1.get(Calendar.YEAR);
		int d1 = date1.get(Calendar.DAY_OF_MONTH);
		int y2 = date2.get(Calendar.YEAR);
		int m2 = date2.get(Calendar.MONTH);
		if (y1 == y2) {
			return date1.get(Calendar.DAY_OF_YEAR) - date2.get(Calendar.DAY_OF_YEAR);
		}
		int count = 0;
		for (int i = y1 + 1; i < y2; i++) {
			count += date1.isLeapYear(i) ? 366 : 365;
		}
		return (count + date2.get(Calendar.DAY_OF_YEAR) + (date1.isLeapYear(y1) ? 366 : 365)) - new GregorianCalendar(y1, m2, d1).get(Calendar.DAY_OF_YEAR);
	}

	public static GregorianCalendar compositeDateTime(final GregorianCalendar date, final GregorianCalendar time) {
		if (date == null && time != null) {
			return getJustTime(time);
		} else if (date != null && time == null) {
			return getJustDate(date);
		}
		return LocalDate.fromCalendarFields(date).toDateTime(LocalTime.fromCalendarFields(time)).toGregorianCalendar();
	}

	public static GregorianCalendar getJustDate(final GregorianCalendar datetime) {
		return LocalDate.fromCalendarFields(datetime).toDateTimeAtStartOfDay().toGregorianCalendar();
	}

	public static GregorianCalendar getJustTime(final GregorianCalendar datetime) {
		DateTime date = new DateTime(1900, 1, 1, 0, 0);
		return LocalTime.fromCalendarFields(datetime).toDateTime(date).toGregorianCalendar();
	}

	public static long difference(final int field, final GregorianCalendar from, final GregorianCalendar to) {
		long fromTime = from.getTimeInMillis();
		long toTime = to.getTimeInMillis();
		long diff = toTime - fromTime;
		switch (field) {
		case Calendar.DAY_OF_MONTH:
			diff /= 1000 * 60 * 60 * 24;
			return diff;
		case Calendar.HOUR_OF_DAY:
			diff /= 1000 * 60 * 60;
			return diff;
		case Calendar.MINUTE:
			diff /= 1000 * 60;
			return diff;
		case Calendar.SECOND:
			diff /= 1000;
			return diff;
		default:
			throw new UnsupportedOperationException();
		}
	}
}
