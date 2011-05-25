/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.app;

import org.apache.commons.configuration.Configuration;
import org.springframework.core.io.ResourceLoader;

/**
 * 系统环境
 */
public interface SystemContext
{
	void setResourceLoader(ResourceLoader loader);

	ResourceLoader getResourceLoader();

	void setConfig(Configuration config);

	Configuration getConfig();

	void setController(SystemController controller);

	SystemController getController();
}
