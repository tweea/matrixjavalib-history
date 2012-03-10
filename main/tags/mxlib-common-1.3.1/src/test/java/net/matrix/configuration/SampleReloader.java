package net.matrix.configuration;

import net.matrix.lang.Reloadable;

public class SampleReloader
	implements Reloadable
{
	private boolean reloaded;

	public SampleReloader()
	{
		reloaded = false;
	}

	@Override
	public void reload()
	{
		reloaded = true;
	}

	public boolean isReloaded()
	{
		return reloaded;
	}
}
