package com.example.googlesto.manager;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
/**
 * �̳߳ع�����
 * 
 * @author threes
 *
 */
public class ThreadManager {
	public ThreadManager() {
	}
	private static ThreadManager instance = new ThreadManager();
	private ThreadPoolProxy longPool;//���̳߳�
	private ThreadPoolProxy shortPool;//���̳߳�

	public static ThreadManager getInstance() {
		return instance;
	}
	    // �����ȽϺ�ʱ
		// ���ʵ��߳���=cpu�ĺ���*2+1
		public synchronized ThreadPoolProxy createLongPool() {
			if (longPool == null) {
				longPool = new ThreadPoolProxy(5, 5, 5000L);
			}
			return longPool;
		}
		// ���������ļ�
		public synchronized ThreadPoolProxy createShortPool() {
			if(shortPool==null){
				shortPool = new ThreadPoolProxy(3, 3, 5000L);
			}
			return shortPool;
		}
	

	// �ڲ����̳߳�
	public class ThreadPoolProxy {

		ThreadPoolExecutor pool;
		private int corePoolSize;// �߳�����
		private int maximumPoolSize;//���⿪���߳���
		private long time;//û������ִ�У��̴߳��ʱ��

		public ThreadPoolProxy(int corePoolSize, int maximumPoolSize, long time) {
			this.corePoolSize = corePoolSize;
			this.maximumPoolSize = maximumPoolSize;
			this.time = time;

		}
		/**
		 * ִ������
		 * 
		 * @param runnable
		 */
		public void excute(Runnable runnable) {
			if (pool == null) {
				// �����̳߳�
				/*
				 * 1. �̳߳����������ٸ��߳� 2. ����Ŷ�����, ����Ŀ����߳��� 3. ����̳߳�û��Ҫִ�е����� �����
				 * 4.ʱ��ĵ�λ 5 ��� �̳߳��������̶߳��Ѿ�����,ʣ�µ����� ��ʱ�浽LinkedBlockingQueue������
				 * �Ŷ�
				 */
				pool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
						time, TimeUnit.MICROSECONDS,
						new LinkedBlockingDeque<Runnable>(10));
			}
			pool.execute(runnable);// �����̳߳�ȥִ���첽����
		}

		/**
		 * ȡ������
		 * @param runnable
		 */
		public void cancle(Runnable runnable) {
			if (null != pool && !pool.isShutdown() & !pool.isTerminated()) {
				pool.remove(runnable);// ȡ���첽����
			}

		}
	}
}
