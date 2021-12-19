package concurrency.lock;

/**
 * 自定义的可重入锁接口。
 */
public interface ReentrantLockJiuren {

    /**
     * 加锁。
     * <li>如果锁空闲，尝试加锁。如成功则立即返回。否则不停地重试，直到加锁成功，或线程被中断。
     * <li>如果锁已经被当前线程锁定，立即返回。
     * <li>如果锁被其他线程锁定，线程不停探测锁状态，等待锁被释放后，尝试加锁。
     * @throws InterruptedException
     */
    void lock() throws InterruptedException;

    /**
     * 释放锁。
     * <li>如果线程没有获得锁，调用该方法会导致抛出 IllegalMonitorStateException。
     * <li>线程unlock次数只有和lock次数相等，才能真正释放掉锁。
     */
    void unlock();

    /**
     * 监测当前线程是否持有锁。
     * @return
     */
    boolean isHeldByCurrentThread();
}
