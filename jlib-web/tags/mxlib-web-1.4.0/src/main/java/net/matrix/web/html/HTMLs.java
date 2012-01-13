/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.web.html;

public abstract class HTMLs
{
	public static String fitToLength(String str, int length)
	{
		StringBuilder sb = new StringBuilder();
		if(str == null){
			for(int i = 0; i < length; i++){
				sb.append("&nbsp;");
			}
			return sb.toString();
		}
		int len = str.length();
		if(len >= length){
			return str;
		}else{
			sb.append(str);
			for(int i = 0; i < length - len; i++){
				sb.append("&nbsp;");
			}
			return sb.toString();
		}
	}
}
