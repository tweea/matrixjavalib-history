/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 工作日
 * 
 * @since 2005.10.28
 */
// TODO using JODA-TIME
public class Calendars {
	private Calendars() {
	}

	/**
	 * 使用已有日期生成新实例
	 * 
	 * @param date
	 *            日期
	 */
	public static GregorianCalendar create(GregorianCalendar date) {
		GregorianCalendar calendar = new GregorianCalendar(date.getTimeZone());
		calendar.setTimeInMillis(date.getTimeInMillis());
		return calendar;
	}

	/**
	 * 使用已有日期生成新实例
	 * 
	 * @param date
	 *            日期
	 */
	public static GregorianCalendar create(Date date) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		return calendar;
	}

	/**
	 * 使用日期值生成新实例
	 * 
	 * @param date
	 *            日期值
	 */
	public static GregorianCalendar create(long date) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(date);
		return calendar;
	}

	public static GregorianCalendar copy(GregorianCalendar calendar) {
		return (GregorianCalendar) calendar.clone();
	}

	public static Date newDate(int year, int month, int date, int hrs, int min, int sec) {
		return new GregorianCalendar(year, month, date, hrs, min, sec).getTime();
	}

	public static boolean isValidDate(int y, int m, int d) {
		GregorianCalendar date = new GregorianCalendar(y, m, d);
		return date.get(Calendar.YEAR) == y && date.get(Calendar.MONTH) == m && date.get(Calendar.DAY_OF_MONTH) == d;
	}

	public static boolean isLeapYear(int year) {
		return new GregorianCalendar().isLeapYear(year);
	}

	public static int getDateRange(GregorianCalendar date1, GregorianCalendar date2) {
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

	public static GregorianCalendar compositeDateTime(GregorianCalendar date, GregorianCalendar time) {
		if (date == null) {
			return getJustTime(time);
		}
		GregorianCalendar datetime = new GregorianCalendar(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		if (time != null) {
			datetime.set(Calendar.HOUR_OF_DAY, time.get(Calendar.HOUR_OF_DAY));
			datetime.set(Calendar.MINUTE, time.get(Calendar.MINUTE));
			datetime.set(Calendar.SECOND, time.get(Calendar.SECOND));
		}
		return datetime;
	}

	public static GregorianCalendar getJustDate(GregorianCalendar datetime) {
		return new GregorianCalendar(datetime.get(Calendar.YEAR), datetime.get(Calendar.MONTH), datetime.get(Calendar.DAY_OF_MONTH));
	}

	public static GregorianCalendar getJustTime(GregorianCalendar datetime) {
		GregorianCalendar time = (GregorianCalendar) datetime.clone();
		time.set(Calendar.YEAR, 1900);
		time.set(Calendar.MONTH, 1);
		time.set(Calendar.DAY_OF_MONTH, 1);
		return time;
	}

	public static long difference(int field, GregorianCalendar from, GregorianCalendar to) {
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
