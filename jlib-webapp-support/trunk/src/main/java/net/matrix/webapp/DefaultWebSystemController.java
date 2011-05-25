/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.webapp;

import javax.servlet.ServletContext;

import net.matrix.app.DefaultSystemController;

public class DefaultWebSystemController
	extends DefaultSystemController
	implements WebSystemController
{
	private ServletContext servletContext;

	public DefaultWebSystemController(ServletContext servletContext)
	{
		this.servletContext = servletContext;
	}

	@Override
	public ServletContext getServletContext()
	{
		return servletContext;
	}
}
