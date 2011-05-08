/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.servlet.struts;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ExceptionHandler;
import org.apache.struts.config.ExceptionConfig;

import net.matrix.servlet.session.WebProcess;

public class MxExceptionHandler
	extends ExceptionHandler
{
	@Override
	public ActionForward execute(Exception e, ExceptionConfig config, ActionMapping mappings, ActionForm form, HttpServletRequest request,
		HttpServletResponse response)
		throws ServletException
	{
		WebProcess.storeRequestURI(request);
		return super.execute(e, config, mappings, form, request, response);
	}
}
