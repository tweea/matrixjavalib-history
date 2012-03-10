/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.web.html;

import java.io.UnsupportedEncodingException;

public abstract class HTMLUtil
{
	public static String fitToLength(String str, int length)
	{
		StringBuilder sb = new StringBuilder();
		if(str == null){
			for(int i = 0; i < length; i++)
				sb.append("&nbsp;");
			return sb.toString();
		}
		int len = str.length();
		if(len >= length)
			return str;
		else{
			sb.append(str);
			for(int i = 0; i < length - len; i++)
				sb.append("&nbsp;");
			return sb.toString();
		}
	}

	/**
	 * 转换一段 HTML 代码 将一段 HTML 代码的标签替换为转移字符，避免跨站脚本等问题。 然后分行，设定每行的最大宽度。
	 * @param input 原始 HTML 代码
	 * @param maxLength 每行最大长度
	 * @return 结果字符串
	 */
	public static String formatInputHTML(String input, int maxLength)
	{
		String text;
		String texts[] = input.replaceAll("<br/>", "\n").replaceAll("&lt;", "<").replaceAll("&gt;", ">").split("\n");
		StringBuffer buffer = new StringBuffer();
		for(int j = 0; j < texts.length; j++){
			text = texts[j];
			for(int i = 0; i * maxLength < text.length(); i++){
				if(((i + 1) * maxLength) < text.length())
					buffer.append(text.substring(i * maxLength, (i + 1) * maxLength)).append("\n");
				else
					buffer.append(text.substring(i * maxLength)).append("\n");
			}
		}
		buffer.setLength(buffer.length() - 1);
		return buffer.toString().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br/>");
	}

	/**
	 * 将文件名中的汉字转为UTF8编码的串,以便下载时能正确显示另存的文件名. 纵横软件制作中心雨亦奇2003.08.01
	 * @param s 原文件名
	 * @return 重新编码后的文件名
	 */
	public static String encodeFormURL(String s)
	{
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < s.length(); i++){
			char c = s.charAt(i);
			if(c >= 0 && c <= 255){
				sb.append(c);
			}else{
				byte[] b;
				try{
					b = Character.toString(c).getBytes("utf-8");
				}catch(Exception ex){
					System.out.println(ex);
					b = new byte[0];
				}
				for(int j = 0; j < b.length; j++){
					int k = b[j];
					if(k < 0)
						k += 256;
					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return sb.toString();
	}

	public static String encodeToHexString(String s)
	{
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < s.length(); i++){
			char c = s.charAt(i);
			byte[] b;
			try{
				b = Character.toString(c).getBytes("utf-8");
			}catch(Exception ex){
				System.out.println(ex);
				b = new byte[0];
			}
			sb.append("x");
			for(int j = 0; j < b.length; j++){
				int k = b[j];
				if(k < 0)
					k += 256;
				sb.append(Integer.toHexString(k).toUpperCase());
			}
		}
		return sb.toString();
	}

	public static String decodeFromHexString(String s)
	{
		StringBuffer sb = new StringBuffer();
		String[] ss = s.split("x");
		for(String sss : ss){
			if(sss.length() == 0)
				continue;
			byte[] b = new byte[sss.length() / 2];
			for(int i = 0; i < b.length; i++)
				b[i] = (byte)Integer.valueOf(sss.substring(i * 2, (i + 1) * 2), 16).intValue();
			try{
				sb.append(new String(b, "utf-8"));
			}catch(UnsupportedEncodingException e){
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}
