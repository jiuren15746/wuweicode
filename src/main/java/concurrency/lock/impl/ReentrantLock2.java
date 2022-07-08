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
    private volatile Thread owner;
    //=============

    @Override
    public void lock() throws InterruptedException {
        if (isHeldByCurrentThread()) {
            lockStatus.incrementAndGet();
            return;
        }
        for (int count = 0; ; count++) {
            if (owner == null && lockStatus.compareAndSet(0, 1)) {
                owner = Thread.currentThread();
                return;
            }
            // spin 10次才sleep一次
            if (count > 0 && count % 10 == 0) {
                Thread.sleep(randomValueWithin(3));
            }
        }
    }

    @Override
    public void unlock() {
        if (!isHeldByCurrentThread()) {
            throw new IllegalMonitorStateException();
        }
        // !!!释放锁的时候，先减lockStatus，再清空holdingThread
        // !!!所以加锁的时候，首先判断holdingThread为null，然后才能增加lockStatus。否则会出大问题。
        if (lockStatus.decrementAndGet() == 0) {
            owner = null;
        }
    }

    @Override
    public boolean isHeldByCurrentThread() {
        return owner == Thread.currentThread();
    }

    @Override
    public String toString() {
        return "ReentrantLock2(lockStatus=" + lockStatus + ", owner=" + owner + ")";
    }

    // 返回 [0, maxValue) 的随机值
    private long randomValueWithin(long maxValue) {
        return System.nanoTime() % maxValue;
    }

}
