/*
 * $Id$
 * Copyright(C) 2009 matrix
 * All right reserved.
 */
package net.matrix.app.repository;

import org.springframework.core.io.Resource;

/**
 * 资源仓库加载环境，根据环境配置在一个资源仓库中定位资源。
 */
public class ResourceContext {
	/**
	 * 资源仓库。
	 */
	private final ResourceRepository repository;

	/**
	 * 加载环境配置。
	 */
	private final ResourceContextConfig contextConfig;

	/**
	 * @param repository
	 *            资源仓库
	 * @param contextConfig
	 *            资源仓库加载环境配置
	 */
	public ResourceContext(final ResourceRepository repository, final ResourceContextConfig contextConfig) {
		this.repository = repository;
		this.contextConfig = contextConfig;
	}

	public ResourceRepository getRepository() {
		return repository;
	}

	public ResourceContextConfig getContextConfig() {
		return contextConfig;
	}

	/**
	 * 重新加载配置。
	 */
	public void reload() {
		contextConfig.checkReload();
	}

	/**
	 * 定位资源。
	 * 
	 * @param catalog
	 *            类别
	 * @return 资源
	 */
	public Resource getResource(final String catalog) {
		ResourceSelection selection = contextConfig.getSelection(catalog);
		if (selection == null) {
			return null;
		}
		return repository.getResource(selection);
	}

	/**
	 * 定位资源。
	 * 
	 * @param catalog
	 *            类别
	 * @param name
	 *            名称
	 * @return 资源
	 */
	public Resource getResource(final String catalog, final String name) {
		ResourceSelection selection = contextConfig.getSelection(catalog, name);
		if (selection == null) {
			return null;
		}
		return repository.getResource(selection);
	}

	/**
	 * 定位资源。
	 * 
	 * @param selection
	 *            资源选择
	 * @return 资源
	 */
	public Resource getResource(final ResourceSelection selection) {
		String catalog = selection.getCatalog();
		String version = selection.getVersion();
		String name = selection.getName();
		if (version == null) {
			if (name == null) {
				return getResource(catalog);
			} else {
				return getResource(catalog, name);
			}
		} else {
			return repository.getResource(selection);
		}
	}
}
