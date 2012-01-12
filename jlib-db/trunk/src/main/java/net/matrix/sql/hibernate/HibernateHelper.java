/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.sql.hibernate;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Query;
import org.hibernate.Session;

import net.matrix.lang.Objects;

/**
 * Hibernate 实用类
 * @since 2005.06.15
 */
public class HibernateHelper
{
	/**
	 * 获得 Hibernate 数据库连接管理对象
	 */
	private static HibernateTransactionContext getTransactionContext()
	{
		return SessionFactoryManager.getInstance().getTransactionContext();
	}

	/**
	 * 获得 Hibernate 数据库连接管理对象
	 */
	private static HibernateTransactionContext getTransactionContext(String sessionFactoryName)
	{
		return SessionFactoryManager.getInstance(sessionFactoryName).getTransactionContext();
	}

	private static Session getSession(HibernateTransactionContext context)
		throws SQLException
	{
		return context.getSession();
	}

	// TODO How to get EntityMode.MAP mode Session?
	private static Session getMapSession(HibernateTransactionContext context)
		throws SQLException
	{
		return context.getSession();
	}

	public static void beginTransaction()
		throws SQLException
	{
		getTransactionContext().begin();
	}

	public static void beginTransaction(String sessionFactoryName)
		throws SQLException
	{
		getTransactionContext(sessionFactoryName).begin();
	}

	public static void commitTransaction()
		throws SQLException
	{
		getTransactionContext().commit();
	}

	public static void commitTransaction(String sessionFactoryName)
		throws SQLException
	{
		getTransactionContext(sessionFactoryName).commit();
	}

	public static void rollbackTransaction()
		throws SQLException
	{
		getTransactionContext().rollback();
	}

	public static void rollbackTransaction(String sessionFactoryName)
		throws SQLException
	{
		getTransactionContext(sessionFactoryName).rollback();
	}

	public static void releaseTransaction()
	{
		getTransactionContext().release();
	}

	public static void releaseTransaction(String sessionFactoryName)
	{
		getTransactionContext(sessionFactoryName).release();
	}

	public static void closeSession()
	{
		getTransactionContext().release();
	}

	public static void closeSession(String sessionFactoryName)
	{
		getTransactionContext(sessionFactoryName).release();
	}

	/**
	 * 向数据库中存储一个对象
	 */
	public static <T> T merge(Session session, T object)
		throws SQLException
	{
		try{
			return (T)session.merge(object);
		}catch(HibernateException e){
			throw new SQLException(e);
		}
	}

	/**
	 * 向数据库中存储一个对象
	 */
	public static <T> T merge(HibernateTransactionContext context, T object)
		throws SQLException
	{
		return merge(getSession(context), object);
	}

	/**
	 * 向数据库中存储一个对象
	 */
	public static <T> T merge(T object)
		throws SQLException
	{
		return merge(getTransactionContext(), object);
	}

	/**
	 * 向数据库中存储一个对象
	 */
	public static <T> T merge(String sessionFactoryName, T object)
		throws SQLException
	{
		return merge(getTransactionContext(sessionFactoryName), object);
	}

	/**
	 * 向数据库中存储一个对象
	 */
	public static Serializable create(Session session, Object object)
		throws SQLException
	{
		try{
			return session.save(object);
		}catch(HibernateException e){
			throw new SQLException(e);
		}
	}

	/**
	 * 向数据库中存储一个对象
	 */
	public static Serializable create(HibernateTransactionContext context, Object object)
		throws SQLException
	{
		return create(getSession(context), object);
	}

	/**
	 * 向数据库中存储一个对象
	 */
	public static Serializable create(Object object)
		throws SQLException
	{
		return create(getTransactionContext(), object);
	}

	/**
	 * 向数据库中存储一个对象
	 */
	public static Serializable create(String sessionFactoryName, Object object)
		throws SQLException
	{
		return create(getTransactionContext(sessionFactoryName), object);
	}

