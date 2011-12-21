/*
 * $Id$
 * Copyright(C) 2009 北航冠新
 * All right reserved.
 */
package net.matrix.app.repository;

import java.util.List;
import java.util.Set;

import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.lang.StringUtils;

import net.matrix.configuration.XMLConfigurationContainer;

/**
 * 资源仓库加载环境配置
 */
public class ResourceContextConfig
	extends XMLConfigurationContainer
{
	private ResourceSelectionSet set;

	@Override
	public void reset()
	{
		super.reset();
		set = new ResourceSelectionSet();
		// catalog 节点
		List<HierarchicalConfiguration> catalogsNode = getConfig().configurationsAt("catalog");
		for(HierarchicalConfiguration catalogNode : catalogsNode){
			String catalog = catalogNode.getString("[@name]");
			String version = catalogNode.getString("[@version]");
			// resource 节点
			List<HierarchicalConfiguration> resourcesNode = catalogNode.configurationsAt("file");
			if(resourcesNode.size() == 0){
				set.add(new ResourceSelection(catalog, version, null));
			}else{
				for(HierarchicalConfiguration resourceNode : resourcesNode){
					String resourceName = resourceNode.getString("[@name]");
					String resourceVersion = resourceNode.getString("[@version]");
					String branch = resourceNode.getString("[@branch]");
					if(StringUtils.isEmpty(resourceVersion)){
						resourceVersion = version;
					}
					if(!StringUtils.isEmpty(branch)){
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
	public Set<String> catalogNames()
	{
		checkReload();
		return set.catalogNames();
	}

	/**
	 * @param catalog 类别
	 * @return 资源名称集合
	 */
	public Set<String> resourceNames(String catalog)
	{
		checkReload();
		return set.resourceNames(catalog);
	}

	/**
	 * 资源选择
	 * @param catalog 类别
	 * @return 资源选择
	 */
	public ResourceSelection getSelection(String catalog)
	{
		checkReload();
		Set<ResourceSelection> result = set.getSelections(catalog);
		for(ResourceSelection selection : result){
			return selection;
		}
		return null;
	}

	/**
	 * 资源选择
	 * @param catalog 类别
	 * @param name 资源名
	 * @return 资源选择
	 */
	public ResourceSelection getSelection(String catalog, String name)
	{
		checkReload();
		Set<ResourceSelection> result = set.getSelections(catalog, name);
		for(ResourceSelection selection : result){
			return selection;
		}
		return null;
	}

	public Set<ResourceSelection> checkDiff(ResourceContextConfig target)
	{
		checkReload();
		return set.checkDiff(target.set);
	}
}
