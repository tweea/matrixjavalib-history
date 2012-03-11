/*
 * $Id$
 * Copyright(C) 2009 北航冠新
 * All right reserved.
 */
package net.matrix.app.repository;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 资源仓库选择集合
 */
public class ResourceSelectionSet {
	private static final Logger LOG = LoggerFactory.getLogger(ResourceSelectionSet.class);

	private Set<ResourceSelection> selections;

	public ResourceSelectionSet() {
		this.selections = new HashSet<ResourceSelection>();
	}

	public void add(ResourceSelection selection) {
		selections.add(selection);
	}

	public boolean contains(ResourceSelection selection) {
		return selections.contains(selection);
	}

	public boolean remove(ResourceSelection selection) {
		return selections.remove(selection);
	}

	/**
	 * @return 类别名称集合
	 */
	public Set<String> catalogNames() {
		Set<String> catalogs = new HashSet<String>();
		for (ResourceSelection selection : selections) {
			catalogs.add(selection.getCatalog());
		}
		return catalogs;
	}

	/**
	 * @param catalog
	 *            类别
	 * @return 资源名称集合
	 */
	public Set<String> resourceNames(String catalog) {
		Set<String> resources = new HashSet<String>();
		for (ResourceSelection selection : selections) {
			if (selection.getCatalog().equals(catalog)) {
				resources.add(selection.getName());
			}
		}
		return resources;
	}

	/**
	 * 资源选择
	 * 
	 * @param catalog
	 *            类别
	 * @return 资源选择
	 */
	public Set<ResourceSelection> getSelections(String catalog) {
		return getSelections(catalog, ResourceSelection.generateName(catalog));
	}

	/**
	 * 资源选择
	 * 
	 * @param catalog
	 *            类别
	 * @param name
	 *            名称
	 * @return 资源选择
	 */
	public Set<ResourceSelection> getSelections(String catalog, String name) {
		Set<ResourceSelection> result = new HashSet<ResourceSelection>();
		for (ResourceSelection selection : selections) {
			if (selection.getCatalog().equals(catalog) && selection.getName().equals(name)) {
				result.add(selection);
			}
		}
		return result;
	}

	public Set<ResourceSelection> checkDiff(ResourceSelectionSet target) {
		// 更新信息列表
		Set<ResourceSelection> diffs = new HashSet<ResourceSelection>();

		// 读取源版本
		Set<ResourceSelection> sourceEntrys = selections;
		LOG.debug("源版本：" + sourceEntrys);

		// 读取目标版本
		Set<ResourceSelection> targetEntrys = target.selections;
		LOG.debug("目标版本：" + targetEntrys);

		// 处理是否更新
		diffs.addAll(targetEntrys);
		diffs.removeAll(sourceEntrys);

		LOG.debug("更新结果：" + diffs);
		return diffs;
	}
}
