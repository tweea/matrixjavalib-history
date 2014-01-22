/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.app;

/**
 * 默认的系统控制器，在应用中继承本类以扩展功能。
 */
public class DefaultSystemController
	implements SystemController {
	/**
	 * 关联的系统环境。
	 */
	private SystemContext context;

	@Override
	public void setContext(final SystemContext context) {
		this.context = context;
	}

	@Override
	public SystemContext getContext() {
		return context;
	}

	@Override
	public void init() {
		// 空实现
	}

	@Override
	public void start() {
		// 空实现
	}

	@Override
	public void stop() {
		// 空实现
	}

	@Override
	public void reset() {
		// 空实现
	}
}
