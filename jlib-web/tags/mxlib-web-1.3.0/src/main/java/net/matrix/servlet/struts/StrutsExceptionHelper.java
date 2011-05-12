/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.servlet.struts;

import javax.servlet.http.HttpServletRequest;

public class StrutsExceptionHelper
{
	public static final String EXCEPTION_KEY = "org.apache.struts.action.EXCEPTION";

	public static void storeException(HttpServletRequest request, Exception e)
	{
		request.setAttribute(EXCEPTION_KEY, e);
	}

	public static Exception getException(HttpServletRequest request)
	{
		return (Exception)request.getAttribute(EXCEPTION_KEY);
	}
}