	/**
	 * 向数据库中更新一个对象
	 */
	public static void update(Session session, Object object)
		throws SQLException
	{
		try{
			session.update(object);
		}catch(HibernateException e){
			throw new SQLException(e);
		}
	}

	/**
	 * 向数据库中更新一个对象
	 */
	public static void update(HibernateTransactionContext context, Object object)
		throws SQLException
	{
		update(getSession(context), object);
	}

	/**
	 * 向数据库中更新一个对象
	 */
	public static void update(Object object)
		throws SQLException
	{
		update(getTransactionContext(), object);
	}

	/**
	 * 向数据库中更新一个对象
	 */
	public static void update(String sessionFactoryName, Object object)
		throws SQLException
	{
		update(getTransactionContext(sessionFactoryName), object);
	}

	/**
	 * 向数据库中存储或更新一个对象
	 */
	public static void createOrUpdate(Session session, Object object)
		throws SQLException
	{
		try{
			session.saveOrUpdate(object);
		}catch(HibernateException e){
			throw new SQLException(e);
		}
	}

	/**
	 * 向数据库中存储或更新一个对象
	 */
	public static void createOrUpdate(HibernateTransactionContext context, Object object)
		throws SQLException
	{
		createOrUpdate(getSession(context), object);
	}

	/**
	 * 向数据库中存储或更新一个对象
	 */
	public static void createOrUpdate(Object object)
		throws SQLException
	{
		createOrUpdate(getTransactionContext(), object);
	}

	/**
	 * 向数据库中存储或更新一个对象
	 */
	public static void createOrUpdate(String sessionFactoryName, Object object)
		throws SQLException
	{
		createOrUpdate(getTransactionContext(sessionFactoryName), object);
	}

	/**
	 * 从数据库中删除一个对象
	 */
	public static void delete(Session session, Object object)
		throws SQLException
	{
		try{
			object = session.merge(object);
			session.delete(object);
		}catch(HibernateException e){
			throw new SQLException(e);
		}
	}

	/**
	 * 从数据库中删除一个对象
	 */
	public static void delete(HibernateTransactionContext context, Object object)
		throws SQLException
	{
		delete(getSession(context), object);
	}

	/**
	 * 从数据库中删除一个对象
	 */
	public static void delete(Object object)
		throws SQLException
	{
		delete(getTransactionContext(), object);
	}

	/**
	 * 从数据库中删除一个对象
	 */
	public static void delete(String sessionFactoryName, Object object)
		throws SQLException
	{
		delete(getTransactionContext(sessionFactoryName), object);
	}

	/**
	 * 从数据库中删除一个对象
	 */
	public static void delete(Session session, Class objectClass, Serializable primaryKey)
		throws SQLException
	{
		try{
			Object obj = session.load(objectClass, primaryKey);
			session.delete(obj);
		}catch(HibernateException e){
			throw new SQLException(e);
		}
	}

	/**
	 * 从数据库中删除一个对象
	 */
	public static void delete(HibernateTransactionContext context, Class objectClass, Serializable primaryKey)
		throws SQLException
	{
		delete(getSession(context), objectClass, primaryKey);
	}

	/**
	 * 从数据库中删除一个对象
	 */
	public static void delete(Class objectClass, Serializable primaryKey)
		throws SQLException
	{
		delete(getTransactionContext(), objectClass, primaryKey);
	}

	/**
	 * 从数据库中删除一个对象
	 */
	public static void delete(String sessionFactoryName, Class objectClass, Serializable primaryKey)
		throws SQLException
	{
		delete(getTransactionContext(sessionFactoryName), objectClass, primaryKey);
	}

	/**
	 * 根据类型和主键从数据库中获得一个对象，若没有则返回 null
	 */
	public static <T> T get(Session session, Class<T> objectClass, Serializable primaryKey)
		throws SQLException
	{
		try{
			return (T)session.get(objectClass, primaryKey);
		}catch(HibernateException e){
			throw new SQLException(e);
		}
	}

