package net.matrix.app.message;

import java.io.IOException;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * 读取编码消息记录定义
 */
public class CodedMessageDefinitionLoader
{
	private static final Log LOG = LogFactory.getLog(CodedMessageDefinitionLoader.class);

	public static void loadDefinitions(ResourceLoader loader)
	{
		try{
			ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(loader);
			Resource[] resources = resolver.getResources("classpath*:codedMessageDefinition.xml");
			for(Resource resource : resources){
				loadDefinitions(resource);
			}
		}catch(IOException e){
			LOG.error("加载失败", e);
		}
	}

	public static void loadDefinitions(Resource resource)
	{
		try{
			XMLConfiguration config = new XMLConfiguration();
			config.setDelimiterParsingDisabled(true);
			config.load(resource.getInputStream());
			for(HierarchicalConfiguration definitionConfig : (List<HierarchicalConfiguration>)config.configurationsAt("definition")){
				String code = definitionConfig.getString("[@code]");
				String template = definitionConfig.getString("[@template]");
				CodedMessageDefinition.define(new CodedMessageDefinition(code, template));
			}
		}catch(IOException e){
			LOG.error("加载失败", e);
		}catch(ConfigurationException e){
			LOG.error("加载失败", e);
		}
	}
}
