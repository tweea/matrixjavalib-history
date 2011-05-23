/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.webapp.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.matrix.app.SystemContext;
import net.matrix.webapp.WebSystemController;

/**
 * 系统初始化
 * @author Tweea
 * @since 2005-11-16
 */
public class InitSystem
	extends HttpServlet
{
	private static final long serialVersionUID = 4408320263631077194L;

	private final static Log LOG = LogFactory.getLog(InitSystem.class);

	@Override
	public final void init()
		throws ServletException
	{
		super.init();
		// 初始化
		LOG.info(getServletContext().getServletContextName() + " 初始化开始");
		SystemContext context = SystemContext.global();
		setResourceLoader(context);
		setConfig(context);
		setController(context);
		context.getController().start();
		LOG.info(getServletContext().getServletContextName() + " 初始化完成");
	}

	protected void setResourceLoader(SystemContext context)
	{
		context.getResourceLoader();
	}

	protected void setConfig(SystemContext context)
	{
		context.getConfig();
	}

	protected void setController(SystemContext context)
	{
		context.setController(new WebSystemController(context, getServletContext()));
	}

	@Override
	public void destroy()
	{
		SystemContext.global().getController().stop();
		super.destroy();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException
	{
		throw new UnsupportedOperationException();
	}
}
