/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.text;

import java.text.NumberFormat;

/**
 * 数字工具方法
 * @author T.A.Tang
 * @since 2006-1-19
 */
public class NumberFormatHelper
{
	private static final ThreadLocal<NumberFormat> LOCAL_FORMAT = new ThreadLocal<NumberFormat>();

	private NumberFormatHelper()
	{
	}

	public static NumberFormat getDateFormat()
	{
		NumberFormat df = LOCAL_FORMAT.get();
		if(df == null){
			df = NumberFormat.getInstance();
			LOCAL_FORMAT.set(df);
		}
		return df;
	}
}
