package net.matrix.transaction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

public class TransactionTest
{
	/**
	 * Logger for this class
	 */
	private static final Log log = LogFactory.getLog(TransactionTest.class);

	@AfterClass
	public static void tearDown()
		throws Exception
	{
	}

	public class TestTransaction
		extends AbstractTransaction
	{
		@Override
		protected void doBegin()
			throws Exception
		{
		}

		@Override
		protected void doCommit()
			throws Exception
		{
		}

		@Override
		protected void doRelease()
			throws Exception
		{
		}

		@Override
		protected void doRollback()
			throws Exception
		{
		}
	}

	@Test
	public void testTransactionManager()
	{
		TransactionManager mm = TransactionManager.getInstance();
		Assert.assertNotNull(mm.getTransaction());
		mm.setTransaction(new TestTransaction());
		Assert.assertNotNull(mm.getTransaction());
	}

	@Test
	public void testCreateDrop()
	{
		TransactionManager mm = TransactionManager.getInstance();
		Assert.assertNotNull(mm.createTransaction());
		Assert.assertNotNull(mm.getTransaction());
		mm.dropTransaction();
		mm.dropTransaction();
		Assert.assertNotNull(mm.getTransaction());
	}

	@Test
	public void testTransaction()
		throws Exception
	{
		log.info("transaction 1");
		Transaction tt = TransactionManager.getInstance().getTransaction();
		try{
			log.info(tt.getStatus());
			tt.begin();
			log.info(tt.getStatus());
			tt.commit();
			log.info(tt.getStatus());
		}catch(Exception e){
			tt.rollback();
			log.info(tt.getStatus());
			throw e;
		}finally{
			tt.release();
		}
	}

	@Test
	public void testTransaction2()
		throws Exception
	{
		log.info("transaction 2");
		Transaction tt = TransactionManager.getInstance().getTransaction();
		try{
			log.info(tt.getStatus());
			tt.begin();
			log.info(tt.getStatus());
			tt.commit();
			log.info(tt.getStatus());
		}catch(Exception e){
			tt.rollback();
			log.info(tt.getStatus());
			throw e;
		}finally{
			tt.release();
		}
		try{
			log.info(tt.getStatus());
			tt.begin();
			log.info(tt.getStatus());
			tt.commit();
			log.info(tt.getStatus());
		}catch(Exception e){
			tt.rollback();
			log.info(tt.getStatus());
			throw e;
		}finally{
			tt.release();
		}
	}

	@Test(expected = Exception.class)
	public void testTransaction3()
		throws Exception
	{
		log.info("transaction 3");
		Transaction tt = TransactionManager.getInstance().getTransaction();
		try{
			log.info(tt.getStatus());
			tt.begin();
			throw new Exception();
		}catch(Exception e){
			tt.rollback();
			log.info(tt.getStatus());
			throw e;
		}finally{
			tt.release();
		}
	}

	@Test
	public void testTransaction4()
		throws Exception
	{
		log.info("transaction 4");
		Transaction tt = TransactionManager.getInstance().getTransaction();
		try{
			log.info(tt.getStatus());
			tt.begin();
			testTransaction2();
			log.info(tt.getStatus());
			tt.commit();
			log.info(tt.getStatus());
		}catch(Exception e){
			tt.rollback();
			log.info(tt.getStatus());
			throw e;
		}finally{
			tt.release();
		}
	}

	class TestValue
	{
		private int i;

		public TestValue(int i)
		{
			this.i = i;
		}

		public int getI()
		{
			return i;
		}

		public void setI(int i)
		{
			this.i = i;
		}
	}

	@Test
	public void testUndo()
	{
		log.info("undo 1");
		TestValue test = new TestValue(22);
		Transaction tt = TransactionManager.getInstance().getTransaction();
		try{
			log.info(tt.getStatus());
			tt.begin();
			test.setI(33);
			final TestValue undoTest = test;
			tt.addUndoOperation(new UndoOperation()
			{
				@Override
				public void undo()
				{
					undoTest.setI(22);
				}
			});
			log.info(tt.getStatus());
			throw new TransactionException();
		}catch(Exception e){
			tt.rollback();
			log.info(tt.getStatus());
		}finally{
			tt.release();
		}
		Assert.assertEquals(test.getI(), 22);
	}
}
