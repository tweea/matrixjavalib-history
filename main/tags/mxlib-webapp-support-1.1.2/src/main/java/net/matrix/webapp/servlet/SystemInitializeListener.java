/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.webapp.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.matrix.app.DefaultSystemController;
import net.matrix.app.GlobalSystemContext;
import net.matrix.app.SystemContext;
import net.matrix.app.SystemController;
import net.matrix.app.message.CodedMessageDefinitionLoader;

/**
 * 系统初始化监听器，注册在 web.xml 中被容器调用初始化、启动和停止。
 * 
 * @since 2005-11-16
 */
public class SystemInitializeListener
	implements ServletContextListener {
	/**
	 * 日志记录器。
	 */
	private static final Logger LOG = LoggerFactory.getLogger(SystemInitializeListener.class);

	/**
	 * 关联的系统环境。
	 */
	protected SystemContext context;

	/**
	 * 默认构造器。
	 */
	public SystemInitializeListener() {
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext servletContext = sce.getServletContext();

		// 默认与全局系统环境关联
		context = GlobalSystemContext.get();

		// 加载消息
		loadMessageDefinitions();

		// 初始化系统环境
		context.registerObject(ServletContext.class, servletContext);
		setResourceLoader();
		setConfig();

		// 初始化控制器
		SystemController controller = getController();
		controller.setContext(context);
		context.setController(controller);
		controller.init();
		controller.start();

		LOG.info(servletContext.getServletContextName() + " 初始化完成");
	}

	/**
	 * 加载消息定义。
	 */
	protected void loadMessageDefinitions() {
		CodedMessageDefinitionLoader.loadDefinitions(context.getResourcePatternResolver());
	}

	protected void setResourceLoader() {
	}

	protected void setConfig() {
	}

	/**
	 * 初始化系统控制器。
	 * 
	 * @return 系统控制器
	 */
	protected SystemController getController() {
		return new DefaultSystemController();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		context.getController().stop();
	}
}
