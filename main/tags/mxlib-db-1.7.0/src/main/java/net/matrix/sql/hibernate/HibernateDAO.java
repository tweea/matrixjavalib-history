/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.sql.hibernate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.NonUniqueResultException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import net.matrix.lang.Reflections;

/**
 * 封装 Hibernate 原生 API 的 DAO 泛型基类。
 * 
 * @param <T>
 *            实体类型
 * @param <ID>
 *            主键类型
 */
public class HibernateDAO<T, ID extends Serializable> {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 实体类。
	 */
	private final Class<T> entityClass;

	/**
	 * Hibernate 操作对象。
	 */
	private SessionFactory sessionFactory;

	/**
	 * 通过子类的泛型定义取得实体类型。
	 */
	public HibernateDAO() {
		this.entityClass = Reflections.getClassGenricType(getClass());
	}

	/**
	 * 直接指定实体类型。
	 * 
	 * @param entityClass
	 *            实体类型
	 */
	public HibernateDAO(final Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	/**
	 * 通过子类的泛型定义取得实体类型。
	 */
	public HibernateDAO(final SessionFactory sessionFactory) {
		this.entityClass = Reflections.getClassGenricType(getClass());
		this.sessionFactory = sessionFactory;
	}

	/**
	 * 直接指定实体类型。
	 * 
	 * @param entityClass
	 *            实体类型
	 */
	public HibernateDAO(final SessionFactory sessionFactory, final Class<T> entityClass) {
		this.entityClass = entityClass;
		this.sessionFactory = sessionFactory;
	}

	public Class<T> getEntityClass() {
		return entityClass;
	}

	/**
	 * 取得 sessionFactory。
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(final SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * 取得当前 Session。
	 */
	public Session currentSession() {
		return sessionFactory.getCurrentSession();
	}

	public boolean isAutoFlush() {
		return false;
	}

	/**
	 * 保存新增或修改的对象。
	 */
	public <S extends T> S save(final S entity) {
		currentSession().save(entity);
		logger.debug("save entity: {}", entity);
		autoFlush();
		return entity;
	}

	public <S extends T> Iterable<S> save(final Iterable<S> entities) {
		List<S> result = new ArrayList<S>();

		for (S entity : entities) {
			currentSession().save(entity);
			logger.debug("save entity: {}", entity);
			result.add(entity);
		}
		autoFlush();

		return result;
	}

	/**
	 * 按 id 获取对象。
	 */
	public T findOne(final ID id) {
		return (T) currentSession().get(entityClass, id);
	}

	public boolean exists(final ID id) {
		return findOne(id) != null;
	}

	/**
	 * 获取全部对象。
	 */
	public Iterable<T> findAll() {
		return createCriteria().list();
	}

	/**
	 * 按 id 列表获取对象列表。
	 */
	public Iterable<T> findAll(final Iterable<ID> ids) {
		List<T> result = new ArrayList<T>();
		for (ID id : ids) {
			result.add(findOne(id));
		}
		return result;
	}

	public long count() {
		Criteria criteria = createCriteria();
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	/**
	 * 按 id 删除对象。
	 */
	public void delete(final ID id) {
		T entity = findOne(id);
		if (entity == null) {
			throw new ObjectNotFoundException(id, entityClass.getSimpleName());
		}
		delete(entity);
		logger.debug("delete entity {},id is {}", entityClass.getSimpleName(), id);
	}

	/**
	 * 删除对象。
	 * 
	 * @param entity
	 *            对象必须是 session 中的对象或含 id 属性的 transient 对象
	 */
	public void delete(final T entity) {
		Session session = currentSession();
		if (session.contains(entity)) {
			session.delete(entity);
		} else {
			session.delete(session.merge(entity));
		}
		logger.debug("delete entity: {}", entity);
	}

	public void delete(final Iterable<? extends T> entities) {
		for (T entity : entities) {
			delete(entity);
		}
	}

	public void deleteAll() {
		for (T entity : findAll()) {
			delete(entity);
		}
	}

	/**
	 * 获取全部对象，支持按属性行序。
	 */
	public Iterable<T> findAll(final Sort sort) {
		Criteria criteria = createCriteria();
		applySort(criteria, sort);
		return criteria.list();
	}

	public Page<T> findAll(final Pageable pageable) {
		long totalCount = count();

		Criteria criteria = createCriteria();
		applyPageable(criteria, pageable);
		List<T> result = criteria.list();

		return new PageImpl(result, pageable, totalCount);
	}

	public void update(final T entity) {
		currentSession().saveOrUpdate(entity);
		autoFlush();
	}

	/**
	 * 按属性查找对象列表，匹配方式为相等。
	 */
	public List<T> findBy(final String propertyName, final Object value) {
		return find(Restrictions.eq(propertyName, value));
	}

	/**
	 * 按属性查找唯一对象，匹配方式为相等。
	 */
	public T findOneBy(final String propertyName, final Object value) {
		return findOne(Restrictions.eq(propertyName, value));
	}

	/**
	 * 按 Criteria 查询对象列表。
	 * 
	 * @param criterions
	 *            数量可变的 Criterion
	 */
	public List<T> find(final Criterion... criterions) {
		return createCriteria(criterions).list();
	}

	/**
	 * 按 Criteria 查询唯一对象。
	 * 
	 * @param criterions
	 *            数量可变的 Criterion
	 */
	public T findOne(final Criterion... criterions) {
		return (T) createCriteria(criterions).uniqueResult();
	}

	/**
	 * 判断对象的属性值在数据库内是否唯一。
	 * 在修改对象的情景下，如果属性新修改的值（newValue）等于属性原来的值（oldValue）则不作比较。
	 */
	public boolean isPropertyUnique(final String propertyName, final T newValue, final T oldValue) {
		if (newValue == null || newValue.equals(oldValue)) {
			return true;
		}
		T object = findOneBy(propertyName, newValue);
		return object == null;
	}

	/**
	 * 根据 Criterion 条件创建 Criteria。
	 * 与 find() 方法可进行更加灵活的操作。
	 * 
	 * @param criterions
	 *            数量可变的 Criterion
	 */
	public Criteria createCriteria(final Criterion... criterions) {
		Criteria criteria = currentSession().createCriteria(entityClass);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}

	/**
	 * 按HQL查询对象列表.
	 * 
	 * @param param
	 *            数量可变的参数,按顺序绑定.
	 */
	public <X> List<X> find(final String hql, final Object... param) {
		return createQuery(hql, param).list();
	}

	/**
	 * 按HQL查询对象列表.
	 * 
	 * @param param
	 *            命名参数,按名称绑定.
	 */
	public <X> List<X> find(final String hql, final Map<String, ?> param) {
		return createQuery(hql, param).list();
	}

	public <X> Page<X> find(final String hql, final Map<String, ?> param, final Pageable pageable) {
		long totalCount = count(hql, param);
		List<X> result = createQuery(hql, param, pageable).list();
		return new PageImpl(result, pageable, totalCount);
	}

	public long count(final String hql, final Map<String, ?> param) {
		String countHql = buildCountQueryString(hql);

		Number result = (Number) createQuery(countHql, param).uniqueResult();
		if (result == null) {
			throw new NonUniqueResultException(0);
		}
		return result.longValue();
	}

	/**
	 * 按HQL查询唯一对象.
	 * 
	 * @param param
	 *            数量可变的参数,按顺序绑定.
	 */
	public <X> X findUnique(final String hql, final Object... param) {
		return (X) createQuery(hql, param).uniqueResult();
	}

	/**
	 * 按HQL查询唯一对象.
	 * 
	 * @param param
	 *            命名参数,按名称绑定.
	 */
	public <X> X findUnique(final String hql, final Map<String, ?> param) {
		return (X) createQuery(hql, param).uniqueResult();
	}

	/**
	 * 执行HQL进行批量修改/删除操作.
	 * 
	 * @param param
	 *            数量可变的参数,按顺序绑定.
	 * @return 更新记录数.
	 */
	public int batchExecute(final String hql, final Object... param) {
		return createQuery(hql, param).executeUpdate();
	}

	/**
	 * 执行HQL进行批量修改/删除操作.
	 * 
	 * @param param
	 *            命名参数,按名称绑定.
	 * @return 更新记录数.
	 */
	public int batchExecute(final String hql, final Map<String, ?> param) {
		return createQuery(hql, param).executeUpdate();
	}

	/**
	 * 根据查询HQL与参数列表创建Query对象.
	 * 与find()方法可进行更加灵活的操作.
	 * 
	 * @param param
	 *            数量可变的参数,按顺序绑定.
	 */
	public Query createQuery(final String hql, final Object... param) {
		Query query = currentSession().createQuery(hql);
		if (param != null) {
			for (int i = 0; i < param.length; i++) {
				query.setParameter(i, param[i]);
			}
		}
		return query;
	}

	/**
	 * 根据查询HQL与参数列表创建Query对象.
	 * 与find()方法可进行更加灵活的操作.
	 * 
	 * @param param
	 *            命名参数,按名称绑定.
	 */
	public Query createQuery(final String hql, final Map<String, ?> param) {
		Query query = currentSession().createQuery(hql);
		if (param != null) {
			query.setProperties(param);
		}
		return query;
	}

	/**
	 * 根据查询 HQL 与参数列表创建 Query 对象。与 find() 方法可进行更加灵活的操作。
	 * 
	 * @param hql
	 *            HQL
	 * @param param
	 *            参数
	 * @param pageable
	 *            分页参数
	 * @return Query 对象
	 */
	public Query createQuery(final String hql, final Map<String, ?> param, final Pageable pageable) {
		StringBuilder queryHql = new StringBuilder(hql);
		if (pageable != null && pageable.getSort() != null) {
			queryHql.append(" order by ");
			for (Sort.Order order : pageable.getSort()) {
				queryHql.append(order.getProperty());
				queryHql.append(' ');
				if (order.isAscending()) {
					queryHql.append("asc");
				} else {
					queryHql.append("desc");
				}
				queryHql.append(',');
			}
			queryHql.deleteCharAt(queryHql.length() - 1);
		}

		Query query = currentSession().createQuery(queryHql.toString());

		if (param != null) {
			query.setProperties(param);
		}

		if (pageable != null) {
			query.setFirstResult(pageable.getOffset());
			query.setMaxResults(pageable.getPageSize());
		}

		return query;
	}

	/**
	 * 为 Criteria 添加 distinct transformer。
	 * 预加载关联对象的 HQL 会引起主对象重复，需要进行distinct处理。
	 */
	public void distinct(final Criteria criteria) {
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
	}

	/**
	 * 为 Query 添加 distinct transformer。
	 * 预加载关联对象的 HQL 会引起主对象重复，需要进行distinct处理。
	 */
	public void distinct(final Query query) {
		query.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
	}

	/**
	 * 根据设置自动执行 flush()。
	 */
	private void autoFlush() {
		if (isAutoFlush()) {
			currentSession().flush();
		}
	}

	/**
	 * 设置分页参数到 Criteria 对象。
	 * 
	 * @param criteria
	 *            Criteria 对象
	 * @param pageable
	 *            分页参数
	 */
	private void applyPageable(final Criteria criteria, final Pageable pageable) {
		criteria.setFirstResult(pageable.getOffset());
		criteria.setMaxResults(pageable.getPageSize());
		if (pageable.getSort() != null) {
			applySort(criteria, pageable.getSort());
		}
	}

	/**
	 * 在 Criteria 对象上设置排序。
	 * 
	 * @param criteria
	 *            Criteria
	 * @param sort
	 *            排序
	 */
	private void applySort(final Criteria criteria, final Sort sort) {
		for (Sort.Order order : sort) {
			Order hibernateOrder;
			if (order.isAscending()) {
				hibernateOrder = Order.asc(order.getProperty());
			} else {
				hibernateOrder = Order.desc(order.getProperty());
			}
			if (order.isIgnoreCase()) {
				hibernateOrder = hibernateOrder.ignoreCase();
			}
			criteria.addOrder(hibernateOrder);
		}
	}

	/**
	 * 根据一个查询 HQL 语句构造一个查询总记录数的 HQL 语句。
	 * 
	 * @param selectHql
	 *            查询 HQL 语句
	 * @return 查询总记录数 HQL 语句
	 */
	private String buildCountQueryString(final String selectHql) {
		// select 子句与 order by 子句会影响 count 查询,进行简单的排除.
		String body = StringUtils.substringAfter(selectHql, "from");
		body = StringUtils.substringBeforeLast(body, "order by");

		return "select count(*) from " + body;
	}
}
