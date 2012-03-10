/*
 * $Id$
 * Copyright(C) 2009 北航冠新
 * All right reserved.
 */
package net.matrix.configuration;

import org.apache.commons.configuration.AbstractFileConfiguration;
import org.apache.commons.configuration.event.ConfigurationEvent;
import org.apache.commons.configuration.event.ConfigurationListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.matrix.lang.ReloadException;
import net.matrix.lang.Reloadable;

/**
 * 配置文件重新加载策略
 */
public class ConfigurationReloadingListener
	implements ConfigurationListener
{
	private static final Log LOG = LogFactory.getLog(ConfigurationReloadingListener.class);

	private Reloadable reloader;

	/**
	 * @param reloader 需要重新加载的配置对象
	 */
	public ConfigurationReloadingListener(Reloadable reloader)
	{
		this.reloader = reloader;
	}

	@Override
	public void configurationChanged(ConfigurationEvent event)
	{
		// 当事件类型为重新加载并加载完毕后
		if(event.getType() == AbstractFileConfiguration.EVENT_RELOAD){
			if(!event.isBeforeUpdate()){
				// 调用重新加载配置
				try{
					reloader.reload();
				}catch(ReloadException e){
					LOG.warn("重新加载配置失败", e);
				}
			}
		}
	}
}
