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
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import net.matrix.lang.Resettable;
import net.matrix.sql.DatabaseConnectionInfo;

public class SessionFactoryManager
	implements Resettable
{
	public final static String DEFAULT_NAME = "";

	private static final Log LOG = LogFactory.getLog(SessionFactoryManager.class);

	private static SessionFactoryManager base = null;

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

	public static synchronized SessionFactoryManager getInstance()
	{
		if(base == null){
			base = new SessionFactoryManager();
		}
		return base;
	}

	/*
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

	/*
	 * 判断名称是否已被占用
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

	/*
	 * 命名一个配置文件到指定名称
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

	public Configuration getConfiguration(String name)
		throws HibernateException
	{
		if(DEFAULT_NAME.equals(name)){
			return getConfiguration();
		}
		checkName(name);
		return configurations.get(name);
	}

	public Session createSession()
		throws HibernateException
	{
		return createSession(DEFAULT_NAME);
	}

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

	public void closeSessionFactory()
	{
		closeSessionFactory(DEFAULT_NAME);
	}

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

	public DatabaseConnectionInfo getConnectionInfo()
		throws SQLException
	{
		return getConnectionInfo(getConfiguration());
	}

	public DatabaseConnectionInfo getConnectionInfo(String configResourceName)
		throws SQLException
	{
		return getConnectionInfo(getConfiguration(configResourceName));
	}

	private DatabaseConnectionInfo getConnectionInfo(Configuration conf)
		throws SQLException
	{
		Map properties = conf.getProperties();
		DatabaseConnectionInfo info = new DatabaseConnectionInfo((String)properties.get(Environment.DRIVER), (String)properties.get(Environment.URL),
			(String)properties.get(Environment.USER), (String)properties.get(Environment.PASS));
		return info;
	}
}
