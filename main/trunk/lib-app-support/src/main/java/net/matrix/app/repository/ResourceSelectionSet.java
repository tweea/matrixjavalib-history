/*
 * $Id$
 * Copyright(C) 2009 matrix
 * All right reserved.
 */
package net.matrix.app.repository;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 资源仓库选择集合。
 */
public class ResourceSelectionSet {
	/**
	 * 日志记录器。
	 */
	private static final Logger LOG = LoggerFactory.getLogger(ResourceSelectionSet.class);

	/**
	 * 内部集合。
	 */
	private Set<ResourceSelection> selections;

	/**
	 * 构造一个空的集合。
	 */
	public ResourceSelectionSet() {
		this.selections = new HashSet<ResourceSelection>();
	}

	/**
	 * 增加一个资源仓库选择。
	 * 
	 * @param selection
	 *            资源仓库选择
	 */
	public void add(final ResourceSelection selection) {
		selections.add(selection);
	}

	/**
	 * 判断是否已包含某资源仓库选择。
	 * 
	 * @param selection
	 *            资源仓库选择
	 * @return true 已包含
	 */
	public boolean contains(final ResourceSelection selection) {
		return selections.contains(selection);
	}

	/**
	 * 移除资源仓库选择。
	 * 
	 * @param selection
	 *            资源仓库选择
	 * @return true 集合中存在该资源仓库选择
	 */
	public boolean remove(final ResourceSelection selection) {
		return selections.remove(selection);
	}

	/**
	 * 汇总得到所有资源仓库选择的类别信息。
	 * 
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
	 * 根据类别名称获取集合中包含的同一类别的资源名称。
	 * 
	 * @param catalog
	 *            类别
	 * @return 资源名称集合
	 */
	public Set<String> resourceNames(final String catalog) {
		Set<String> resources = new HashSet<String>();
		for (ResourceSelection selection : selections) {
			if (selection.getCatalog().equals(catalog)) {
				resources.add(selection.getName());
			}
		}
		return resources;
	}

	/**
	 * 选择某类别的默认名称资源。
	 * 
	 * @param catalog
	 *            类别
	 * @return 资源选择
	 */
	public Set<ResourceSelection> getSelections(final String catalog) {
		return getSelections(catalog, ResourceSelection.generateName(catalog));
	}

	/**
	 * 选择某类别的特定名称资源。
	 * 
	 * @param catalog
	 *            类别
	 * @param name
	 *            名称
	 * @return 资源选择
	 */
	public Set<ResourceSelection> getSelections(final String catalog, final String name) {
		Set<ResourceSelection> result = new HashSet<ResourceSelection>();
		for (ResourceSelection selection : selections) {
			if (selection.getCatalog().equals(catalog) && selection.getName().equals(name)) {
				result.add(selection);
			}
		}
		return result;
	}

	/**
	 * 检查本集合与另一集合包含资源仓库选择的差异。
	 * 
	 * @param target
	 *            另一集合
	 * @return 差异集合
	 */
	public Set<ResourceSelection> checkDiff(final ResourceSelectionSet target) {
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
