/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.servlet.session;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

public class HttpServletsTest {
	@Test
	public void getParametersStartingWith() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addParameter("pre_a", "aa");
		request.addParameter("pre_b", "bb");
		request.addParameter("c", "c");
		Map<String, Object> result = HttpServlets.getParametersStartingWith(request, "pre_");
		assertEquals(2, result.size());
		assertTrue(result.keySet().contains("a"));
		assertTrue(result.keySet().contains("b"));
		assertTrue(result.values().contains("aa"));
		assertTrue(result.values().contains("bb"));

		result = HttpServlets.getParametersStartingWith(request, "error_");
		assertEquals(0, result.size());

		result = HttpServlets.getParametersStartingWith(request, null);
		assertEquals(3, result.size());
	}
}
