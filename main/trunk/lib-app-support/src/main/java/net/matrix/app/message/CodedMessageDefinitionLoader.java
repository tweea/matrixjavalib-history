/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.app.message;

import java.io.IOException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * 读取编码消息记录定义。
 */
public final class CodedMessageDefinitionLoader {
	/**
	 * 日志记录器。
	 */
	private static final Logger LOG = LoggerFactory.getLogger(CodedMessageDefinitionLoader.class);

	/**
	 * 从类路径中加载所有名为 codedMessageDefinition.xml 的配置文件。
	 * 
	 * @param resolver
	 *            资源加载策略
	 */
	public static void loadDefinitions(final ResourcePatternResolver resolver) {
		try {
			Resource[] resources = resolver.getResources("classpath*:codedMessageDefinition*.xml");
			for (Resource resource : resources) {
				loadDefinitions(resource);
			}
		} catch (IOException e) {
			LOG.error("加载失败", e);
		}
	}

	/**
	 * 从特定位置加载配置文件。
	 * 
	 * @param resource
	 *            配置文件位置
	 */
	public static void loadDefinitions(final Resource resource) {
		try {
			XMLConfiguration config = new XMLConfiguration();
			config.setDelimiterParsingDisabled(true);
			config.load(resource.getInputStream());
			for (HierarchicalConfiguration definitionConfig : config.configurationsAt("definition")) {
				String code = definitionConfig.getString("[@code]");
				String template = definitionConfig.getString("[@template]");
				CodedMessageDefinition.define(new CodedMessageDefinition(code, template));
			}
		} catch (IOException e) {
			LOG.error("加载失败", e);
		} catch (ConfigurationException e) {
			LOG.error("加载失败", e);
		}
	}
}
