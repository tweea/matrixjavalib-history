/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.servlet;

import java.io.UnsupportedEncodingException;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.matrix.util.Encodes;
import net.matrix.web.http.HTTPs;

/**
 * Servlet 工具类。
 */
public final class Servlets {
	/**
	 * 日志记录器。
	 */
	private static final Logger LOG = LoggerFactory.getLogger(Servlets.class);

	/**
	 * 阻止实例化。
	 */
	private Servlets() {
	}

	/**
	 * 获取客户端 UserAgent 字符串。
	 */
	public static String getUserAgent(final HttpServletRequest request) {
		return request.getHeader(HTTPs.USER_AGENT_HEADER);
	}

	/**
	 * 设置客户端缓存过期时间的 Header。
	 */
	public static void setExpiresHeader(final HttpServletResponse response, final long expiresSeconds) {
		// Http 1.0 header, set a fix expires date.
		response.setDateHeader(HTTPs.EXPIRES_HEADER, System.currentTimeMillis() + expiresSeconds * 1000);
		// Http 1.1 header, set a time after now.
		response.setHeader(HTTPs.CACHE_CONTROL_HEADER, "private, max-age=" + expiresSeconds);
	}

	/**
	 * 设置禁止客户端缓存的 Header。
	 */
	public static void setNoCacheHeader(final HttpServletResponse response) {
		// Http 1.0 header
		response.setDateHeader(HTTPs.EXPIRES_HEADER, 1L);
		response.addHeader(HTTPs.PRAGMA_HEADER, "no-cache");
		// Http 1.1 header
		response.setHeader(HTTPs.CACHE_CONTROL_HEADER, "no-cache, no-store, max-age=0");
	}

	/**
	 * 设置 LastModified Header。
	 */
	public static void setLastModifiedHeader(final HttpServletResponse response, final long lastModifiedDate) {
		response.setDateHeader(HTTPs.LAST_MODIFIED_HEADER, lastModifiedDate);
	}

	/**
	 * 根据浏览器 If-Modified-Since Header，计算文件是否已被修改。
	 * 如果无修改，返回 false，设置 304 not modify status。
	 * 
	 * @param lastModified
	 *            内容的最后修改时间
	 */
	public static boolean checkIfModifiedSince(final HttpServletRequest request, final HttpServletResponse response, final long lastModified) {
		long ifModifiedSince = request.getDateHeader(HTTPs.IF_MODIFIED_SINCE_HEADER);
		if ((ifModifiedSince != -1) && (lastModified < ifModifiedSince + 1000)) {
			response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			return false;
		}
		return true;
	}

	/**
	 * 设置 ETag Header。
	 */
	public static void setEtag(final HttpServletResponse response, final String etag) {
		response.setHeader(HTTPs.E_TAG_HEADER, etag);
	}

	/**
	 * 根据浏览器 If-None-Match Header，计算 ETag 是否已失效。
	 * 如果 ETag 有效，返回 false，设置 304 not modify status。
	 * 
	 * @param etag
	 *            内容的ETag
	 */
	public static boolean checkIfNoneMatchEtag(final HttpServletRequest request, final HttpServletResponse response, final String etag) {
		String headerValue = request.getHeader(HTTPs.IF_NONE_MATCH_HEADER);
		if (headerValue != null) {
			boolean conditionSatisfied = false;
			if (!"*".equals(headerValue)) {
				StringTokenizer commaTokenizer = new StringTokenizer(headerValue, ",");
				while (!conditionSatisfied && commaTokenizer.hasMoreTokens()) {
					String currentToken = commaTokenizer.nextToken();
					if (currentToken.trim().equals(etag)) {
						conditionSatisfied = true;
					}
				}
			} else {
				conditionSatisfied = true;
			}

			if (conditionSatisfied) {
				response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
				response.setHeader(HTTPs.E_TAG_HEADER, etag);
				return false;
			}
		}
		return true;
	}

	/**
	 * 设置让浏览器弹出下载对话框的 Header。
	 * 
	 * @param filename
	 *            下载后的文件名
	 */
	public static void setFileDownloadHeader(final HttpServletResponse response, final String filename) {
		try {
			// 中文文件名支持
			String encodedFilename = Encodes.urlEncode(filename);
			response.setHeader(HTTPs.CONTENT_DISPOSITION_HEADER, "attachment; filename=\"" + encodedFilename + "\"");
		} catch (UnsupportedEncodingException e) {
			LOG.warn("", e);
		}
	}
}
