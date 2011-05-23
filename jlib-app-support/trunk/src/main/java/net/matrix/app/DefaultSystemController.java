/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.app;

public class DefaultSystemController
	implements SystemController
{
	private SystemContext context;

	public DefaultSystemController(SystemContext context)
	{
		this.context = context;
	}

	@Override
	public SystemContext getContext()
	{
		return context;
	}

	@Override
	public void start()
	{
	}

	@Override
	public void stop()
	{
	}

	@Override
	public void reset()
	{
	}
}
