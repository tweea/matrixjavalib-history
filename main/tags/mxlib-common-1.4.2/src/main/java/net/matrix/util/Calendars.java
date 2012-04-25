/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.util;

import org.joda.time.DateTime;
import org.joda.time.IllegalFieldValueException;

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
	 * 校验日期。
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月 1-12
	 * @param day
	 *            日
	 * @return true 日期正确
	 */
	public static boolean isValidDate(final int year, final int month, final int day) {
		try {
			new DateTime(year, month, day, 0, 0);
			return true;
		} catch (IllegalFieldValueException e) {
			return false;
		}
	}

	/**
	 * 判断是否闰年。
	 * 
	 * @param year
	 *            年份
	 * @return true 是闰年
	 */
	public static boolean isLeapYear(final int year) {
		return new DateTime(year, 1, 1, 0, 0).year().isLeap();
	}
}
