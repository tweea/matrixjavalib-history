/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.webapp;

import javax.servlet.ServletContext;

import net.matrix.app.SystemController;

public class WebSystemController
	implements SystemController
{
	protected ServletContext context;

	public WebSystemController(ServletContext context)
	{
		this.context = context;
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
