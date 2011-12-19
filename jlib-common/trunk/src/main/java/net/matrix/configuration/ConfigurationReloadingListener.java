/*
 * $Id$
 * Copyright(C) 2009 北航冠新
 * All right reserved.
 */
package net.matrix.configuration;

import org.apache.commons.configuration.AbstractFileConfiguration;
import org.apache.commons.configuration.event.ConfigurationEvent;
import org.apache.commons.configuration.event.ConfigurationListener;

/**
 * 配置重新加载监听器，当收到配置重新加载事件后重新加载相应的对象
 */
public class ConfigurationReloadingListener
	implements ConfigurationListener
{
	private ReloadableConfigurationContainer container;

	/**
	 * @param container 需要重新加载的对象
	 */
	public ConfigurationReloadingListener(ReloadableConfigurationContainer container)
	{
		this.container = container;
	}

	@Override
	public void configurationChanged(ConfigurationEvent event)
	{
		// 当事件类型为重新加载并加载完毕后
		if(event.getType() == AbstractFileConfiguration.EVENT_RELOAD){
			if(!event.isBeforeUpdate()){
				// 调用重新加载配置
				container.reset();
			}
		}
	}
}
