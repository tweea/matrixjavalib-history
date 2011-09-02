/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.transaction;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.matrix.util.Counter;

/**
 * 事务处理
 * @since 2006.07.04
 */
public abstract class AbstractTransaction
	implements Transaction
{
	private static final Log LOG = LogFactory.getLog(AbstractTransaction.class);

	private TransactionStatus status = TransactionStatus.INACTIVE;

	private Counter counter = new Counter();

	private List<UndoOperation> undos = new ArrayList<UndoOperation>();

	@Override
	public TransactionStatus getStatus()
	{
		return status;
	}

	@Override
	public boolean isActive()
	{
		return status == TransactionStatus.BEGIN;
	}

	/**
	 * 开始一个事务
	 */
	@Override
	public void begin()
		throws TransactionException
	{
		try{
			doBegin();
		}catch(TransactionException e){
			LOG.debug("开始事务失败: " + counter.getCount());
			throw e;
		}catch(Exception e){
			LOG.debug("开始事务失败: " + counter.getCount());
			throw new TransactionException(e);
		}
		counter.increment();
		status = TransactionStatus.BEGIN;
		LOG.debug("开始事务: " + counter.getCount());
	}

	/**
	 * 提交一个事务
	 */
	@Override
	public void commit()
		throws TransactionException
	{
		if(!isActive()){
			throw new IllegalStateException("事务没有开始");
		}
		LOG.debug("开始提交事务: " + counter.getCount());
		counter.decrement();
		if(counter.isZero()){
			try{
				doCommit();
			}catch(TransactionException e){
				LOG.debug("提交事务失败: " + counter.getCount());
				throw e;
			}catch(Exception e){
				LOG.debug("提交事务失败: " + counter.getCount());
				throw new TransactionException(e);
			}
			status = TransactionStatus.COMMIT;
			LOG.debug("事务已提交");
		}
	}

	/**
	 * 滚回事务
	 */
	@Override
	public void rollback()
	{
		if(!isActive()){
			return;
		}
		LOG.debug("开始回滚事务: " + counter.getCount());
		status = TransactionStatus.ROLLBACK;
		counter.clean();
		try{
			doRollback();
		}catch(Exception ex){
			LOG.warn("事务回滚失败", ex);
		}
		for(UndoOperation undo : undos){
			try{
				LOG.debug("撤销操作：" + undo);
				undo.undo();
			}catch(Exception e){
				LOG.warn("操作撤销失败", e);
			}
		}
		undos.clear();
		LOG.debug("事务回滚结束");
	}

	/**
	 * 完成事务，若未提交则滚回
	 */
	@Override
	public void release()
	{
		if(!counter.isZero()){
			return;
		}
		try{
			if(isActive()){
				rollback();
			}
			undos.clear();
			doRelease();
			LOG.debug("事务资源已释放");
		}catch(Exception e){
			LOG.warn("事务资源释放失败: " + counter.getCount(), e);
		}
	}

	/**
	 * 增加一个撤销操作
	 * @param undo 撤销操作
	 */
	@Override
	public void addUndoOperation(UndoOperation undo)
	{
		undos.add(0, undo);
	}

	protected abstract void doBegin()
		throws Exception;

	protected abstract void doCommit()
		throws Exception;

	protected abstract void doRollback()
		throws Exception;

	protected abstract void doRelease()
		throws Exception;
}
