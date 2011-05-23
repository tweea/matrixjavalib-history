/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.web.html;

public abstract class HTMLUtil
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

	/**
	 * 转换一段 HTML 代码 将一段 HTML 代码的标签替换为转移字符，避免跨站脚本等问题。 然后分行，设定每行的最大宽度。
	 * @param input 原始 HTML 代码
	 * @param maxLength 每行最大长度
	 * @return 结果字符串
	 */
	public static String formatInputHTML(String input, int maxLength)
	{
		String texts[] = input.replaceAll("<br/>", "\n").replaceAll("&lt;", "<").replaceAll("&gt;", ">").split("\n");
		StringBuffer buffer = new StringBuffer();
		for(int j = 0; j < texts.length; j++){
			String text = texts[j];
			for(int i = 0; i * maxLength < text.length(); i++){
				if(((i + 1) * maxLength) < text.length()){
					buffer.append(text.substring(i * maxLength, (i + 1) * maxLength)).append("\n");
				}else{
					buffer.append(text.substring(i * maxLength)).append("\n");
				}
			}
		}
		buffer.setLength(buffer.length() - 1);
		return buffer.toString().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br/>");
	}
}
