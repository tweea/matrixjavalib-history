/*
 * $Id$
 * Copyright(C) 2009 matrix
 * All right reserved.
 */
package net.matrix.configuration;

import org.apache.commons.configuration.AbstractFileConfiguration;
import org.apache.commons.configuration.event.ConfigurationEvent;
import org.apache.commons.configuration.event.ConfigurationListener;

/**
 * 配置重新加载监听器，当收到配置重新加载事件后重新加载相应的配置对象容器。
 */
public class ConfigurationReloadingListener
	implements ConfigurationListener {
	/**
	 * 需要重新加载的配置对象容器。
	 */
	private final ReloadableConfigurationContainer container;

	/**
	 * 构造一个 {@code ConfigurationReloadingListener}，指定需要重新加载的配置对象容器。
	 * 
	 * @param container
	 *            需要重新加载的配置对象容器
	 */
	public ConfigurationReloadingListener(final ReloadableConfigurationContainer container) {
		this.container = container;
	}

	@Override
	public void configurationChanged(final ConfigurationEvent event) {
		// 当事件类型为重新加载并加载完毕后
		if (event.getType() == AbstractFileConfiguration.EVENT_RELOAD && !event.isBeforeUpdate()) {
			// 调用重新加载配置
			container.reset();
		}
	}
}
