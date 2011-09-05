package net.matrix.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;

import net.matrix.util.IterableIterator;

/**
 * 配置对象实用工具
 */
public class HierarchicalConfigurationUtils
{
	private HierarchicalConfigurationUtils()
	{
	}

	/**
	 * 从一个表示名字、数值对的配置对象中抽取信息转换为 <code>Map</code> 对象
	 * @param config 配置对象
	 * @param subKey 子项配置键值
	 * @param nameKey 名字配置键值
	 * @param valueKey 值配置键值
	 * @return <code>Map</code> 对象
	 */
	public static Map<String, String> parseParameter(HierarchicalConfiguration config, String subKey, String nameKey, String valueKey)
	{
		Map<String, String> parameters = new HashMap<String, String>();
		for(HierarchicalConfiguration subConfig : (List<HierarchicalConfiguration>)config.configurationsAt(subKey)){
			String name = subConfig.getString(nameKey);
			String value = subConfig.getString(valueKey);
			parameters.put(name, value);
		}
		return parameters;
	}

	/**
	 * 把一个 <code>Map</code> 对象的内容更新到配置对象中
	 * @param config 配置对象
	 * @param subKey 子项配置键值
	 * @param nameKey 名字配置键值
	 * @param valueKey 值配置键值
	 * @param parameters <code>Map</code> 对象
	 */
	public static void updateParameter(HierarchicalConfiguration config, String subKey, String nameKey, String valueKey, Map<String, String> parameters)
	{
		for(HierarchicalConfiguration subConfig : (List<HierarchicalConfiguration>)config.configurationsAt(subKey)){
			subConfig.clear();
		}
		nameKey = subKey + "(-1)." + nameKey;
		valueKey = subKey + "." + valueKey;
		for(Map.Entry<String, String> parameter : parameters.entrySet()){
			config.addProperty(nameKey, parameter.getKey());
			config.addProperty(valueKey, parameter.getValue());
		}
	}

	/**
	 * 从一个配置对象中抽取信息转换为 <code>Map</code> 对象
	 * @param config 配置对象
	 * @return <code>Map</code> 对象
	 */
	public static Map<String, String> parseAttributes(HierarchicalConfiguration config)
	{
		Map<String, String> parameters = new HashMap<String, String>();
		for(String key : new IterableIterator<String>(config.getKeys())){
			String value = config.getString(key);
			parameters.put(key, value);
		}
		return parameters;
	}

	/**
	 * 列出所有配置对象的名字
	 * @param config 配置对象
	 * @param subKey 子项配置键值
	 * @param nameKey 名字配置键值
	 * @return 所有配置对象的名字列表
	 */
	public static List<String> listAllNames(HierarchicalConfiguration config, String subKey, String nameKey)
	{
		List<String> names = new ArrayList<String>();
		for(HierarchicalConfiguration subConfig : (List<HierarchicalConfiguration>)config.configurationsAt(subKey)){
			String name = subConfig.getString(nameKey);
			names.add(name);
		}
		return names;
	}

	/**
	 * 从多个配置对象中找出符合给定名字的配置对象。
	 * @param config 配置对象
	 * @param subKey 子项配置键值
	 * @param nameKey 名字配置键值
	 * @param nameValue 属性值
	 * @throws ConfigurationException 找不到指定配置
	 * @return 匹配的配置对象
	 */
	public static HierarchicalConfiguration findForName(HierarchicalConfiguration config, String subKey, String nameKey, String nameValue)
		throws ConfigurationException
	{
		for(HierarchicalConfiguration subConfig : (List<HierarchicalConfiguration>)config.configurationsAt(subKey)){
			String name = subConfig.getString(nameKey);
			if(name.equals(nameValue)){
				return subConfig;
			}
		}
		// 没有找到
		throw new ConfigurationException("未找到属性 " + nameKey + " 的值为 " + nameValue + " 的 " + subKey + " 配置节点");
	}
}
