/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.app;

import java.io.File;
import java.io.IOException;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public class SystemContext
{
	private static final Log LOG = LogFactory.getLog(SystemContext.class);

	private static Configuration globalConfig;

	private static ResourceLoader resourceLoader;

	private static UserDataHome userDataHome;

	private static SystemController systemController;

	private SystemContext()
	{
		// 阻止访问
	}

	public static void setGlobalConfig(Configuration conf)
	{
		globalConfig = conf;
	}

	public static Configuration getGlobalConfig()
	{
		checkConfig();
		return globalConfig;
	}

	public static void setResourceLoader(ResourceLoader resourceLoader)
	{
		checkConfig();
		SystemContext.resourceLoader = resourceLoader;
	}

	public static ResourceLoader getResourceLoader()
	{
		checkConfig();
		return resourceLoader;
	}

	public static void setUserDataHome(File userHomeDir)
	{
		checkConfig();
		userDataHome = new UserDataHome(userHomeDir);
	}

	public static UserDataHome getUserDataHome()
	{
		checkConfig();
		return userDataHome;
	}

	public static void setSystemController(SystemController controller)
	{
		checkConfig();
		systemController = controller;
	}

	public static SystemController getSystemController()
	{
		checkConfig();
		return systemController;
	}

	private static void checkConfig()
	{
		// 尝试加载默认位置
		if(globalConfig == null){
			LOG.info("加载默认配置");
			Resource resource = new ClassPathResource("sysconfig.cfg");
			try{
				globalConfig = new PropertiesConfiguration(resource.getURL());
			}catch(IOException e){
				throw new RuntimeException("sysconfig.cfg 加载失败", e);
			}catch(ConfigurationException e){
				throw new RuntimeException("sysconfig.cfg 加载失败", e);
			}
		}
	}
}
