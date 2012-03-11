/*
 * $Id$
 * Copyright(C) 2009 matrix
 * All right reserved.
 */
package net.matrix.app.repository;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 资源仓库选择
 */
public class ResourceSelection {
	private String catalog;

	private String version;

	private String name;

	/**
	 * @param catalog
	 *            分类
	 * @param version
	 *            版本
	 * @param name
	 *            名称
	 */
	public ResourceSelection(String catalog, String version, String name) {
		this.catalog = catalog;
		this.version = version;
		if (StringUtils.isBlank(name)) {
			this.name = generateName(catalog);
		} else {
			this.name = name;
		}
	}

	public static String generateName(String catalog) {
		String[] catalogs = catalog.split("/");
		return catalogs[catalogs.length - 1];
	}

	/**
	 * @return 分类
	 */
	public String getCatalog() {
		return catalog;
	}

	/**
	 * @return 版本
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @return 名称
	 */
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "ResourceSelection[" + catalog + ":" + ObjectUtils.defaultIfNull(version, "") + ":" + name + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((catalog == null) ? 0 : catalog.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ResourceSelection other = (ResourceSelection) obj;
		if (catalog == null) {
			if (other.catalog != null)
				return false;
		} else if (!catalog.equals(other.catalog))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}
}
