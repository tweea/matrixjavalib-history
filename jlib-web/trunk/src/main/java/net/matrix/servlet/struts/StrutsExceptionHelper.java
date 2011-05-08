/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.servlet.struts;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.Globals;

public class StrutsExceptionHelper
{
	public static void storeException(HttpServletRequest request, Exception e)
	{
		request.setAttribute(Globals.EXCEPTION_KEY, e);
	}

	public static Exception getException(HttpServletRequest request)
	{
		return (Exception)request.getAttribute(Globals.EXCEPTION_KEY);
	}
}