	/**
	 * 根据类型和主键从数据库中获得一个对象，若没有则返回 null
	 */
	public static <T> T get(HibernateTransactionContext context, Class<T> objectClass, Serializable primaryKey)
		throws SQLException
	{
		return get(getSession(context), objectClass, primaryKey);
	}

	/**
	 * 根据类型和主键从数据库中获得一个对象，若没有则返回 null
	 */
	public static <T> T get(Class<T> objectClass, Serializable primaryKey)
		throws SQLException
	{
		return get(getTransactionContext(), objectClass, primaryKey);
	}

	/**
	 * 根据类型和主键从数据库中获得一个对象，若没有则返回 null
	 */
	public static <T> T get(String sessionFactoryName, Class<T> objectClass, Serializable primaryKey)
		throws SQLException
	{
		return get(getTransactionContext(sessionFactoryName), objectClass, primaryKey);
	}

	/**
	 * 根据类型和主键从数据库中获得一个对象，若没有则返回 null
	 */
	public static Map<String, Object> getAsMap(Session session, Class objectClass, Serializable primaryKey)
		throws SQLException
	{
		try{
			return (Map)session.get(objectClass, primaryKey);
		}catch(HibernateException e){
			throw new SQLException(e);
		}catch(ClassCastException e){
			throw new SQLException(e);
		}
	}

	/**
	 * 根据类型和主键从数据库中获得一个对象，若没有则返回 null
	 */
	public static Map<String, Object> getAsMap(HibernateTransactionContext context, Class objectClass, Serializable primaryKey)
		throws SQLException
	{
		return getAsMap(getMapSession(context), objectClass, primaryKey);
	}

	/**
	 * 根据类型和主键从数据库中获得一个对象，若没有则返回 null
	 */
	public static Map<String, Object> getAsMap(Class objectClass, Serializable primaryKey)
		throws SQLException
	{
		return getAsMap(getTransactionContext(), objectClass, primaryKey);
	}

	/**
	 * 根据类型和主键从数据库中获得一个对象，若没有则返回 null
	 */
	public static Map<String, Object> getAsMap(String sessionFactoryName, Class objectClass, Serializable primaryKey)
		throws SQLException
	{
		return getAsMap(getTransactionContext(sessionFactoryName), objectClass, primaryKey);
	}

	/**
	 * 执行 HQL 语句
	 */
	public static int execute(Session session, String queryString, Object... params)
		throws SQLException
	{
		try{
			Query query = session.createQuery(queryString);
			if(params != null){
				for(int i = 0; i < params.length; i++){
					query.setParameter(HQLBuilder.getParameterName(i), params[i]);
				}
			}
			int effectedRows = query.executeUpdate();
			return effectedRows;
		}catch(HibernateException e){
			throw new SQLException(e);
		}
	}

	/**
	 * 执行 HQL 语句
	 */
	public static int execute(HibernateTransactionContext context, String queryString, Object... params)
		throws SQLException
	{
		return execute(getSession(context), queryString, params);
	}

	/**
	 * 执行 HQL 语句
	 */
	public static int execute(String queryString, Object... params)
		throws SQLException
	{
		return execute(getTransactionContext(), queryString, params);
	}

	/**
	 * 执行 HQL 语句
	 */
	public static int execute(String sessionFactoryName, String queryString, Object... params)
		throws SQLException
	{
		return execute(getTransactionContext(sessionFactoryName), queryString, params);
	}

	/**
	 * 执行 HQL 语句
	 */
	public static int execute(Session session, String queryString, Iterable params)
		throws SQLException
	{
		try{
			Query query = session.createQuery(queryString);
			int i = 0;
			for(Object param : params){
				query.setParameter(HQLBuilder.getParameterName(i++), param);
			}
			int effectedRows = query.executeUpdate();
			return effectedRows;
		}catch(HibernateException e){
			throw new SQLException(e);
		}
	}

	/**
	 * 执行 HQL 语句
	 */
	public static int execute(HibernateTransactionContext context, String queryString, Iterable params)
		throws SQLException
	{
		return execute(getSession(context), queryString, params);
	}

