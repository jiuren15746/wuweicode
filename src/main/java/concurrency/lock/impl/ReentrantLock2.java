package concurrency.lock.impl;

import concurrency.lock.ReentrantLockJiuren;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 可重入锁。仅使用CAS实现。第二次实现。
 */
public class ReentrantLock2 implements ReentrantLockJiuren {
    // 锁状态：0-free，>=1-busy
    private final AtomicInteger lockStatus = new AtomicInteger(0);
    // 持有锁的线程
    private volatile Thread holdingThread;
    //=============

    @Override
    public void lock() throws InterruptedException {
        if (isHeldByCurrentThread()) {
            lockStatus.incrementAndGet();
            return;
        }
        for (int tryCount = 0; ; tryCount++) {
            if (lockStatus.get() == 0
                    && holdingThread == null
                    && lockStatus.compareAndSet(0, 1)) {
                holdingThread = Thread.currentThread();
                return;
            }
            if (tryCount > 0 && tryCount % 10 == 0) {
                Thread.sleep(randomValueWithin(3));
            }
        }
    }

    @Override
    public void unlock() {
        if (!isHeldByCurrentThread()) throw new IllegalMonitorStateException();
        if (lockStatus.decrementAndGet() == 0) {
            holdingThread = null;
        }
    }

    @Override
    public boolean isHeldByCurrentThread() {
        return lockStatus.get() > 0 && holdingThread == Thread.currentThread();
    }

    // 返回 [0, maxValue) 的随机值
    private long randomValueWithin(long maxValue) {
        return System.nanoTime() % maxValue;
    }

}
