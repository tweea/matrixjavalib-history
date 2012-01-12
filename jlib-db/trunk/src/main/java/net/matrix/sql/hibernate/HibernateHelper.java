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
	private static HibernateTransactionContext getTransactionContext(HibernateTransactionContext context)
	{
		if(context == null){
			return SessionFactoryManager.getInstance().getTransactionContext();
		}
		return context;
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

	/**
	 * 向数据库中存储一个对象
	 */
	private static <T> T merge0(HibernateTransactionContext context, T object)
		throws SQLException
	{
		try{
			return (T)getSession(context).merge(object);
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
		try{
			return merge0(getTransactionContext(context), object);
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
		context = getTransactionContext(context);
		try{
			context.begin();
			Serializable id = getSession(context).save(object);
			context.commit();
			return id;
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
	 * 向数据库中更新一个对象
	 */
	public static void update(HibernateTransactionContext context, Object object)
		throws SQLException
	{
		context = getTransactionContext(context);
		try{
			context.begin();
			getSession(context).update(object);
			context.commit();
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
	 * 向数据库中存储或更新一个对象
	 */
	public static void createOrUpdate(HibernateTransactionContext context, Object object)
		throws SQLException
	{
		context = getTransactionContext(context);
		try{
			context.begin();
			getSession(context).saveOrUpdate(object);
			context.commit();
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
	 * 从数据库中删除一个对象
	 */
	public static void delete(HibernateTransactionContext context, Object object)
		throws SQLException
	{
		context = getTransactionContext(context);
		try{
			context.begin();
			object = merge0(context, object);
			getSession(context).delete(object);
			context.commit();
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
	 * 从数据库中删除一个对象
	 */
	public static void delete(HibernateTransactionContext context, Class objectClass, Serializable primaryKey)
		throws SQLException
	{
		context = getTransactionContext(context);
		try{
			context.begin();
			Object obj = getSession(context).load(objectClass, primaryKey);
			getSession(context).delete(obj);
			context.commit();
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
	 * 根据类型和主键从数据库中获得一个对象，若没有则返回 null
	 */
	public static <T> T get(HibernateTransactionContext context, Class<T> objectClass, Serializable primaryKey)
		throws SQLException
	{
		context = getTransactionContext(context);
		T obj;
		try{
			context.begin();
			obj = (T)getSession(context).get(objectClass, primaryKey);
			context.commit();
		}catch(SQLException re){
			context.rollback();
			throw re;
		}catch(Exception e){
			context.rollback();
			throw new SQLException(e);
		}finally{
			context.release();
		}
		return obj;
	}

	/**
	 * 根据类型和主键从数据库中获得一个对象，若没有则返回 null
	 */
	public static Map<String, Object> getAsMap(HibernateTransactionContext context, Class objectClass, Serializable primaryKey)
		throws SQLException
	{
		context = getTransactionContext(context);
		Map obj;
		try{
			context.begin();
			obj = (Map)getMapSession(context).get(objectClass, primaryKey);
			context.commit();
		}catch(SQLException re){
			context.rollback();
			throw re;
		}catch(Exception e){
			context.rollback();
			throw new SQLException(e);
		}finally{
			context.release();
		}
		return obj;
	}

	/**
	 * 执行 HQL 语句
	 */
	public static int execute(HibernateTransactionContext context, String queryString, Object... params)
		throws SQLException
	{
		context = getTransactionContext(context);
		try{
			int effectedRows;
			context.begin();
			Query query = getSession(context).createQuery(queryString);
			if(params != null)
				for(int i = 0; i < params.length; i++)
					query.setParameter(HQLBuilder.getParameterName(i), params[i]);
			effectedRows = query.executeUpdate();
			context.commit();
			return effectedRows;
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
	 * 执行 HQL 语句
	 */
	public static int execute(HibernateTransactionContext context, String queryString, Iterable params)
		throws SQLException
	{
		context = getTransactionContext(context);
		try{
			int effectedRows;
			context.begin();
			Query query = getSession(context).createQuery(queryString);
			int i = 0;
			for(Object param : params)
				query.setParameter(HQLBuilder.getParameterName(i++), param);
			effectedRows = query.executeUpdate();
			context.commit();
			return effectedRows;
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
	public static int execute(HibernateTransactionContext context, String queryString, Map<String, ?> params)
		throws SQLException
	{
		context = getTransactionContext(context);
		try{
			int effectedRows;
			context.begin();
			Query query = getSession(context).createQuery(queryString);
			for(Map.Entry<String, ? extends Object> paramEntry : params.entrySet())
				query.setParameter(paramEntry.getKey(), paramEntry.getValue());
			effectedRows = query.executeUpdate();
			context.commit();
			return effectedRows;
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
