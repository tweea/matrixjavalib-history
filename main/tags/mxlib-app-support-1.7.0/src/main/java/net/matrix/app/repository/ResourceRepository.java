/*
 * $Id$
 * Copyright(C) 2010 matrix
 * All right reserved.
 */
package net.matrix.app.repository;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

/**
 * 资源仓库，根据指定的资源根位置相对定位所有资源。
 */
public class ResourceRepository {
	/**
	 * 日志记录器。
	 */
	private static final Logger LOG = LoggerFactory.getLogger(ResourceRepository.class);

	/**
	 * 资源根位置。
	 */
	private final Resource root;

	/**
	 * @param root
	 *            资源根位置
	 */
	public ResourceRepository(final Resource root) {
		this.root = root;
	}

	/**
	 * 定位资源。
	 * 
	 * @param selection
	 *            资源仓库选择
	 * @return 资源
	 */
	public Resource getResource(final ResourceSelection selection) {
		if (LOG.isTraceEnabled()) {
			LOG.trace("定位资源：" + selection);
		}
		String catalog = selection.getCatalog();
		String version = selection.getVersion();
		String name = selection.getName();

		String path = catalog;
		if (StringUtils.isNotBlank(version)) {
			path += '/' + version;
		}
		while (true) {
			try {
				Resource resource = root.createRelative(path + '/' + name);
				if (resource.exists()) {
					if (LOG.isTraceEnabled()) {
						LOG.trace("定位资源到：" + resource);
					}
					return resource;
				}
			} catch (IOException e) {
				LOG.warn(root + "/" + path + '/' + name + " 解析失败", e);
				return null;
			}
			if (path.length() <= catalog.length()) {
				break;
			}
			path = path.substring(0, path.lastIndexOf('/'));
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("未找到资源：" + selection);
		}
		return null;
	}
}
