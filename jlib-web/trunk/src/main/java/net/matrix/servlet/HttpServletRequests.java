/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.servlet;

import javax.servlet.http.HttpServletRequest;

public abstract class HttpServletRequests
{
	public static String getUserAgent(HttpServletRequest request)
	{
		return request.getHeader("user-agent");
	}
}
