/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.lang;

/**
 * 字符串实用方法
 * @since 2006.05.20
 */
public class Strings
{
	private Strings()
	{
	}

	/**
	 * 在目标字符串中按定界符查找，将定界字符串中间的字符串的所有 replaceSrc 替换为 replaceDest。
	 * @param source 源字符串
	 * @param startStr 定界开始
	 * @param endStr 定界结束
	 * @param replaceSrc 要替换的字符串
	 * @param replaceDest 替换成的字符串
	 * @return 结果
	 */
	public static String replaceAllBetweenDelimiter(String source, String startStr, String endStr, String replaceSrc, String replaceDest)
	{
		StringBuffer str = new StringBuffer(source);
		for(int procPointer = 0; procPointer >= 0;){
			int start = str.indexOf(startStr, procPointer);
			int end = -1;
			if(start >= 0){
				end = str.indexOf(endStr, start);
			}
			if(end < 0){
				procPointer = -1;
			}else{
				String midStr = str.substring(start + startStr.length(), end).replaceAll(replaceSrc, replaceDest);
				str.replace(start + startStr.length(), end, midStr);
				procPointer = start + startStr.length() + midStr.length() + endStr.length();
			}
		}
		return str.toString();
	}
}
