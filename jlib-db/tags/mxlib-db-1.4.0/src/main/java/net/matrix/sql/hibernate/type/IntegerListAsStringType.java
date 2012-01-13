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
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;

public class IntegerListAsStringType
	implements UserType, ParameterizedType
{
	private static final int[] TYPES = new int[]{
		Types.VARCHAR
	};

	private String separator;

	private Pattern pattern;

	private IntegerListAsStringType()
	{
		separator = ",";
		pattern = Pattern.compile(separator);
	}

	@Override
	public int[] sqlTypes()
	{
		return TYPES;
	}

	@Override
	public Class returnedClass()
	{
		return List.class;
	}

	@Override
	public boolean equals(Object x, Object y)
		throws HibernateException
	{
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
		String r = rs.getString(names[0]);
		if(r == null){
			return new ArrayList();
		}
		List<Integer> l = new ArrayList<Integer>();
		String[] list = pattern.split(r);
		for(String item : list){
			l.add(Integer.parseInt(item));
		}
		return l;
	}

	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session)
		throws HibernateException, SQLException
	{
		if(value == null){
			st.setNull(index, Types.VARCHAR);
			return;
		}
		List<Integer> v = (List)value;
		if(v.size() == 0){
			st.setNull(index, Types.VARCHAR);
			return;
		}
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < v.size(); i++){
			if(i != 0){
				sb.append(separator);
			}
			sb.append(v.get(i));
		}
		st.setString(index, sb.toString());
	}

	@Override
	public Object deepCopy(Object value)
		throws HibernateException
	{
		if(value == null){
			return null;
		}
		return new ArrayList((List)value);
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
			return;
		}
		if(parameters.get("separator") != null){
			this.separator = parameters.getProperty("separator");
			this.pattern = Pattern.compile(separator);
		}
	}
}
