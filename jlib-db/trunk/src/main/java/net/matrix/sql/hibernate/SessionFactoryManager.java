/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.sql.hibernate;

import java.sql.SQLException;
import java.util.HashMap;
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
// TODO 延迟加载配置
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

	private ThreadLocal<HibernateTransactionContext> threadContext;

	/**
	 * @return 默认实例
	 */
	public static SessionFactoryManager getInstance()
		throws HibernateException
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
		throws HibernateException
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
	 * 命名默认配置文件到指定名称
	 * @param name SessionFactory 名称
	 */
	public static void nameSessionFactory(String name)
		throws HibernateException
	{
		synchronized(instances){
			if(isNameUsed(name)){
				throw new IllegalStateException("名称 " + name + " 已被占用");
			}
			instances.put(name, new SessionFactoryManager(name));
		}
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

	/**
	 * 清除所有 SessionFactory 配置
	 */
	public static void clearAll()
	{
		resetAll();
		instances.clear();
	}

	/**
	 * 重置所有 SessionFactory 配置
	 */
	public static void resetAll()
	{
		for(SessionFactoryManager instance : instances.values()){
			instance.reset();
		}
	}

	private SessionFactoryManager(String name)
		throws HibernateException
	{
		this.factoryName = name;
		LOG.info("读取默认的 Hibernate 配置。");
		this.configuration = new Configuration().configure();
		this.threadContext = new ThreadLocal<HibernateTransactionContext>();
	}

	private SessionFactoryManager(String name, String configResource)
		throws HibernateException
	{
		this.factoryName = name;
		LOG.info("读取 " + configResource + "的 Hibernate 配置。");
		this.configuration = new Configuration().configure(configResource);
		this.threadContext = new ThreadLocal<HibernateTransactionContext>();
	}

	/**
	 * 关闭 SessionFactory
	 * @see net.matrix.lang.Resettable#reset()
	 */
	@Override
	public void reset()
	{
		if(sessionFactory == null){
			return;
		}
		try{
			sessionFactory.close();
			LOG.info(factoryName + " 配置的 Hibernate SessionFactory 已关闭。");
		}catch(HibernateException e){
			LOG.error(factoryName + " 配置的 Hibernate SessionFactory 关闭失败。");
		}finally{
			sessionFactory = null;
		}
	}

	/**
	 * 获取 SessionFactory 配置
	 * @return SessionFactory 配置
	 */
	public Configuration getConfiguration()
	{
		return configuration;
	}

	/**
	 * 获取 SessionFactory
	 * @return SessionFactory
	 */
	public SessionFactory getSessionFactory()
		throws HibernateException
	{
		if(sessionFactory == null){
			if(DEFAULT_NAME.equals(factoryName)){
				LOG.info("以默认配置构建 Hibernate SessionFactory。");
			}else{
				LOG.info("以 " + factoryName + " 配置构建 Hibernate SessionFactory。");
			}
			sessionFactory = configuration.buildSessionFactory();
		}
		return sessionFactory;
	}

	/**
	 * 使用 SessionFactory 建立 Session
	 * @return 新建的 Session
	 * @throws HibernateException 建立失败
	 */
	public Session createSession()
		throws HibernateException
	{
		return getSessionFactory().openSession();
	}

	/**
	 * 获取当前顶层事务上下文，没有则建立
	 * @return 当前顶层事务上下文
	 */
	public HibernateTransactionContext getTransactionContext()
	{
		HibernateTransactionContext context = threadContext.get();
		if(context == null){
			context = new HibernateTransactionContext(factoryName);
			threadContext.set(context);
		}
		return context;
	}

	/**
	 * 丢弃顶层事务上下文
	 * @throws SQLException 回滚发生错误
	 */
	public void dropTransactionContext()
		throws SQLException
	{
		HibernateTransactionContext context = threadContext.get();
		if(context == null){
			return;
		}
		threadContext.set(null);
		try{
			context.rollback();
		}finally{
			context.release();
		}
	}

	/**
	 * 获取 SessionFactory 相关连接信息
	 * @return 连接信息
	 * @throws SQLException 信息获取失败
	 */
	public DatabaseConnectionInfo getConnectionInfo()
		throws SQLException
	{
		Properties properties = configuration.getProperties();
		String driver = properties.getProperty(AvailableSettings.DRIVER);
		String url = properties.getProperty(AvailableSettings.URL);
		String user = properties.getProperty(AvailableSettings.USER);
		String pass = properties.getProperty(AvailableSettings.PASS);
		return new DatabaseConnectionInfo(driver, url, user, pass);
	}
}
