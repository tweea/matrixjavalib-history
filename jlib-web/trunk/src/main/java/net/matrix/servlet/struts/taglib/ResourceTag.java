/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.servlet.struts.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.taglib.TagUtils;

import net.matrix.text.resources.Resources;

/**
 * 显示表单中的资源字符串。
 */
public class ResourceTag
	extends TagSupport
{
	private static final long serialVersionUID = 726145998718973331L;

	protected String name;

	protected String property;

	protected String resource;

	public ResourceTag()
	{
		resource = null;
		property = null;
		name = null;
	}

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
		Object value = TagUtils.getInstance().lookup(pageContext, name, property, null);
		if(value != null)
			TagUtils.getInstance().write(pageContext, Resources.getResources(resource).getProperty((String)value));
		return 0;
	}
}
