/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.servlet;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.Validate;

import net.matrix.util.IterableEnumeration;
import net.matrix.web.http.HTTPs;

/**
 * Servlet 工具类.
 */
public class Servlets
{
	/**
	 * 获取客户端 UserAgent 字符串
	 */
	public static String getUserAgent(HttpServletRequest request)
	{
		return request.getHeader(HTTPs.USER_AGENT_HEADER);
	}

	/**
	 * 设置客户端缓存过期时间 的Header.
	 */
	public static void setExpiresHeader(HttpServletResponse response, long expiresSeconds)
	{
		// Http 1.0 header
		response.setDateHeader(HTTPs.EXPIRES_HEADER, System.currentTimeMillis() + expiresSeconds * 1000);
		// Http 1.1 header
		response.setHeader(HTTPs.CACHE_CONTROL_HEADER, "private, max-age=" + expiresSeconds);
	}

	/**
	 * 设置禁止客户端缓存的Header.
	 */
	public static void setDisableCacheHeader(HttpServletResponse response)
	{
		// Http 1.0 header
		response.setDateHeader(HTTPs.EXPIRES_HEADER, 1L);
		response.addHeader(HTTPs.PRAGMA_HEADER, "no-cache");
		// Http 1.1 header
		response.setHeader(HTTPs.CACHE_CONTROL_HEADER, "no-cache, no-store, max-age=0");
	}

	/**
	 * 设置LastModified Header.
	 */
	public static void setLastModifiedHeader(HttpServletResponse response, long lastModifiedDate)
	{
		response.setDateHeader(HTTPs.LAST_MODIFIED_HEADER, lastModifiedDate);
	}

	/**
	 * 根据浏览器If-Modified-Since Header, 计算文件是否已被修改.
	 * 如果无修改, checkIfModify返回false ,设置304 not modify status.
	 * @param lastModified 内容的最后修改时间.
	 */
	public static boolean checkIfModifiedSince(HttpServletRequest request, HttpServletResponse response, long lastModified)
	{
		long ifModifiedSince = request.getDateHeader(HTTPs.IF_MODIFIED_SINCE_HEADER);
		if((ifModifiedSince != -1) && (lastModified < ifModifiedSince + 1000)){
			response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			return false;
		}
		return true;
	}

	/**
	 * 设置Etag Header.
	 */
	public static void setEtag(HttpServletResponse response, String etag)
	{
		response.setHeader(HTTPs.E_TAG_HEADER, etag);
	}

	/**
	 * 根据浏览器 If-None-Match Header, 计算Etag是否已无效.
	 * 如果Etag有效, checkIfNoneMatch返回false, 设置304 not modify status.
	 * @param etag 内容的ETag.
	 */
	public static boolean checkIfNoneMatchEtag(HttpServletRequest request, HttpServletResponse response, String etag)
	{
		String headerValue = request.getHeader(HTTPs.IF_NONE_MATCH_HEADER);
		if(headerValue != null){
			boolean conditionSatisfied = false;
			if(!"*".equals(headerValue)){
				StringTokenizer commaTokenizer = new StringTokenizer(headerValue, ",");
				while(!conditionSatisfied && commaTokenizer.hasMoreTokens()){
					String currentToken = commaTokenizer.nextToken();
					if(currentToken.trim().equals(etag)){
						conditionSatisfied = true;
					}
				}
			}else{
				conditionSatisfied = true;
			}

			if(conditionSatisfied){
				response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
				response.setHeader(HTTPs.E_TAG_HEADER, etag);
				return false;
			}
		}
		return true;
	}

	/**
	 * 设置让浏览器弹出下载对话框的Header.
	 * @param fileName 下载后的文件名.
	 */
	public static void setFileDownloadHeader(HttpServletResponse response, String fileName)
	{
		try{
			// 中文文件名支持
			String encodedfileName = URLEncoder.encode(fileName, "UTF-8");
			response.setHeader(HTTPs.CONTENT_DISPOSITION_HEADER, "attachment; filename=\"" + encodedfileName + "\"");
		}catch(UnsupportedEncodingException e){
		}
	}

	/**
	 * 取得带相同前缀的Request Parameters.
	 * 返回的结果的Parameter名已去除前缀.
	 */
	public static Map<String, Object> getParametersStartingWith(HttpServletRequest request, String prefix)
	{
		Validate.notNull(request, "Request must not be null");
		if(prefix == null){
			prefix = "";
		}
		Enumeration paramNames = request.getParameterNames();
		Map<String, Object> params = new TreeMap<String, Object>();
		for(String paramName : new IterableEnumeration<String>(paramNames)){
			if("".equals(prefix) || paramName.startsWith(prefix)){
				String unprefixed = paramName.substring(prefix.length());
				String[] values = request.getParameterValues(paramName);
				if(values == null || values.length == 0){
					// Do nothing, no values found at all.
				}else if(values.length > 1){
					params.put(unprefixed, values);
				}else{
					params.put(unprefixed, values[0]);
				}
			}
		}
		return params;
	}
}
