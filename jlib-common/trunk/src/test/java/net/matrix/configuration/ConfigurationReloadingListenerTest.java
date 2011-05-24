/*
 * $Id$
 * Copyright(C) 2009 北航冠新
 * All right reserved.
 */
package net.matrix.configuration;

import org.apache.commons.configuration.AbstractFileConfiguration;
import org.apache.commons.configuration.event.ConfigurationEvent;
import org.apache.commons.configuration.event.ConfigurationListener;
import org.junit.Assert;
import org.junit.Test;

public class ConfigurationReloadingListenerTest
{
	@Test
	public void testReloadingPerformed()
	{
		// 读取配置
		SampleReloader testReloader = new SampleReloader();
		ConfigurationListener listener = new ConfigurationReloadingListener(testReloader);
		Assert.assertFalse(testReloader.isReloaded());
		listener.configurationChanged(new ConfigurationEvent(this, AbstractFileConfiguration.EVENT_RELOAD, null, null, false));
		Assert.assertTrue(testReloader.isReloaded());
	}
}
