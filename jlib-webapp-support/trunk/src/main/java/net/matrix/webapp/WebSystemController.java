/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.webapp;

import javax.servlet.ServletContext;

import net.matrix.app.DefaultSystemController;
import net.matrix.app.SystemContext;

public class WebSystemController
	extends DefaultSystemController
{
	protected ServletContext servletContext;

	public WebSystemController(SystemContext context, ServletContext servletContext)
	{
		super(context);
		this.servletContext = servletContext;
	}

	@Override
	public void reset()
	{
	}

	@Override
	public void start()
	{
	}

	@Override
	public void stop()
	{
	}
}