	/**
	 * 执行 HQL 语句
	 */
	public static int execute(String queryString, Iterable params)
		throws SQLException
	{
		return execute(getTransactionContext(), queryString, params);
	}

	/**
	 * 执行 HQL 语句
	 */
	public static int execute(String sessionFactoryName, String queryString, Iterable params)
		throws SQLException
	{
		return execute(getTransactionContext(sessionFactoryName), queryString, params);
	}

	/**
	 * 根据 HQL 查询字符串和参数从数据库中获得对象列表
	 */
	public static int execute(Session session, String queryString, Map<String, ?> params)
		throws SQLException
	{
		try{
			Query query = session.createQuery(queryString);
			for(Map.Entry<String, ? extends Object> paramEntry : params.entrySet()){
				query.setParameter(paramEntry.getKey(), paramEntry.getValue());
			}
			int effectedRows = query.executeUpdate();
			return effectedRows;
		}catch(HibernateException e){
			throw new SQLException(e);
		}
	}

	/**
	 * 根据 HQL 查询字符串和参数从数据库中获得对象列表
	 */
	public static int execute(HibernateTransactionContext context, String queryString, Map<String, ?> params)
		throws SQLException
	{
		return execute(getSession(context), queryString, params);
	}

	/**
	 * 执行 HQL 语句
	 */
	public static int execute(String queryString, Map<String, ?> params)
		throws SQLException
	{
		return execute(getTransactionContext(), queryString, params);
	}

	/**
	 * 执行 HQL 语句
	 */
	public static int execute(String sessionFactoryName, String queryString, Map<String, ?> params)
		throws SQLException
	{
		return execute(getTransactionContext(sessionFactoryName), queryString, params);
	}

	/**
	 * 根据 HQL 查询字符串和参数从数据库中获得对象列表
	 */
	public static List queryAll(HibernateTransactionContext context, String queryString, Object... params)
		throws SQLException
	{
		context = getTransactionContext(context);
		try{
			List l;
			context.begin();
			Query query = getSession(context).createQuery(queryString);
			if(params != null)
				for(int i = 0; i < params.length; i++)
					query.setParameter(HQLBuilder.getParameterName(i), params[i]);
			l = query.list();
			context.commit();
			return l;
		}catch(ObjectNotFoundException oe){
			try{
				context.commit();
			}catch(Exception e){
				context.rollback();
			}
			return new ArrayList();
		}catch(SQLException re){
			context.rollback();
			throw re;
		}catch(Exception e){
			context.rollback();
			throw new SQLException(e);
		}finally{
			context.release();
		}
	}

	/**
	 * 根据 HQL 查询字符串和参数从数据库中获得对象列表
	 */
	public static List<Map<String, Object>> queryAllAsMap(HibernateTransactionContext context, String queryString, Object... params)
		throws SQLException
	{
		context = getTransactionContext(context);
		try{
			List l;
			context.begin();
			Query query = getMapSession(context).createQuery(queryString);
			if(params != null)
				for(int i = 0; i < params.length; i++)
					query.setParameter(HQLBuilder.getParameterName(i), params[i]);
			l = query.list();
			context.commit();
			return l;
		}catch(ObjectNotFoundException oe){
			try{
				context.commit();
			}catch(Exception e){
				context.rollback();
			}
			return new ArrayList();
		}catch(SQLException re){
			context.rollback();
			throw re;
		}catch(Exception e){
			context.rollback();
			throw new SQLException(e);
		}finally{
			context.release();
		}
	}

	/**
	 * 根据 HQL 查询字符串和参数从数据库中获得对象列表，限定起始结果和行数。
	 */
	public static List queryPage(HibernateTransactionContext context, String queryString, int startNum, int maxResults, Object... params)
		throws SQLException
	{
		context = getTransactionContext(context);
		try{
			List l;
			context.begin();
			Query query = getSession(context).createQuery(queryString);
			if(params != null)
				for(int i = 0; i < params.length; i++)
					query.setParameter(HQLBuilder.getParameterName(i), params[i]);
			query.setFirstResult(startNum);
			query.setMaxResults(maxResults);
			l = query.list();
			context.commit();
			return l;
		}catch(ObjectNotFoundException oe){
			try{
				context.commit();
			}catch(Exception e){
				context.rollback();
			}
			return new ArrayList();
		}catch(SQLException re){
			context.rollback();
			throw re;
		}catch(Exception e){
			context.rollback();
			throw new SQLException(e);
		}finally{
			context.release();
		}
	}

