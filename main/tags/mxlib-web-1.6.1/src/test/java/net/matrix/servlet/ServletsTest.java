/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.servlet;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class ServletsTest {
	@Test
	public void checkIfModified() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		// 未设Header,返回true,需要传输内容
		assertEquals(true, Servlets.checkIfModifiedSince(request, response, (new Date().getTime() - 2000)));

		// 设置If-Modified-Since Header
		request.addHeader("If-Modified-Since", new Date().getTime());
		// 文件修改时间比Header时间小,文件未修改, 返回false.
		assertEquals(false, Servlets.checkIfModifiedSince(request, response, (new Date().getTime() - 2000)));
		// 文件修改时间比Header时间大,文件已修改, 返回true,需要传输内容.
		assertEquals(true, Servlets.checkIfModifiedSince(request, response, (new Date().getTime() + 2000)));
	}

	@Test
	public void checkIfNoneMatch() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		// 未设Header,返回true,需要传输内容
		assertEquals(true, Servlets.checkIfNoneMatchEtag(request, response, "V1.0"));

		// 设置If-None-Match Header
		request.addHeader("If-None-Match", "V1.0,V1.1");
		// 存在Etag
		assertEquals(false, Servlets.checkIfNoneMatchEtag(request, response, "V1.0"));
		// 不存在Etag
		assertEquals(true, Servlets.checkIfNoneMatchEtag(request, response, "V2.0"));
	}
}
