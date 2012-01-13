/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.sql.hibernate.type;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;

public class DateTimeAsIntegerType
	implements UserType, ParameterizedType
{
	private final static int[] TYPES = new int[]{
		Types.INTEGER
	};

	private final static String[] FORMATS = {
		"yyyyMMdd", "HHmmss"
	};

	private final static int yyyyMMdd = 0;

	private final static int HHmmss = 1;

	private int formatIndex;

	@Override
	public int[] sqlTypes()
	{
		return TYPES;
	}

	@Override
	public Class returnedClass()
	{
		return GregorianCalendar.class;
	}

	@Override
	public boolean equals(Object x, Object y)
		throws HibernateException
	{
		if(x == y){
			return true;
		}
		if(x == null || y == null){
			return false;
		}
		return x.equals(y);
	}

	@Override
	public int hashCode(Object x)
		throws HibernateException
	{
		return x.hashCode();
	}

	@Override
	public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner)
		throws HibernateException, SQLException
	{
		int r = rs.getInt(names[0]);
		if(rs.wasNull()){
			return null;
		}
		GregorianCalendar date = null;
		if(formatIndex == yyyyMMdd){
			// 0 相当于 NULL
			if(r == 0){
				return null;
			}
			int year = 0, month = 0, day = 0;
			day = r % 100;
			r -= day;
			r /= 100;
			month = r % 100;
			r -= month;
			r /= 100;
			year = r;
			date = new GregorianCalendar(year, month - 1, day);
		}else if(formatIndex == HHmmss){
			int hour = 0, minute = 0, second = 0;
			second = r % 100;
			r -= second;
			r /= 100;
			minute = r % 100;
			r -= minute;
			r /= 100;
			hour = r;
			date = new GregorianCalendar(1900, 1, 1, hour, minute, second);
		}
		return date;
	}

	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session)
		throws HibernateException, SQLException
	{
		if(value == null){
			st.setNull(index, Types.INTEGER);
			return;
		}
		GregorianCalendar date = (GregorianCalendar)value;
		int intDate = 0;
		if(formatIndex == yyyyMMdd){
			intDate = date.get(Calendar.YEAR) * 10000;
			intDate += date.get(Calendar.MONTH) * 100 + 100;
			intDate += date.get(Calendar.DAY_OF_MONTH);
		}else if(formatIndex == HHmmss){
			intDate = date.get(Calendar.HOUR_OF_DAY) * 10000;
			intDate += date.get(Calendar.MINUTE) * 100;
			intDate += date.get(Calendar.SECOND);
		}
		st.setInt(index, intDate);
	}

	@Override
	public Object deepCopy(Object value)
		throws HibernateException
	{
		if(value == null){
			return null;
		}
		return ((GregorianCalendar)value).clone();
	}

	@Override
	public boolean isMutable()
	{
		return true;
	}

	@Override
	public Serializable disassemble(Object value)
		throws HibernateException
	{
		return (Serializable)deepCopy(value);
	}

	@Override
	public Object assemble(Serializable cached, Object owner)
		throws HibernateException
	{
		return deepCopy(cached);
	}

	@Override
	public Object replace(Object original, Object target, Object owner)
		throws HibernateException
	{
		return deepCopy(original);
	}

	@Override
	public void setParameterValues(Properties parameters)
	{
		if(parameters == null){
			formatIndex = yyyyMMdd;
			return;
		}
		String format = (String)parameters.get("format");
		if(format == null){
			formatIndex = yyyyMMdd;
			return;
		}
		for(int i = 0; i < FORMATS.length; i++){
			if(FORMATS[i].equals(format)){
				formatIndex = i;
				break;
			}
		}
	}
}
