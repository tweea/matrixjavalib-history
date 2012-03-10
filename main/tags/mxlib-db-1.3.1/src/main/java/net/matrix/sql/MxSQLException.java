/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.sql;

import java.sql.SQLException;

/**
 * 数据库异常，支持 Java 6 中的构造器
 * @author Tweea
 * @since 2005.06.15
 */
public class MxSQLException
	extends SQLException
{
	private static final long serialVersionUID = -8096256134781767328L;

	public MxSQLException()
	{
		super();
	}

	public MxSQLException(String msg)
	{
		super(msg);
	}

	public MxSQLException(Throwable e)
	{
		super();
		if(getCause() == null){
			initCause(e);
		}
	}

	public MxSQLException(String msg, Throwable e)
	{
		super(msg);
		if(getCause() == null){
			initCause(e);
		}
	}
}
