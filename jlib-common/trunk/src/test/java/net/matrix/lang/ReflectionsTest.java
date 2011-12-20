package net.matrix.lang;

import org.junit.Assert;
import org.junit.Test;

public class ReflectionsTest
{
	@Test
	public void getAndSetFieldValue()
	{
		TestBean bean = new TestBean();
		// 无需getter函数, 直接读取privateField
		Assert.assertEquals(1, Reflections.getFieldValue(bean, "privateField"));
		// 绕过将publicField+1的getter函数,直接读取publicField的原始值
		Assert.assertEquals(1, Reflections.getFieldValue(bean, "publicField"));

		bean = new TestBean();
		// 无需setter函数, 直接设置privateField
		Reflections.setFieldValue(bean, "privateField", 2);
		Assert.assertEquals(2, bean.inspectPrivateField());

		// 绕过将publicField+1的setter函数,直接设置publicField的原始值
		Reflections.setFieldValue(bean, "publicField", 2);
		Assert.assertEquals(2, bean.inspectPublicField());
	}

	@Test
	public void invokeGetterAndSetter()
	{
		TestBean bean = new TestBean();
		Assert.assertEquals(bean.inspectPublicField() + 1, Reflections.invokeGetter(bean, "publicField"));

		bean = new TestBean();
		Reflections.invokeSetter(bean, "publicField", 10, int.class);
		Assert.assertEquals(10 + 1, bean.inspectPublicField());
	}

	@Test
	public void invokeMethod()
	{
		TestBean bean = new TestBean();
		Assert.assertEquals("hello calvin", Reflections.invokeMethod(bean, "privateMethod", new Class[]{
			String.class
		}, new Object[]{
			"calvin"
		}));
	}

	@Test
	public void getSuperClassGenricType()
	{
		// 获取第1，2个泛型类型
		Assert.assertEquals(String.class, Reflections.getSuperClassGenricType(TestBean.class));
		Assert.assertEquals(Long.class, Reflections.getSuperClassGenricType(TestBean.class, 1));

		// 定义父类时无泛型定义
		Assert.assertEquals(Object.class, Reflections.getSuperClassGenricType(TestBean2.class));

		// 无父类定义
		Assert.assertEquals(Object.class, Reflections.getSuperClassGenricType(TestBean3.class));
	}

	public static class ParentBean<T, ID>
	{
	}

	public static class TestBean
		extends ParentBean<String, Long>
	{
		/** 没有getter/setter的field */
		private int privateField = 1;

		/** 有getter/setter的field */
		private int publicField = 1;

		public int getPublicField()
		{
			return publicField + 1;
		}

		public void setPublicField(int publicField)
		{
			this.publicField = publicField + 1;
		}

		public int inspectPrivateField()
		{
			return privateField;
		}

		public int inspectPublicField()
		{
			return publicField;
		}

		// TODO make private method accessible
		public String privateMethod(String text)
		{
			return "hello " + text;
		}
	}

	public static class TestBean2
		extends ParentBean
	{
	}

	public static class TestBean3
	{
		private int id;

		public int getId()
		{
			return id;
		}

		public void setId(int id)
		{
			this.id = id;
		}
	}
}
