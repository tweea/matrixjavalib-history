/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.servlet.struts.taglib;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.matrix.text.resources.Resources;

/**
 * 显示表单中的资源字符串。
 */
public class ResourceTag
	extends TagSupport
{
	private static final long serialVersionUID = 726145998718973331L;

	private static final Log LOG = LogFactory.getLog(ResourceTag.class);

	protected String name;

	protected String property;

	protected String resource;

	public void setProperty(String property)
	{
		this.property = property;
	}

	public String getProperty()
	{
		return property;
	}

	public void setResource(String resource)
	{
		this.resource = resource;
	}

	public String getResource()
	{
		return resource;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	@Override
	public int doStartTag()
		throws JspException
	{
		Object bean = pageContext.findAttribute(name);
		if(bean == null){
			return 0;
		}
		Object value;
		try{
			value = PropertyUtils.getProperty(bean, property);
		}catch(IllegalAccessException e){
			throw new JspException(e);
		}catch(InvocationTargetException e){
			throw new JspException(e);
		}catch(NoSuchMethodException e){
			throw new JspException(e);
		}
		if(value == null){
			return 0;
		}
		JspWriter writer = pageContext.getOut();
		try{
			writer.print(Resources.getResources(resource).getProperty(value.toString()));
		}catch(IOException e){
			LOG.error("", e);
		}
		return 0;
	}
}
