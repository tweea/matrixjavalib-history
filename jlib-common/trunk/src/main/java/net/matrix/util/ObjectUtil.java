/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.util;

import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.matrix.data.BusinessDate;
import net.matrix.text.DateFormatHelper;

public abstract class ObjectUtil
{
	private final static Log LOG = LogFactory.getLog(ObjectUtil.class);

	public static <T> T isNull(T value, T replacement)
	{
		return value == null ? replacement : value;
	}

	public static <T> T nullIf(T value, T value2)
	{
		if(value == null || value2 == null){
			return null;
		}
		return value.equals(value2) ? null : value;
	}

	public static Object getValue(Object obj, String fieldName)
	{
		PropertyDescriptor pd;
		try{
			pd = PropertyUtils.getPropertyDescriptor(obj, fieldName);
		}catch(IllegalAccessException e){
			LOG.error("", e);
			return null;
		}catch(InvocationTargetException e){
			LOG.error("", e);
			return null;
		}catch(NoSuchMethodException e){
			LOG.error("", e);
			return null;
		}
		PropertyEditor pe = pd.createPropertyEditor(obj);
		return pe.getValue();
	}

	public static void setValue(Object obj, String fieldName, Object value)
		throws Exception
	{
		try{
			PropertyDescriptor pd;
			try{
				pd = PropertyUtils.getPropertyDescriptor(obj, fieldName);
			}catch(IllegalAccessException e){
				LOG.error("", e);
				return;
			}catch(InvocationTargetException e){
				LOG.error("", e);
				return;
			}catch(NoSuchMethodException e){
				LOG.error("", e);
				return;
			}
			PropertyEditor pe = pd.createPropertyEditor(obj);
			if(String.class.isInstance(value)){
				String fieldType = pd.getPropertyType().getSimpleName();
				String v = (String)value;
				if("int".equals(fieldType)){
					pe.setValue(Integer.parseInt(v));
				}else if("long".equals(fieldType)){
					pe.setValue(new Long(v));
				}else if("float".equals(fieldType)){
					pe.setValue(Float.parseFloat(v));
				}else if("Calendar".equals(fieldType)){
					pe.setValue(BusinessDate.parse(v, DateFormatHelper.ISO_DATE_FORMAT));
				}else if("GregorianCalendar".equals(fieldType)){
					pe.setValue(BusinessDate.parse(v, DateFormatHelper.ISO_DATE_FORMAT));
				}else if("BigDecimal".equals(fieldType)){
					pe.setValue(new BigDecimal(v));
				}else{
					pe.setValue(value);
				}
			}else{
				pe.setValue(value);
			}
		}catch(Exception e){
			LOG.error("", e);
		}
	}
}