	/**
	 * 根据 HQL 查询字符串和参数从数据库中获得对象列表，限定起始结果和行数。
	 */
	public static List<Map<String, Object>> queryPageAsMap(HibernateTransactionContext context, String queryString, int startNum, int maxResults,
		Object... params)
		throws SQLException
	{
		context = getTransactionContext(context);
		try{
			List l;
			context.begin();
			Query query = getMapSession(context).createQuery(queryString);
			if(params != null)
				for(int i = 0; i < params.length; i++)
					query.setParameter(HQLBuilder.getParameterName(i), params[i]);
			query.setFirstResult(startNum);
			query.setMaxResults(maxResults);
			l = query.list();
			context.commit();
			return l;
		}catch(ObjectNotFoundException oe){
			try{
				context.commit();
			}catch(Exception e){
				context.rollback();
			}
			return new ArrayList();
		}catch(SQLException re){
			context.rollback();
			throw re;
		}catch(Exception e){
			context.rollback();
			throw new SQLException(e);
		}finally{
			context.release();
		}
	}

	/**
	 * 根据 HQL 查询字符串和参数从数据库中获得对象列表
	 */
	public static List queryAll(HibernateTransactionContext context, String queryString, Iterable params)
		throws SQLException
	{
		context = getTransactionContext(context);
		try{
			List l;
			context.begin();
			Query query = getSession(context).createQuery(queryString);
			int i = 0;
			for(Object param : params)
				query.setParameter(HQLBuilder.getParameterName(i++), param);
			l = query.list();
			context.commit();
			return l;
		}catch(ObjectNotFoundException oe){
			try{
				context.commit();
			}catch(Exception e){
				context.rollback();
			}
			return new ArrayList();
		}catch(SQLException re){
			context.rollback();
			throw re;
		}catch(Exception e){
			context.rollback();
			throw new SQLException(e);
		}finally{
			context.release();
		}
	}

	/**
	 * 根据 HQL 查询字符串和参数从数据库中获得对象列表
	 */
	public static List<Map<String, Object>> queryAllAsMap(HibernateTransactionContext context, String queryString, Iterable params)
		throws SQLException
	{
		context = getTransactionContext(context);
		try{
			List l;
			context.begin();
			Query query = getMapSession(context).createQuery(queryString);
			int i = 0;
			for(Object param : params)
				query.setParameter(HQLBuilder.getParameterName(i++), param);
			l = query.list();
			context.commit();
			return l;
		}catch(ObjectNotFoundException oe){
			try{
				context.commit();
			}catch(Exception e){
				context.rollback();
			}
			return new ArrayList();
		}catch(SQLException re){
			context.rollback();
			throw re;
		}catch(Exception e){
			context.rollback();
			throw new SQLException(e);
		}finally{
			context.release();
		}
	}

	/**
	 * 根据 HQL 查询字符串和参数从数据库中获得对象列表，限定起始结果和行数。
	 */
	public static List queryPage(HibernateTransactionContext context, String queryString, Iterable params, int startNum, int maxResults)
		throws SQLException
	{
		context = getTransactionContext(context);
		try{
			List l;
			context.begin();
			Query query = getSession(context).createQuery(queryString);
			int i = 0;
			for(Object param : params)
				query.setParameter(HQLBuilder.getParameterName(i++), param);
			query.setFirstResult(startNum);
			query.setMaxResults(maxResults);
			l = query.list();
			context.commit();
			return l;
		}catch(ObjectNotFoundException oe){
			try{
				context.commit();
			}catch(Exception e){
				context.rollback();
			}
			return new ArrayList();
		}catch(SQLException re){
			context.rollback();
			throw re;
		}catch(Exception e){
			context.rollback();
			throw new SQLException(e);
		}finally{
			context.release();
		}
	}

