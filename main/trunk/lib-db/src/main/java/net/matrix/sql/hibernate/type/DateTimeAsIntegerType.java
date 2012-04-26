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
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * 将数据库中的整形值作为日期时间值处理的类型。
 */
public class DateTimeAsIntegerType
	implements UserType, ParameterizedType {
	/**
	 * 仅有日期的格式。
	 */
	private static final String DATE_ONLY = "yyyyMMdd";

	/**
	 * 日期格式。
	 */
	private String format;

	/**
	 * 默认构造器，识别数据为仅有日期的格式。
	 */
	public DateTimeAsIntegerType() {
		this.format = DATE_ONLY;
	}

	@Override
	public int[] sqlTypes() {
		return new int[] {
			Types.INTEGER
		};
	}

	@Override
	public Class returnedClass() {
		return DateTime.class;
	}

	@Override
	public boolean equals(final Object x, final Object y)
		throws HibernateException {
		if (x == y) {
			return true;
		}
		if (x == null || y == null) {
			return false;
		}
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
		if (rs.wasNull()) {
			return null;
		}

		String value = Integer.toString(r);
		if (value.length() < format.length()) {
			value = StringUtils.repeat('0', format.length() - value.length()) + value;
		}
		DateTimeFormatter formatter = DateTimeFormat.forPattern(format);
		return formatter.parseDateTime(value);
	}

	@Override
	public void nullSafeSet(final PreparedStatement st, final Object value, final int index, final SessionImplementor session)
		throws HibernateException, SQLException {
		if (value == null) {
			st.setNull(index, Types.INTEGER);
			return;
		}
		DateTime date = (DateTime) value;
		DateTimeFormatter formatter = DateTimeFormat.forPattern(format);
		int intDate = Integer.parseInt(formatter.print(date));
		st.setInt(index, intDate);
	}

	@Override
	public Object deepCopy(final Object value)
		throws HibernateException {
		if (value == null) {
			return null;
		}
		return new DateTime(value);
	}

	@Override
	public boolean isMutable() {
		return true;
	}

	@Override
	public Serializable disassemble(final Object value)
		throws HibernateException {
		return (Serializable) deepCopy(value);
	}

	@Override
	public Object assemble(final Serializable cached, final Object owner)
		throws HibernateException {
		return deepCopy(cached);
	}

	@Override
	public Object replace(final Object original, final Object target, final Object owner)
		throws HibernateException {
		return deepCopy(original);
	}

	@Override
	public void setParameterValues(final Properties parameters) {
		if (parameters == null) {
			return;
		}
		String newFormat = parameters.getProperty("format");
		if (newFormat == null) {
			return;
		}
		format = newFormat;
	}
}
