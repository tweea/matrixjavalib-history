/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.webapp.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import net.matrix.app.DefaultSystemController;
import net.matrix.app.GlobalSystemContext;
import net.matrix.app.SystemContext;
import net.matrix.app.SystemController;
import net.matrix.app.message.CodedMessageDefinitionLoader;

/**
 * 系统初始化
 * @author Tweea
 * @since 2005-11-16
 */
public class InitSystem
	implements ServletContextListener
{
	private final static Log LOG = LogFactory.getLog(InitSystem.class);

	@Override
	public void contextInitialized(ServletContextEvent sce)
	{
		ServletContext servletContext = sce.getServletContext();

		// 加载消息
		loadMessageDefinitions();

		// 初始化系统环境
		SystemContext context = GlobalSystemContext.get();
		context.registerObject(ServletContext.class, servletContext);
		setResourceLoader(context);
		setConfig(context);

		// 初始化控制器
		SystemController controller = getController();
		controller.setContext(context);
		context.setController(controller);
		controller.init();
		controller.start();

		LOG.info(servletContext.getServletContextName() + " 初始化完成");
	}

	protected void loadMessageDefinitions()
	{
		CodedMessageDefinitionLoader.loadDefinitions(new PathMatchingResourcePatternResolver());
	}

	/**
	 * @param context 系统环境
	 */
	protected void setResourceLoader(SystemContext context)
	{
	}

	/**
	 * @param context 系统环境
	 */
	protected void setConfig(SystemContext context)
	{
	}

	protected SystemController getController()
	{
		return new DefaultSystemController();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce)
	{
		GlobalSystemContext.get().getController().stop();
	}
}