	/**
	 * 根据 HQL 查询字符串和参数从数据库中获得对象列表，限定起始结果和行数。
	 */
	public static List<Map<String, Object>> queryPageAsMap(HibernateTransactionContext context, String queryString, Iterable params, int startNum,
		int maxResults)
		throws SQLException
	{
		context = getTransactionContext(context);
		try{
			List l;
			context.begin();
			Query query = getMapSession(context).createQuery(queryString);
			int i = 0;
			for(Object param : params)
				query.setParameter(HQLBuilder.getParameterName(i++), param);
			query.setFirstResult(startNum);
			query.setMaxResults(maxResults);
			l = query.list();
			context.commit();
			return l;
		}catch(ObjectNotFoundException oe){
			try{
				context.commit();
			}catch(Exception e){
				context.rollback();
			}
			return new ArrayList();
		}catch(SQLException re){
			context.rollback();
			throw re;
		}catch(Exception e){
			context.rollback();
			throw new SQLException(e);
		}finally{
			context.release();
		}
	}

	/**
	 * 根据 HQL 查询字符串和参数从数据库中获得对象列表
	 */
	public static List queryAll(HibernateTransactionContext context, String queryString, Map<String, ?> params)
		throws SQLException
	{
		context = getTransactionContext(context);
		try{
			List l;
			context.begin();
			Query query = getSession(context).createQuery(queryString);
			for(Map.Entry<String, ? extends Object> paramEntry : params.entrySet())
				query.setParameter(paramEntry.getKey(), paramEntry.getValue());
			l = query.list();
			context.commit();
			return l;
		}catch(ObjectNotFoundException oe){
			try{
				context.commit();
			}catch(Exception e){
				context.rollback();
			}
			return new ArrayList();
		}catch(SQLException re){
			context.rollback();
			throw re;
		}catch(Exception e){
			context.rollback();
			throw new SQLException(e);
		}finally{
			context.release();
		}
	}

	/**
	 * 根据 HQL 查询字符串和参数从数据库中获得对象列表
	 */
	public static List<Map<String, Object>> queryAllAsMap(HibernateTransactionContext context, String queryString, Map<String, ?> params)
		throws SQLException
	{
		context = getTransactionContext(context);
		try{
			List l;
			context.begin();
			Query query = getMapSession(context).createQuery(queryString);
			for(Map.Entry<String, ? extends Object> paramEntry : params.entrySet())
				query.setParameter(paramEntry.getKey(), paramEntry.getValue());
			l = query.list();
			context.commit();
			return l;
		}catch(ObjectNotFoundException oe){
			try{
				context.commit();
			}catch(Exception e){
				context.rollback();
			}
			return new ArrayList();
		}catch(SQLException re){
			context.rollback();
			throw re;
		}catch(Exception e){
			context.rollback();
			throw new SQLException(e);
		}finally{
			context.release();
		}
	}

	/**
	 * 根据 HQL 查询字符串和参数从数据库中获得对象列表，限定起始结果和行数。
	 */
	public static List queryPage(HibernateTransactionContext context, String queryString, Map<String, ?> params, int startNum, int maxResults)
		throws SQLException
	{
		context = getTransactionContext(context);
		try{
			List l;
			context.begin();
			Query query = getSession(context).createQuery(queryString);
			for(Map.Entry<String, ? extends Object> paramEntry : params.entrySet())
				query.setParameter(paramEntry.getKey(), paramEntry.getValue());
			query.setFirstResult(startNum);
			query.setMaxResults(maxResults);
			l = query.list();
			context.commit();
			return l;
		}catch(ObjectNotFoundException oe){
			try{
				context.commit();
			}catch(Exception e){
				context.rollback();
			}
			return new ArrayList();
		}catch(SQLException re){
			context.rollback();
			throw re;
		}catch(Exception e){
			context.rollback();
			throw new SQLException(e);
		}finally{
			context.release();
		}
	}

