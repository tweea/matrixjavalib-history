/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.sql;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数据库连接信息。
 */
public class DatabaseConnectionInfo
	implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -7842286530934311836L;

	/**
	 * 日志记录器。
	 */
	private static final Logger LOG = LoggerFactory.getLogger(DatabaseConnectionInfo.class);

	// 连接信息
	private String driverClass = null;

	private String url = null;

	private String userName = null;

	private String password = null;

	// 元数据
	private String databaseType = null;

	private String driverName = null;

	public DatabaseConnectionInfo(String driverClass, String url, String userName, String password)
		throws SQLException {
		this.driverClass = driverClass;
		this.url = url;
		this.userName = userName;
		this.password = password;
		setMetaData();
	}

	public String getDriverClass() {
		return driverClass;
	}

	public String getUrl() {
		return url;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	/**
	 * 获得数据库类型。
	 * 
	 * @return 数据库类型
	 */
	public String getDatabaseType() {
		return databaseType;
	}

	public String getDriverName() {
		return driverName;
	}

	/**
	 * 使用数据库连接信息建立一个连接。
	 * 
	 * @return 新建的连接。
	 * @throws SQLException
	 *             找不到驱动类或建立连接失败
	 */
	public Connection getJDBCConnection()
		throws SQLException {
		try {
			Class.forName(driverClass);
			return DriverManager.getConnection(url, userName, password);
		} catch (ClassNotFoundException e) {
			throw new SQLException(e);
		}
	}

	/**
	 * 从数据库读取元数据。
	 * 
	 * @throws SQLException
	 *             建立连接失败或读取信息失败
	 */
	private void setMetaData()
		throws SQLException {
		Connection connection = getJDBCConnection();
		try {
			DatabaseMetaData metadata = connection.getMetaData();
			databaseType = metadata.getDatabaseProductName();
			driverName = metadata.getDriverName();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				LOG.warn("关闭连接失败", e);
			}
		}
	}
}
