/*
 * $Id$
 * Copyright(C) 2011 Matrix
 * All right reserved.
 */
package net.matrix.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 线程相关工具类。
 */
public final class Threads {
	/**
	 * 日志记录器。
	 */
	private static final Logger LOG = LoggerFactory.getLogger(Threads.class);

	/**
	 * 阻止实例化。
	 */
	private Threads() {
	}

	/**
	 * sleep 等待，忽略 InterruptedException。
	 * 
	 * @param millis
	 *            等待毫秒数
	 */
	public static void sleep(final long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			LOG.warn("thread interrupted.");
		}
	}

	/**
	 * sleep 等待，忽略 InterruptedException。
	 * 
	 * @param duration
	 *            等待时间
	 * @param unit
	 *            时间单位
	 */
	public static void sleep(final long duration, final TimeUnit unit) {
		try {
			Thread.sleep(unit.toMillis(duration));
		} catch (InterruptedException e) {
			LOG.warn("thread interrupted.");
		}
	}

	/**
	 * 按照 ExecutorService JavaDoc 示例代码编写的 Graceful Shutdown 方法。
	 * 先使用 shutdown，停止接收新任务并尝试完成所有已存在任务。
	 * 如果超时，则调用 shutdownNow，取消在 workQueue 中 Pending 的任务，并中断所有阻塞函数。
	 * 如果仍然超時，則強制退出。
	 * 另对在 shutdown 时线程本身被调用中断做了处理。
	 * 
	 * @param pool
	 *            ExecutorService
	 * @param shutdownTimeout
	 *            等待关闭超时时间
	 * @param shutdownNowTimeout
	 *            立即关闭超时时间
	 * @param timeUnit
	 *            时间单位
	 */
	public static void gracefulShutdown(final ExecutorService pool, final int shutdownTimeout, final int shutdownNowTimeout, final TimeUnit timeUnit) {
		// Disable new tasks from being submitted
		pool.shutdown();
		try {
			// Wait a while for existing tasks to terminate
			if (!pool.awaitTermination(shutdownTimeout, timeUnit)) {
				// Cancel currently executing tasks
				pool.shutdownNow();
				// Wait a while for tasks to respond to being cancelled
				if (!pool.awaitTermination(shutdownNowTimeout, timeUnit)) {
					LOG.error("Pool did not terminated");
				}
			}
		} catch (InterruptedException ie) {
			// (Re-)Cancel if current thread also interrupted
			pool.shutdownNow();
			// Preserve interrupt status
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * 直接调用 shutdownNow 的方法，有 timeout 控制。取消在 workQueue 中 Pending 的任务，并中断所有阻塞函数。
	 * 
	 * @param pool
	 *            ExecutorService
	 * @param timeout
	 *            立即关闭超时时间
	 * @param timeUnit
	 *            时间单位
	 */
	public static void normalShutdown(final ExecutorService pool, final int timeout, final TimeUnit timeUnit) {
		try {
			pool.shutdownNow();
			if (!pool.awaitTermination(timeout, timeUnit)) {
				LOG.error("Pool did not terminated");
			}
		} catch (InterruptedException ie) {
			Thread.currentThread().interrupt();
		}
	}
}