	/**
	 * 根据 HQL 查询字符串和参数从数据库中获得对象列表，限定起始结果和行数。
	 */
	public static List<Map<String, Object>> queryPageAsMap(HibernateTransactionContext context, String queryString, Map<String, ?> params, int startNum,
		int maxResults)
		throws SQLException
	{
		context = getTransactionContext(context);
		try{
			List l;
			context.begin();
			Query query = getMapSession(context).createQuery(queryString);
			for(Map.Entry<String, ? extends Object> paramEntry : params.entrySet())
				query.setParameter(paramEntry.getKey(), paramEntry.getValue());
			query.setFirstResult(startNum);
			query.setMaxResults(maxResults);
			l = query.list();
			context.commit();
			return l;
		}catch(ObjectNotFoundException oe){
			try{
				context.commit();
			}catch(Exception e){
				context.rollback();
			}
			return new ArrayList();
		}catch(SQLException re){
			context.rollback();
			throw re;
		}catch(Exception e){
			context.rollback();
			throw new SQLException(e);
		}finally{
			context.release();
		}
	}

	/**
	 * 根据 HQL 查询字符串和参数从数据库中获得整形返回值
	 */
	public static long queryCount(HibernateTransactionContext context, String queryString, Object... params)
		throws SQLException
	{
		context = getTransactionContext(context);
		try{
			Query query = getSession(context).createQuery(queryString);
			if(params != null)
				for(int i = 0; i < params.length; i++)
					query.setParameter(HQLBuilder.getParameterName(i), params[i]);
			Object r = query.uniqueResult();
			if(r == null)
				return 0;
			return ((Long)r).longValue();
		}catch(ObjectNotFoundException oe){
			return 0;
		}catch(HibernateException he){
			throw new SQLException(he);
		}catch(SQLException re){
			throw re;
		}finally{
			context.release();
		}
	}

	/**
	 * 根据 HQL 查询字符串和参数从数据库中获得整形返回值
	 */
	public static long queryCount(HibernateTransactionContext context, String queryString, Iterable params)
		throws SQLException
	{
		context = getTransactionContext(context);
		try{
			Query query = getSession(context).createQuery(queryString);
			int i = 0;
			for(Object param : params)
				query.setParameter(HQLBuilder.getParameterName(i++), param);
			Object r = query.uniqueResult();
			if(r == null)
				return 0;
			return ((Long)r).longValue();
		}catch(HibernateException he){
			throw new SQLException(he);
		}catch(SQLException re){
			throw re;
		}finally{
			context.release();
		}
	}

	/**
	 * 根据 HQL 查询字符串和参数从数据库中获得整形返回值
	 */
	public static long queryCount(HibernateTransactionContext context, String queryString, Map<String, ?> params)
		throws SQLException
	{
		context = getTransactionContext(context);
		try{
			Query query = getSession(context).createQuery(queryString);
			for(Map.Entry<String, ? extends Object> paramEntry : params.entrySet())
				query.setParameter(paramEntry.getKey(), paramEntry.getValue());
			Object r = query.uniqueResult();
			if(r == null)
				return 0;
			return ((Long)r).longValue();
		}catch(HibernateException he){
			throw new SQLException(he);
		}catch(SQLException re){
			throw re;
		}finally{
			context.release();
		}
	}

	public static void updateSQL(HibernateTransactionContext context, String sql, Object... params)
		throws SQLException
	{
		context = getTransactionContext(context);
		PreparedStatement stat = null;
		try{
			context.begin();
			stat = getConnection(context).prepareStatement(sql);
			if(params != null)
				for(int i = 0; i < params.length; i++)
					stat.setObject(i + 1, params[i]);
			stat.executeUpdate();
			context.commit();
		}catch(SQLException re){
			context.rollback();
			throw re;
		}catch(Exception e){
			context.rollback();
			throw new SQLException(e);
		}finally{
			if(stat != null){
				try{
					stat.close();
				}catch(SQLException e){
				}
			}
			context.release();
		}
	}

