/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.webapp.servlet;

import java.io.File;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import net.matrix.app.SystemContext;
import net.matrix.app.SystemController;
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
		SystemContext.setGlobalConfig(getGlobalConfig());
		SystemContext.setResourceLoader(getResourceLoader());
		SystemContext.setUserDataHome(getUserDataHome());
		SystemContext.setSystemController(getSystemController());
		SystemContext.getSystemController().start();
		LOG.info(getServletContext().getServletContextName() + " 初始化完成");
	}

	protected Configuration getGlobalConfig()
	{
		return SystemContext.getGlobalConfig();
	}

	protected ResourceLoader getResourceLoader()
	{
		return new DefaultResourceLoader();
	}

	protected File getUserDataHome()
	{
		return new File(getServletContext().getRealPath(SystemContext.getGlobalConfig().getString("user_files_dir")));
	}

	protected SystemController getSystemController()
	{
		return new WebSystemController(getServletContext());
	}

	@Override
	public void destroy()
	{
		SystemContext.getSystemController().stop();
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
