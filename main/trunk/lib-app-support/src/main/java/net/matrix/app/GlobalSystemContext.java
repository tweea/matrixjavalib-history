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

	public static void set(final SystemContext context) {
		global = context;
	}
}
