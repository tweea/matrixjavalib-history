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

public class BooleanAsIntegerType
	implements UserType {
	private static final int[] TYPES = new int[] {
		Types.INTEGER
	};

	@Override
	public int[] sqlTypes() {
		return TYPES;
	}

	@Override
	public Class returnedClass() {
		return Boolean.class;
	}

	@Override
	public boolean equals(Object x, Object y)
		throws HibernateException {
		return x.equals(y);
	}

	@Override
	public int hashCode(Object x)
		throws HibernateException {
		return x.hashCode();
	}

	@Override
	public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner)
		throws HibernateException, SQLException {
		int r = rs.getInt(names[0]);
		return r != 0;
	}

	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session)
		throws HibernateException, SQLException {
		if (value == null) {
			st.setNull(index, Types.INTEGER);
		} else {
			Boolean v = (Boolean) value;
			st.setInt(index, v ? 1 : 0);
		}
	}

	@Override
	public Object deepCopy(Object value)
		throws HibernateException {
		return value;
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public Serializable disassemble(Object value)
		throws HibernateException {
		return (Serializable) value;
	}

	@Override
	public Object assemble(Serializable cached, Object owner)
		throws HibernateException {
		return cached;
	}

	@Override
	public Object replace(Object original, Object target, Object owner)
		throws HibernateException {
		return original;
	}
}
