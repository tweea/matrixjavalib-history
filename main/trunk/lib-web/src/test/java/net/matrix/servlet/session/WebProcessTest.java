package net.matrix.servlet.session;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

public class WebProcessTest {
	@Test
	public void getParametersStartingWith() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addParameter("pre_a", "aa");
		request.addParameter("pre_b", "bb");
		request.addParameter("c", "c");
		Map<String, Object> result = WebProcess.getParametersStartingWith(request, "pre_");
		assertEquals(2, result.size());
		assertTrue(result.keySet().contains("a"));
		assertTrue(result.keySet().contains("b"));
		assertTrue(result.values().contains("aa"));
		assertTrue(result.values().contains("bb"));

		result = WebProcess.getParametersStartingWith(request, "error_");
		assertEquals(0, result.size());

		result = WebProcess.getParametersStartingWith(request, null);
		assertEquals(3, result.size());
	}
}
