/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.servlet.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestDumpFilter
	implements Filter {
	private static final Logger LOG = LoggerFactory.getLogger(RequestDumpFilter.class);

	/**
	 * The filter configuration object we are associated with. If this value is
	 * null, this filter instance is not currently configured.
	 */
	protected FilterConfig filterConfig;

	private boolean enabled = false;

	private boolean hasRequesst = true;

	private boolean hasCookie = true;

	private boolean hasResponse = true;

	private boolean hasSession = true;

	private int maxLength = 100;

	/**
	 * Place this filter into service.
	 * 
	 * @param filterConfigIn
	 *            The filter configuration object
	 */
	@Override
	public void init(FilterConfig filterConfigIn)
		throws ServletException {
		this.filterConfig = filterConfigIn;
		this.enabled = "true".equals(filterConfigIn.getInitParameter("enable"));
		this.hasRequesst = !"false".equals(filterConfigIn.getInitParameter("hasRequesst"));
		this.hasCookie = !"false".equals(filterConfigIn.getInitParameter("hasCookie"));
		this.hasResponse = !"false".equals(filterConfigIn.getInitParameter("hasResponse"));
		this.hasSession = !"false".equals(filterConfigIn.getInitParameter("hasSession"));
		if (!StringUtils.isEmpty(filterConfigIn.getInitParameter("maxLength"))) {
			this.maxLength = Integer.parseInt(filterConfigIn.getInitParameter("maxLength"));
		}
	}

	/**
	 * Take this filter out of service.
	 */
	@Override
	public void destroy() {
		this.filterConfig = null;
	}

	/**
	 * Select and set (if specified) the character encoding to be used to
	 * interpret request parameters for this request.
	 * 
	 * @param request
	 *            The servlet request we are processing
	 * @param response
	 *            The servlet response we are creating
	 * @param chain
	 *            The filter chain we are processing
	 * @exception IOException
	 *                if an input/output error occurs
	 * @exception ServletException
	 *                if a servlet error occurs
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
		throws IOException, ServletException {
		if (enabled) {
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			HttpSession httpSession = httpRequest.getSession(false);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			pw.println();
			pw.println("============================== 请求内容开始 ======================================");

			if (hasRequesst) {
				dumpRequest(httpRequest, pw);
			}

			if (hasCookie) {
				Cookie[] cookies = httpRequest.getCookies();
				if (cookies != null)
					for (Cookie cookie : cookies)
						dumpCookie(cookie, pw);
			}

			if (hasSession) {
				dumpSession(httpSession, pw);
			}

			if (hasResponse) {
				dumpResponse(httpResponse, pw);
			}

			pw.println("============================== 请求内容结束 ======================================");
			LOG.info(sw.toString());
		}
		// Pass control on to the next filter
		chain.doFilter(request, response);
	}

	/**
	 * @param request
	 * @param writer
	 */
	private void dumpRequest(HttpServletRequest request, PrintWriter writer) {
		// Object Properties
		Map<String, String> requestProperties = new LinkedHashMap<String, String>();
		requestProperties.put("Local", request.getLocalAddr() + ":" + request.getLocalPort());
		requestProperties.put("Remote", request.getRemoteAddr() + ":" + request.getRemotePort());
		requestProperties.put("RequestURI", request.getRequestURI());
		requestProperties.put("PathInfo", request.getPathInfo());
		requestProperties.put("QueryString", request.getQueryString());
		requestProperties.put("Method", request.getMethod());
		requestProperties.put("ContentType", request.getContentType());
		requestProperties.put("ContentLength", Integer.toString(request.getContentLength()));
		requestProperties.put("CharacterEncoding", request.getCharacterEncoding());
		requestProperties.put("Locale", request.getLocale().toString());
		requestProperties.put("Locales", Collections.list(request.getLocales()).toString());
		dumpStringMap(writer, "Request: " + request, requestProperties);
		// request headers
		Map<String, String> requestHeaders = new LinkedHashMap<String, String>();
		List<String> names = Collections.list(request.getHeaderNames());
		Collections.sort(names);
		for (String name : names) {
			String value = request.getHeader(name);
			requestHeaders.put(name, value);
		}
		dumpStringMap(writer, "Request Headers", requestHeaders);
		// request parameters
		Map<String, String> requestParameters = new LinkedHashMap<String, String>();
		names = Collections.list(request.getParameterNames());
		Collections.sort(names);
		for (String name : names) {
			String[] values = request.getParameterValues(name);
			if (values.length < 2)
				requestParameters.put(name, values[0]);
			else
				requestParameters.put(name, Arrays.toString(values));
		}
		dumpStringMap(writer, "Request Parameters", requestParameters);
		// request attributes
		Map<String, Object> requestAttributes = new LinkedHashMap<String, Object>();
		names = Collections.list(request.getAttributeNames());
		Collections.sort(names);
		for (String name : names) {
			Object obj = request.getAttribute(name);
			requestAttributes.put(name, obj);
		}
		dumpObjectMap(writer, "Request Attributes", requestAttributes);
	}

	private void dumpCookie(Cookie cookie, PrintWriter writer) {
		Map<String, String> cookieProperties = new LinkedHashMap<String, String>();
		cookieProperties.put("Name", cookie.getName());
		cookieProperties.put("Value", cookie.getValue());
		cookieProperties.put("Domain", cookie.getDomain());
		cookieProperties.put("Path", cookie.getPath());
		cookieProperties.put("MaxAge", Integer.toString(cookie.getMaxAge()));
		cookieProperties.put("Secure", Boolean.toString(cookie.getSecure()));
		cookieProperties.put("Version", Integer.toString(cookie.getVersion()));
		cookieProperties.put("Comment", cookie.getComment());
		dumpStringMap(writer, "Cookie: " + cookie, cookieProperties);
	}

	/**
	 * @param session
	 * @param writer
	 */
	private void dumpSession(HttpSession session, PrintWriter writer) {
		writer.print("session: ");
		if (session == null) {
			writer.println("未创建");
		} else {
			writer.println(session);
			// session attributes
			Map<String, Object> sessionAttributes = new LinkedHashMap<String, Object>();
			List<String> names = Collections.list(session.getAttributeNames());
			Collections.sort(names);
			for (String name : names) {
				Object obj = session.getAttribute(name);
				sessionAttributes.put(name, obj);
			}
			dumpObjectMap(writer, "Session Attributes", sessionAttributes);
		}
	}

	/**
	 * @param response
	 * @param writer
	 */
	private void dumpResponse(HttpServletResponse response, PrintWriter writer) {
		writer.print("response: ");
		writer.println(response);
	}

	private void dumpStringMap(PrintWriter writer, String title, Map<String, String> map) {
		int maxNameLen = 0;
		int maxValueLen = 0;
		int totalLen = title.length();
		if (map.size() != 0) {
			for (Map.Entry<String, String> item : map.entrySet()) {
				if (item.getValue() == null)
					item.setValue("(null)");
				if (item.getKey().length() > maxNameLen)
					maxNameLen = item.getKey().length();
				if (item.getValue().length() <= maxLength && item.getValue().length() > maxValueLen)
					maxValueLen = item.getValue().length();
				else if (item.getValue().length() > maxLength)
					maxValueLen = maxLength;
			}
			if (maxNameLen + maxValueLen + 1 > totalLen)
				totalLen = maxNameLen + maxValueLen + 1;
			else
				maxValueLen = totalLen - (maxNameLen + 1);
		}
		writer.print('+');
		for (int i = 0; i < totalLen; i++)
			writer.print('-');
		writer.println('+');
		writer.print('|');
		writer.print(title);
		for (int i = 0; i < totalLen - title.length(); i++)
			writer.print(' ');
		writer.println('|');
		if (map.size() == 0) {
			writer.print('+');
			for (int i = 0; i < totalLen; i++)
				writer.print('-');
			writer.println('+');
		} else {
			writer.print('+');
			for (int i = 0; i < maxNameLen; i++)
				writer.print('-');
			writer.print('+');
			for (int i = 0; i < maxValueLen; i++)
				writer.print('-');
			writer.println('+');
			for (Map.Entry<String, String> item : map.entrySet()) {
				writer.print('|');
				writer.print(item.getKey());
				for (int i = 0; i < maxNameLen - item.getKey().length(); i++)
					writer.print(' ');
				writer.print('|');
				int linNum = item.getValue().length() / maxLength;
				linNum += item.getValue().length() % maxLength == 0 ? 0 : 1;
				if (linNum == 0) {
					for (int i = 0; i < maxValueLen; i++)
						writer.print(' ');
					writer.println('|');
				}
				for (int j = 0; j < linNum; j++) {
					if (j < linNum - 1) {
						writer.append(item.getValue(), j * maxLength, (j + 1) * maxLength);
						writer.println('|');
						writer.print('|');
						for (int i = 0; i < maxNameLen; i++)
							writer.print(' ');
						writer.print('|');
					} else if (linNum > 1) {
						writer.append(item.getValue(), j * maxLength, item.getValue().length());
						for (int i = 0; i < (j + 1) * maxLength - item.getValue().length(); i++)
							writer.print(' ');
						writer.println('|');
					} else {
						writer.append(item.getValue());
						for (int i = 0; i < maxValueLen - item.getValue().length(); i++)
							writer.print(' ');
						writer.println('|');
					}
				}
			}
			writer.print('+');
			for (int i = 0; i < maxNameLen; i++)
				writer.print('-');
			writer.print('+');
			for (int i = 0; i < maxValueLen; i++)
				writer.print('-');
			writer.println('+');
		}
	}

	private class ClassAndToString {
		private String clazz;

		private String toString;

		public ClassAndToString(Object obj) {
			if (obj == null) {
				this.clazz = "(np)";
				this.toString = "(null)";
			} else {
				this.clazz = obj.getClass().toString();
				if (obj.getClass().isArray()) {
					this.toString = Arrays.toString((Object[]) obj);
				} else {
					this.toString = obj.toString();
				}
			}
		}
	}

	private void dumpObjectMap(PrintWriter writer, String title, Map<String, Object> objMap) {
		Map<String, ClassAndToString> map = new LinkedHashMap<String, ClassAndToString>();
		for (Map.Entry<String, Object> item : objMap.entrySet())
			map.put(item.getKey(), new ClassAndToString(item.getValue()));
		int maxNameLen = 0;
		int maxClassLen = 0;
		int maxValueLen = title.length();
		if (map.size() != 0) {
			for (Map.Entry<String, ClassAndToString> item : map.entrySet()) {
				if (item.getKey().length() > maxNameLen)
					maxNameLen = item.getKey().length();
				if (item.getValue().clazz.length() > maxClassLen)
					maxClassLen = item.getValue().clazz.length();
				if (item.getValue().toString.length() <= maxLength && item.getValue().toString.length() > maxValueLen)
					maxValueLen = item.getValue().toString.length();
				else if (item.getValue().toString.length() > maxLength)
					maxValueLen = maxLength;
			}
			if (maxNameLen + maxClassLen + 1 > maxValueLen)
				maxValueLen = maxNameLen + maxClassLen + 1;
			else
				maxClassLen = maxValueLen - (maxNameLen + 1);
		}
		writer.print('+');
		for (int i = 0; i < maxValueLen; i++)
			writer.print('-');
		writer.println('+');
		writer.print('|');
		writer.print(title);
		for (int i = 0; i < maxValueLen - title.length(); i++)
			writer.print(' ');
		writer.println('|');
		if (map.size() == 0) {
			writer.print('+');
			for (int i = 0; i < maxValueLen; i++)
				writer.print('-');
			writer.println('+');
		} else {
			writer.print('+');
			for (int i = 0; i < maxNameLen; i++)
				writer.print('-');
			writer.print('+');
			for (int i = 0; i < maxClassLen; i++)
				writer.print('-');
			writer.println('+');
			for (Map.Entry<String, ClassAndToString> item : map.entrySet()) {
				writer.print('|');
				writer.print(item.getKey());
				for (int i = 0; i < maxNameLen - item.getKey().length(); i++)
					writer.print(' ');
				writer.print('|');
				writer.print(item.getValue().clazz);
				for (int i = 0; i < maxClassLen - item.getValue().clazz.length(); i++)
					writer.print(' ');
				writer.println('|');
				int linNum = item.getValue().toString.length() / maxLength;
				linNum += item.getValue().toString.length() % maxLength == 0 ? 0 : 1;
				if (linNum == 0) {
					for (int i = 0; i < maxValueLen; i++)
						writer.print(' ');
					writer.println('|');
				}
				for (int j = 0; j < linNum; j++) {
					writer.print('|');
					if (j < linNum - 1) {
						writer.append(item.getValue().toString, j * maxLength, (j + 1) * maxLength);
						writer.println('|');
					} else if (linNum > 1) {
						writer.append(item.getValue().toString, j * maxLength, item.getValue().toString.length());
						for (int i = 0; i < (j + 1) * maxLength - item.getValue().toString.length(); i++)
							writer.print(' ');
						writer.println('|');
					} else {
						writer.append(item.getValue().toString);
						for (int i = 0; i < maxValueLen - item.getValue().toString.length(); i++)
							writer.print(' ');
						writer.println('|');
					}
				}
			}
			writer.print('+');
			for (int i = 0; i < maxValueLen; i++)
				writer.print('-');
			writer.println('+');
		}
	}
}
