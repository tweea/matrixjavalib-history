/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.sql.hibernate;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;

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

	private static Map<String, SessionFactoryManager> instances = new HashMap<String, SessionFactoryManager>();

	private String factoryName;

	private Configuration configuration;

	private SessionFactory sessionFactory;

	private HibernateTransactionContextManager contextManager;

	/**
	 * @return 默认实例
	 */
	public static SessionFactoryManager getInstance()
	{
		SessionFactoryManager instance = instances.get(DEFAULT_NAME);
		if(instance == null){
			instance = new SessionFactoryManager(DEFAULT_NAME);
			instances.put(DEFAULT_NAME, instance);
		}
		return instance;
	}

	/**
	 * @return 默认实例
	 */
	public static SessionFactoryManager getInstance(String name)
	{
		if(DEFAULT_NAME.equals(name)){
			return getInstance();
		}
		if(!isNameUsed(name)){
			throw new IllegalStateException("名称 " + name + " 没有命名");
		}
		return instances.get(name);
	}

	/**
	 * 判断 SessionFactory 名称是否已被占用
	 * @param name SessionFactory 名称
	 */
	public static boolean isNameUsed(String name)
	{
		return instances.containsValue(name);
	}

	/**
	 * 命名一个配置文件到指定名称
	 * @param name SessionFactory 名称
	 * @param configResourceName SessionFactory 配置资源
	 */
	public static void nameSessionFactory(String name, String configResourceName)
		throws HibernateException
	{
		synchronized(instances){
			if(isNameUsed(name)){
				throw new IllegalStateException("名称 " + name + " 已被占用");
			}
			instances.put(name, new SessionFactoryManager(name, configResourceName));
		}
	}

	private SessionFactoryManager(String name)
		throws HibernateException
	{
		this.factoryName = name;
		LOG.info("读取默认的 Hibernate 配置。");
		this.configuration = new Configuration().configure();
		this.contextManager = new HibernateTransactionContextManager(name);
	}

	private SessionFactoryManager(String name, String configResourceName)
		throws HibernateException
	{
		this.factoryName = name;
		LOG.info("读取 " + configResourceName + "的 Hibernate 配置。");
		this.configuration = new Configuration().configure(configResourceName);
		this.contextManager = new HibernateTransactionContextManager(name);
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
		contextManagers = new HashMap<String, HibernateTransactionContextManager>();
		names.add(DEFAULT_NAME);
	}

	/**
	 * 获取默认 SessionFactory 配置
	 * @return 默认 SessionFactory 配置
	 * @throws HibernateException 配置失败
	 */
	public Configuration getConfiguration()
	{
		return configuration;
	}

	/**
	 * 使用默认 SessionFactory 建立 Session
	 * @return 新建的 Session
	 * @throws HibernateException 建立失败
	 */
	public Session createSession()
		throws HibernateException
	{
		if(sessionFactory == null){
			LOG.info("以 " + factoryName + " 的配置构建 Hibernate SessionFactory。");
			sessionFactory = configuration.buildSessionFactory();
		}
		return sessionFactory.openSession();
	}

	/**
	 * 关闭默认的 SessionFactory
	 */
	public void closeSessionFactory()
		throws HibernateException
	{
		if(sessionFactory != null){
			sessionFactory.close();
			LOG.info(factoryName + " 配置的 Hibernate SessionFactory 已关闭。");
		}
	}

	/**
	 * 获取默认事务上下文管理器
	 * @return 默认事务上下文管理器
	 */
	public HibernateTransactionContextManager getContextManager()
	{
		return contextManager;
	}

	/**
	 * 获取默认 SessionFactory 相关连接信息
	 * @return 连接信息
	 * @throws SQLException 获取失败
	 */
	public DatabaseConnectionInfo getConnectionInfo()
		throws SQLException
	{
		Properties properties = configuration.getProperties();
		DatabaseConnectionInfo info = new DatabaseConnectionInfo(properties.getProperty(AvailableSettings.DRIVER),
			properties.getProperty(AvailableSettings.URL), properties.getProperty(AvailableSettings.USER), properties.getProperty(AvailableSettings.PASS));
		return info;
	}
}
