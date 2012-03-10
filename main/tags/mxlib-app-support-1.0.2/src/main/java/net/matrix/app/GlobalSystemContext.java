/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.app;

/**
 * 系统环境
 */
public class GlobalSystemContext
{
	private static SystemContext GLOBAL;

	public static SystemContext get()
	{
		if(GLOBAL == null){
			GLOBAL = new DefaultSystemContext();
		}
		return GLOBAL;
	}

	public static void set(SystemContext context)
	{
		GLOBAL = context;
	}
}
