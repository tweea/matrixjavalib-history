/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.app;

/**
 * 全局系统环境，保存系统环境的全局实例。
 */
public final class GlobalSystemContext {
	/**
	 * 同步锁。
	 */
	private static final Object LOCK = new Object();

	/**
	 * 系统环境的全局实例。
	 */
	private volatile static SystemContext global;

	/**
	 * 获取系统环境的全局实例，如果不存在则建立默认的系统环境实例。
	 * 
	 * @return 系统环境的全局实例
	 */
	public static SystemContext get() {
		if (global != null) {
			return global;
		}
		synchronized (LOCK) {
			if (global == null) {
				global = new DefaultSystemContext();
			}
			return global;
		}
	}

	/**
	 * 设置系统环境的全局实例。
	 * 
	 * @param context
	 *            系统环境
	 */
	public static void set(final SystemContext context) {
		global = context;
	}
}
