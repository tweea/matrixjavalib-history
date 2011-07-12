/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.app;

import org.apache.commons.configuration.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * 系统环境
 */
public interface SystemContext
{
	void setResourceLoader(ResourceLoader loader);

	ResourceLoader getResourceLoader();

	ResourcePatternResolver getResourcePatternResolver();

	void setConfig(Configuration config);

	Configuration getConfig();

	void registerObject(String name, Object object);

	<T> void registerObject(Class<T> type, T object);

	Object lookupObject(String name);

	<T> T lookupObject(String name, Class<T> type);

	<T> T lookupObject(Class<T> type);

	void setController(SystemController controller);

	SystemController getController();
}
