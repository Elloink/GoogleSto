package com.example.googlesto.notes;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * �̳߳�ԭ��
 * @author threes
 *
 */
public class ThreadPoll {
	int maxCount = 3;
	AtomicInteger count =new AtomicInteger(0);// ��ǰ�����߳���  count=0
	LinkedList<Runnable> runnables = new LinkedList<Runnable>();

	public void execute(Runnable runnable) {
		runnables.add(runnable);
		if(count.incrementAndGet()<=3){//�˷���cout��++����
			createThread();
		}
	}
	private void createThread() {
		new Thread() {
			@Override
			public void run() {
				super.run();
				while (true) {
					// ȡ����һ���첽����
					if (runnables.size() > 0) {
						Runnable remove = runnables.remove(0);
						if (remove != null) {
							remove.run();
						}
					}else{
						//  �ȴ�״̬   wake();
					}
				}
			}
		}.start();
	}
}




