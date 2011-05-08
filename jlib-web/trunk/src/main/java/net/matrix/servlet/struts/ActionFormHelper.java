/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.servlet.struts;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;

public class ActionFormHelper
{
	public static void setFormProperty(ActionForm form, String property, String value)
		throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
	{
		PropertyUtils.setSimpleProperty(form, property, value);
	}

	public static String getFormProperty(ActionForm form, String property)
		throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
	{
		return (String)PropertyUtils.getSimpleProperty(form, property);
	}

	public static String getFormProperty(ActionForm form, String property, String charset)
		throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, UnsupportedEncodingException
	{
		return (String)PropertyUtils.getSimpleProperty(form, property);
	}
}
