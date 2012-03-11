/*
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.sql.hibernate;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.junit.Assert;
import org.junit.Test;

import net.matrix.sql.hibernate.entity.UserInfo;

/**
 * Hibernate 测试
 * 
 * @version 2005-11-30
 */
public class HibernateJPATest {
	@Test
	public void testContextManager() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("test1");
		Assert.assertNotNull(emf);
		EntityManager em = emf.createEntityManager();
		Assert.assertNotNull(em);
		// 插入
		EntityTransaction et = em.getTransaction();
		et.begin();
		UserInfo user = new UserInfo();
		user.setYhm("abc");
		user.setMm("abc");
		em.persist(user);
		et.commit();
		// 查询
		Query query = em.createNamedQuery("UserInfo.findAll");
		Assert.assertNotNull(query);
		List<UserInfo> result = query.getResultList();
		Assert.assertNotNull(result);
		Assert.assertTrue(result.size() > 0);
	}
}
