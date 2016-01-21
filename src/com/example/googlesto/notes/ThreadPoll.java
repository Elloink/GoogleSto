package com.example.googlesto.notes;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * 线程池原理
 * @author threes
 *
 */
public class ThreadPoll {
	int maxCount = 3;
	AtomicInteger count =new AtomicInteger(0);// 当前开的线程数  count=0
	LinkedList<Runnable> runnables = new LinkedList<Runnable>();

	public void execute(Runnable runnable) {
		runnables.add(runnable);
		if(count.incrementAndGet()<=3){//此方法cout做++操作
			createThread();
		}
	}
	private void createThread() {
		new Thread() {
			@Override
			public void run() {
				super.run();
				while (true) {
					// 取出来一个异步任务
					if (runnables.size() > 0) {
						Runnable remove = runnables.remove(0);
						if (remove != null) {
							remove.run();
						}
					}else{
						//  等待状态   wake();
					}
				}
			}
		}.start();
	}
}