	public static List<Map<String, String>> querySQLAsMap(HibernateTransactionContext context, String sql)
		throws SQLException
	{
		context = getTransactionContext(context);
		List<Map<String, String>> table = new ArrayList<Map<String, String>>();
		try{
			context.begin();
			Statement st = context.getConnection().createStatement();
			ResultSet rs = st.executeQuery(sql);

			// field name
			ResultSetMetaData meta = rs.getMetaData();
			int count = meta.getColumnCount();
			String[] str = new String[count];
			for(int i = 0; i < count; i++){
				str[i] = meta.getColumnName(i + 1).toLowerCase();
			}

			while(rs.next()){
				Map<String, String> row = new HashMap<String, String>();
				for(int i = 0; i < count; i++){
					row.put(str[i], rs.getString(i + 1));
				}
				table.add(row);
			}
			context.commit();
			return table;
		}catch(SQLException re){
			context.rollback();
			throw re;
		}catch(Exception e){
			context.rollback();
			throw new SQLException(e);
		}finally{
			context.release();
		}
	}

	public static List<Map<String, String>> querySQLPageAsMap(HibernateTransactionContext context, String sql, int startNum, int numPerPage)
		throws SQLException
	{
		context = getTransactionContext(context);
		List<Map<String, String>> table = new ArrayList<Map<String, String>>();
		try{
			context.begin();
			Statement st = context.getConnection().createStatement();
			ResultSet rs = st.executeQuery(sql);

			ResultSetMetaData meta = rs.getMetaData();
			int count = meta.getColumnCount();
			int i = 0;
			String[] str = new String[count];
			for(; i < count; i++){
				str[i] = meta.getColumnName(i + 1).toLowerCase();
			}

			String tem = null;
			int index = 0;
			while(index < startNum && rs.next()){
				index++;
			}
			int j = 0;
			while(rs.next() && j < numPerPage){
				HashMap<String, String> row = new HashMap<String, String>();
				for(i = 0; i < count; i++){
					tem = Objects.isNull(rs.getString(i + 1), "");
					row.put(str[i], tem);

				}
				table.add(row);
				j += 1;
			}
			context.commit();
			return table;
		}catch(SQLException re){
			context.rollback();
			throw re;
		}catch(Exception e){
			context.rollback();
			throw new SQLException(e);
		}finally{
			context.release();
		}
	}

	public static long querySQLCount(HibernateTransactionContext context, String sql, Object... params)
		throws SQLException
	{
		PreparedStatement stat;
		context = getTransactionContext(context);
		stat = null;
		try{
			stat = getConnection(context).prepareStatement(sql);
			if(params != null)
				for(int i = 0; i < params.length; i++)
					stat.setObject(i + 1, params[i]);
			ResultSet rlt = stat.executeQuery();
			if(!rlt.next())
				return 0;
			return rlt.getLong(1);
		}finally{
			if(stat != null){
				try{
					stat.close();
				}catch(SQLException e){
				}
			}
			context.release();
		}
	}

	public static long[] querySQLCount(HibernateTransactionContext context, String sql, int countNum, Object... params)
		throws SQLException
	{
		PreparedStatement stat = null;
		ResultSet rlt = null;
		context = getTransactionContext(context);
		long al[];
		try{
			stat = getConnection(context).prepareStatement(sql);
			if(params != null)
				for(int i = 0; i < params.length; i++)
					stat.setObject(i + 1, params[i]);
			rlt = stat.executeQuery();
			long result[] = new long[countNum];
			if(rlt.next())
				for(int i = 0; i < countNum; i++)
					result[i] = rlt.getLong(i + 1);
			else
				for(int i = 0; i < countNum; i++)
					result[i] = 0L;
			al = result;
		}finally{
			try{
				if(rlt != null)
					rlt.close();
				if(stat != null)
					stat.close();
			}catch(SQLException e){
			}
			context.release();
		}
		return al;
	}
}
