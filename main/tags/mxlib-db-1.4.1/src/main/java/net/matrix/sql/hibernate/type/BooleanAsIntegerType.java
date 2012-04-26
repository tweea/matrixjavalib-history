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

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

/**
 * 将数据库中的整形值作为布尔值处理的类型。
 */
public class BooleanAsIntegerType
	implements UserType {
	/**
	 * 默认构造器。
	 */
	public BooleanAsIntegerType() {
	}

	@Override
	public int[] sqlTypes() {
		return new int[] {
			Types.INTEGER
		};
	}

	@Override
	public Class returnedClass() {
		return Boolean.class;
	}

	@Override
	public boolean equals(final Object x, final Object y)
		throws HibernateException {
		return x.equals(y);
	}

	@Override
	public int hashCode(final Object x)
		throws HibernateException {
		return x.hashCode();
	}

	@Override
	public Object nullSafeGet(final ResultSet rs, final String[] names, final SessionImplementor session, final Object owner)
		throws HibernateException, SQLException {
		int r = rs.getInt(names[0]);
		return r != 0;
	}

	@Override
	public void nullSafeSet(final PreparedStatement st, final Object value, final int index, final SessionImplementor session)
		throws HibernateException, SQLException {
		if (value == null) {
			st.setNull(index, Types.INTEGER);
		} else {
			Boolean v = (Boolean) value;
			st.setInt(index, v ? 1 : 0);
		}
	}

	@Override
	public Object deepCopy(final Object value)
		throws HibernateException {
		return value;
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public Serializable disassemble(final Object value)
		throws HibernateException {
		return (Serializable) value;
	}

	@Override
	public Object assemble(final Serializable cached, final Object owner)
		throws HibernateException {
		return cached;
	}

	@Override
	public Object replace(final Object original, final Object target, final Object owner)
		throws HibernateException {
		return original;
	}
}
