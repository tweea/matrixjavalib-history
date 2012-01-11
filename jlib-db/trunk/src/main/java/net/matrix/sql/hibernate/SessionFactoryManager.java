/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.sql.hibernate;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.matrix.lang.Resettable;
import net.matrix.sql.DatabaseConnectionInfo;

/**
 * Hibernate SessionFactory 管理器。
 */
public class SessionFactoryManager
	implements Resettable
{
	/**
	 * 默认的 SessionFactory 名称
	 */
	public final static String DEFAULT_NAME = "";

	private static final Logger LOG = LoggerFactory.getLogger(SessionFactoryManager.class);

	private static SessionFactoryManager instance = null;

	private Set<String> names;

	private HashMap<String, Configuration> configurations;

	private HashMap<String, SessionFactory> sessionFactorys;

	private SessionFactoryManager()
	{
		names = new HashSet<String>();
		configurations = new HashMap<String, Configuration>();
		sessionFactorys = new HashMap<String, SessionFactory>();
		names.add(DEFAULT_NAME);
	}

	/**
	 * @return 唯一实例
	 */
	public static synchronized SessionFactoryManager getInstance()
	{
		if(instance == null){
			instance = new SessionFactoryManager();
		}
		return instance;
	}

	/**
	 * 删除所有 SessionFactory 配置
	 * @see net.matrix.lang.Resettable#reset()
	 */
	@Override
	public void reset()
	{
		for(SessionFactory factory : sessionFactorys.values()){
			factory.close();
		}
		names = new HashSet<String>();
		configurations = new HashMap<String, Configuration>();
		sessionFactorys = new HashMap<String, SessionFactory>();
		names.add(DEFAULT_NAME);
	}

	/**
	 * 判断 SessionFactory 名称是否已被占用
	 * @param name SessionFactory 名称
	 */
	public boolean isNameUsed(String name)
	{
		return names.contains(name);
	}

	private void checkName(String name)
	{
		if(!isNameUsed(name)){
			throw new IllegalStateException("名称 " + name + " 没有命名");
		}
	}

	/**
	 * 命名一个配置文件到指定名称
	 * @param name SessionFactory 名称
	 * @param configResourceName SessionFactory 配置资源
	 */
	public void nameSessionFactory(String name, String configResourceName)
		throws HibernateException
	{
		if(isNameUsed(name)){
			throw new IllegalStateException("名称 " + name + " 已被占用");
		}
		synchronized(this){
			LOG.info("读取 " + configResourceName + "的 Hibernate 配置。");
			Configuration configuration = new Configuration().configure(configResourceName);
			names.add(name);
			configurations.put(name, configuration);
		}
	}

	/**
	 * 获取默认 SessionFactory 配置
	 * @return 默认 SessionFactory 配置
	 * @throws HibernateException 配置失败
	 */
	public Configuration getConfiguration()
		throws HibernateException
	{
		Configuration configuration = configurations.get(DEFAULT_NAME);
		if(configuration == null){
			synchronized(this){
				LOG.info("读取默认的 Hibernate 配置。");
				configuration = new Configuration().configure();
				configurations.put(DEFAULT_NAME, configuration);
			}
		}
		return configuration;
	}

	/**
	 * 获取 SessionFactory 配置
	 * @param name SessionFactory 名称
	 * @return SessionFactory 配置
	 * @throws HibernateException 配置失败
	 */
	public Configuration getConfiguration(String name)
		throws HibernateException
	{
		if(DEFAULT_NAME.equals(name)){
			return getConfiguration();
		}
		checkName(name);
		return configurations.get(name);
	}

	/**
	 * 使用默认 SessionFactory 建立 Session
	 * @return 新建的 Session
	 * @throws HibernateException 建立失败
	 */
	public Session createSession()
		throws HibernateException
	{
		return createSession(DEFAULT_NAME);
	}

	/**
	 * 使用 SessionFactory 建立 Session
	 * @param name SessionFactory 名称
	 * @return 新建的 Session
	 * @throws HibernateException 建立失败
	 */
	public Session createSession(String name)
		throws HibernateException
	{
		checkName(name);
		SessionFactory factory = sessionFactorys.get(name);
		if(factory == null){
			synchronized(this){
				LOG.info("以 " + name + " 的配置构建 Hibernate SessionFactory。");
				factory = getConfiguration(name).buildSessionFactory();
				sessionFactorys.put(name, factory);
			}
		}
		return factory.openSession();
	}

	/**
	 * 关闭默认的 SessionFactory
	 */
	public void closeSessionFactory()
	{
		closeSessionFactory(DEFAULT_NAME);
	}

	/**
	 * 关闭 SessionFactory
	 * @param name SessionFactory 名称
	 */
	public void closeSessionFactory(String name)
	{
		checkName(name);
		SessionFactory factory = sessionFactorys.get(name);
		if(factory != null){
			synchronized(this){
				factory.close();
				sessionFactorys.remove(name);
				LOG.info(name + " 配置的 Hibernate SessionFactory 已关闭。");
			}
		}
	}

	/**
	 * 获取默认 SessionFactory 相关连接信息
	 * @return 连接信息
	 * @throws SQLException 获取失败
	 */
	public DatabaseConnectionInfo getConnectionInfo()
		throws SQLException
	{
		return getConnectionInfo(getConfiguration());
	}

	/**
	 * 获取 SessionFactory 相关连接信息
	 * @param name SessionFactory 名称
	 * @return 连接信息
	 * @throws SQLException 获取失败
	 */
	public DatabaseConnectionInfo getConnectionInfo(String name)
		throws SQLException
	{
		return getConnectionInfo(getConfiguration(name));
	}

	private DatabaseConnectionInfo getConnectionInfo(Configuration conf)
		throws SQLException
	{
		Properties properties = conf.getProperties();
		DatabaseConnectionInfo info = new DatabaseConnectionInfo(properties.getProperty(AvailableSettings.DRIVER),
			properties.getProperty(AvailableSettings.URL), properties.getProperty(AvailableSettings.USER), properties.getProperty(AvailableSettings.PASS));
		return info;
	}
}
