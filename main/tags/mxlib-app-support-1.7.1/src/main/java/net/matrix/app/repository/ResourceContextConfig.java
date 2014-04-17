/*
 * $Id$
 * Copyright(C) 2009 matrix
 * All right reserved.
 */
package net.matrix.app.repository;

import java.util.List;
import java.util.Set;

import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.lang3.StringUtils;

import net.matrix.configuration.XMLConfigurationContainer;

/**
 * 资源仓库加载环境配置，保存了一系列资源仓库选择。
 */
public class ResourceContextConfig
	extends XMLConfigurationContainer {
	/**
	 * 根据配置内容生成的集合。
	 */
	private ResourceSelectionSet set;

	@Override
	public void reset() {
		super.reset();
		set = new ResourceSelectionSet();
		// catalog 节点
		List<HierarchicalConfiguration> catalogsNode = getConfig().configurationsAt("catalog");
		for (HierarchicalConfiguration catalogNode : catalogsNode) {
			String catalog = catalogNode.getString("[@name]");
			String version = catalogNode.getString("[@version]");
			// resource 节点
			List<HierarchicalConfiguration> resourcesNode = catalogNode.configurationsAt("file");
			if (resourcesNode.isEmpty()) {
				set.add(new ResourceSelection(catalog, version, null));
			} else {
				for (HierarchicalConfiguration resourceNode : resourcesNode) {
					String resourceName = resourceNode.getString("[@name]");
					String resourceVersion = resourceNode.getString("[@version]");
					String branch = resourceNode.getString("[@branch]");
					if (StringUtils.isEmpty(resourceVersion)) {
						resourceVersion = version;
					}
					if (!StringUtils.isEmpty(branch)) {
						resourceVersion += '/' + branch;
					}
					set.add(new ResourceSelection(catalog, resourceVersion, resourceName));
				}
			}
		}
	}

	/**
	 * @return 类别名称集合
	 */
	public Set<String> catalogNames() {
		checkReload();
		return set.catalogNames();
	}

	/**
	 * @param catalog
	 *            类别
	 * @return 资源名称集合
	 */
	public Set<String> resourceNames(final String catalog) {
		checkReload();
		return set.resourceNames(catalog);
	}

	/**
	 * 选择某类别的默认名称资源。
	 * 
	 * @param catalog
	 *            类别
	 * @return 资源选择
	 */
	public ResourceSelection getSelection(final String catalog) {
		checkReload();
		Set<ResourceSelection> result = set.getSelections(catalog);
		for (ResourceSelection selection : result) {
			return selection;
		}
		return null;
	}

	/**
	 * 选择某类别的特定名称资源。
	 * 
	 * @param catalog
	 *            类别
	 * @param name
	 *            资源名
	 * @return 资源选择
	 */
	public ResourceSelection getSelection(final String catalog, final String name) {
		checkReload();
		Set<ResourceSelection> result = set.getSelections(catalog, name);
		for (ResourceSelection selection : result) {
			return selection;
		}
		return null;
	}

	/**
	 * 检查本配置与另一配置包含资源仓库选择的差异。
	 * 
	 * @param target
	 *            另一配置
	 * @return 差异集合
	 */
	public Set<ResourceSelection> checkDiff(final ResourceContextConfig target) {
		checkReload();
		return set.checkDiff(target.set);
	}
}
