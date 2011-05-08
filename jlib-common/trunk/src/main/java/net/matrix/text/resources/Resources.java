/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.text.resources;

import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 读取资源文件
 * @since 2005-11-22
 */
public class Resources
{
	private final static Log LOG = LogFactory.getLog(Resources.class);

	private final static Map<String, Resources> RESOURCES = new HashMap<String, Resources>();

	private ResourceBundle bundle;

	private Resources(String key)
	{
		try{
			bundle = ResourceBundle.getBundle(key);
		}catch(MissingResourceException e){
			LOG.warn(key + " 资源加载失败", e);
		}
	}

	public static Resources getResources(String key)
	{
		Resources res = RESOURCES.get(key);
		if(res == null){
			res = new Resources(key);
			RESOURCES.put(key, res);
		}
		return res;
	}

	public String getProperty(String name)
	{
		if(bundle == null){
			return name;
		}
		try{
			return bundle.getString(name);
		}catch(MissingResourceException e){
			LOG.warn("找不到名为 " + name + " 的资源项");
			return name;
		}
	}

	public static String getProperty(String key, String name)
	{
		return getResources(key).getProperty(name);
	}
}
