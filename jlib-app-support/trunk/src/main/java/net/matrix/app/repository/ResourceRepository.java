/*
 * $Id$
 * Copyright(C) 2010 北航冠新
 * All right reserved.
 */
package net.matrix.app.repository;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;

/**
 * 资源仓库
 */
public class ResourceRepository
{
	private static final Log LOG = LogFactory.getLog(ResourceRepository.class);

	private Resource root;

	/**
	 * @param root 资源仓库位置
	 */
	public ResourceRepository(Resource root)
	{
		this.root = root;
	}

	/**
	 * 定位资源
	 * @param selection 资源仓库选择
	 * @return 资源
	 */
	public Resource getResource(ResourceSelection selection)
	{
		if(LOG.isTraceEnabled()){
			LOG.trace("定位资源：" + selection);
		}
		String catalog = selection.getCatalog();
		String version = selection.getVersion();
		String name = selection.getName();

		String path = catalog;
		if(StringUtils.isNotBlank(version)){
			path += '/' + version;
		}
		while(true){
			try{
				Resource resource = root.createRelative(path + '/' + name);
				if(resource.exists()){
					if(LOG.isTraceEnabled()){
						LOG.trace("定位资源到：" + resource);
					}
					return resource;
				}
			}catch(IOException e){
				LOG.warn(root + "/" + path + '/' + name + " 解析失败", e);
				throw new RuntimeException(e);
			}
			if(path.length() <= catalog.length()){
				break;
			}
			path = path.substring(0, path.lastIndexOf('/'));
		}
		LOG.debug("未找到资源：" + selection);
		return null;
	}
}
