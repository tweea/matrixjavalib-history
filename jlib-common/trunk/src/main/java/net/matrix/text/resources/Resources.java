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
 * 读取多语言资源文件
 * @since 2005-11-22
 */
public class Resources
{
	private static final Log LOG = LogFactory.getLog(Resources.class);

	private static final Map<String, Resources> RESOURCES = new HashMap<String, Resources>();

	private ResourceBundle bundle;

	private Resources(String key)
	{
		try{
			bundle = ResourceBundle.getBundle(key);
		}catch(MissingResourceException e){
			LOG.warn(key + " 资源加载失败", e);
		}
	}

	/**
	 * 根据位置加载资源
	 * @param key 位置
	 * @return 资源
	 */
	public static Resources getResources(String key)
	{
		Resources res = RESOURCES.get(key);
		if(res == null){
			res = new Resources(key);
			RESOURCES.put(key, res);
		}
		return res;
	}

	/**
	 * 获取多语言字符串
	 * @param name 字符串名
	 * @return 字符串
	 */
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

	/**
	 * 加载资源并返回其中指定的字符串
	 * @param key 位置
	 * @param name 字符串名
	 * @return 字符串
	 */
	public static String getProperty(String key, String name)
	{
		return getResources(key).getProperty(name);
	}
}
