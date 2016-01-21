package com.example.googlesto.manager;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
/**
 * 线程池管理者
 * 
 * @author threes
 *
 */
public class ThreadManager {
	public ThreadManager() {
	}
	private static ThreadManager instance = new ThreadManager();
	private ThreadPoolProxy longPool;//长线程池
	private ThreadPoolProxy shortPool;//短线程池

	public static ThreadManager getInstance() {
		return instance;
	}
	    // 联网比较耗时
		// 合适的线程数=cpu的核数*2+1
		public synchronized ThreadPoolProxy createLongPool() {
			if (longPool == null) {
				longPool = new ThreadPoolProxy(5, 5, 5000L);
			}
			return longPool;
		}
		// 操作本地文件
		public synchronized ThreadPoolProxy createShortPool() {
			if(shortPool==null){
				shortPool = new ThreadPoolProxy(3, 3, 5000L);
			}
			return shortPool;
		}
	

	// 内部类线程池
	public class ThreadPoolProxy {

		ThreadPoolExecutor pool;
		private int corePoolSize;// 线程数量
		private int maximumPoolSize;//额外开的线程数
		private long time;//没有任务执行，线程存活时间

		public ThreadPoolProxy(int corePoolSize, int maximumPoolSize, long time) {
			this.corePoolSize = corePoolSize;
			this.maximumPoolSize = maximumPoolSize;
			this.time = time;

		}
		/**
		 * 执行任务
		 * 
		 * @param runnable
		 */
		public void excute(Runnable runnable) {
			if (pool == null) {
				// 创建线程池
				/*
				 * 1. 线程池里面管理多少个线程 2. 如果排队满了, 额外的开的线程数 3. 如果线程池没有要执行的任务 存活多久
				 * 4.时间的单位 5 如果 线程池里管理的线程都已经用了,剩下的任务 临时存到LinkedBlockingQueue对象中
				 * 排队
				 */
				pool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
						time, TimeUnit.MICROSECONDS,
						new LinkedBlockingDeque<Runnable>(10));
			}
			pool.execute(runnable);// 调用线程池去执行异步任务
		}

		/**
		 * 取消任务
		 * @param runnable
		 */
		public void cancle(Runnable runnable) {
			if (null != pool && !pool.isShutdown() & !pool.isTerminated()) {
				pool.remove(runnable);// 取消异步任务
			}

		}
	}
}
