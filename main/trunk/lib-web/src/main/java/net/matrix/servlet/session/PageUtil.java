/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.servlet.session;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import net.matrix.lang.Objects;

public final class PageUtil {
	public static final String PAGING_KEY = "pagekey";

	public static final String URL_KEY = "pageurl";

	public static final String INDEX_KEY = "pageindex";

	public static final String NUM_PER_PAGE_KEY = "numPerPage";

	public static final String TATAL_NUM_KEY = "total";

	public static void setPageInfos(HttpServletRequest request, PagingInfo info) {
		request.setAttribute(PAGING_KEY, info.getKey());
		if (StringUtils.isEmpty(info.getUrl())) {
			request.setAttribute(URL_KEY, request.getServletPath());
		} else {
			request.setAttribute(URL_KEY, info.getUrl());
		}
		request.setAttribute(INDEX_KEY, info.getPageIndex());
		request.setAttribute(NUM_PER_PAGE_KEY, info.getNumberPerPage());
		request.setAttribute(TATAL_NUM_KEY, info.getTotal());
	}

	public static PagingInfo getPageInfos(HttpServletRequest request) {
		return getPageInfos(request, 1);
	}

	public static PagingInfo getPageInfos(HttpServletRequest request, int defaultValue) {
		PagingInfo info = new PagingInfo();
		info.setKey(getPageKey(request));
		info.setUrl(getUrl(request));
		info.setPageIndex(getPageIndex(request));
		info.setNumberPerPage(getNumbersPerPage(request, defaultValue));
		info.setTotal(getTotal(request));
		return info;
	}

	private static String getPageKey(HttpServletRequest request) {
		String key = request.getParameter(PAGING_KEY);
		if (StringUtils.isEmpty(key)) {
			key = (String) request.getAttribute(PAGING_KEY);
		}
		if (StringUtils.isEmpty(key)) {
			key = UUID.randomUUID().toString();
		}
		request.setAttribute(PAGING_KEY, key);
		return key;
	}

	private static String getUrl(HttpServletRequest request) {
		String url = request.getParameter(URL_KEY);
		if (StringUtils.isEmpty(url)) {
			url = (String) request.getAttribute(URL_KEY);
		}
		if (StringUtils.isEmpty(url)) {
			url = request.getServletPath();
		}
		request.setAttribute(URL_KEY, url);
		return url;
	}

	private static int getPageIndex(HttpServletRequest request) {
		int pageIndex = 0;
		String page = request.getParameter(INDEX_KEY);
		if (StringUtils.isEmpty(page)) {
			pageIndex = Objects.isNull((Integer) request.getAttribute(INDEX_KEY), 0);
		} else {
			try {
				pageIndex = Integer.parseInt(page);
			} catch (NumberFormatException ne) {
			}
		}
		request.setAttribute(INDEX_KEY, pageIndex);
		return pageIndex;
	}

	private static int getNumbersPerPage(HttpServletRequest request, int defaultValue) {
		if (defaultValue <= 0) {
			throw new IllegalArgumentException("每页显示数目必须大于等于1");
		}
		int pageIndex = 0;
		String page = request.getParameter(NUM_PER_PAGE_KEY);
		if (StringUtils.isEmpty(page)) {
			pageIndex = Objects.isNull((Integer) request.getAttribute(NUM_PER_PAGE_KEY), defaultValue);
		} else {
			try {
				pageIndex = Integer.parseInt(page);
			} catch (NumberFormatException ne) {
			}
		}
		request.setAttribute(NUM_PER_PAGE_KEY, pageIndex);
		return pageIndex;
	}

	private static long getTotal(HttpServletRequest request) {
		long pageIndex = 0;
		String page = request.getParameter(TATAL_NUM_KEY);
		if (StringUtils.isEmpty(page)) {
			pageIndex = Objects.isNull((Long) request.getAttribute(TATAL_NUM_KEY), 0L);
		} else {
			try {
				pageIndex = Integer.parseInt(page);
			} catch (NumberFormatException ne) {
			}
		}
		request.setAttribute(TATAL_NUM_KEY, pageIndex);
		return pageIndex;
	}
}
