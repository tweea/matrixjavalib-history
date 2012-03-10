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
	private static SystemContext global;

	public static SystemContext get()
	{
		if(global == null){
			global = new DefaultSystemContext();
		}
		return global;
	}

	public static void set(SystemContext context)
	{
		global = context;
	}
}
