/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.servlet;

import javax.servlet.http.HttpServletRequest;

public abstract class HttpServletRequests
{
	public static final String USER_AGENT_HEADER = "user-agent";

	public static String getUserAgent(HttpServletRequest request)
	{
		return request.getHeader(USER_AGENT_HEADER);
	}
}
