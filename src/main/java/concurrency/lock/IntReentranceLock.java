package concurrency.lock;

import java.util.concurrent.atomic.AtomicInteger;
import static concurrency.PrintLog.printLog;

/**
 * 可重入锁。仅使用CAS实现。
 */
public class IntReentranceLock {

    // 0表示锁空闲。大于零表示加锁次数。
    private AtomicInteger lockCount = new AtomicInteger(0);

    // 持有锁的线程
    private volatile Thread holdingThread = null;

    //--------

    /**
     * 阻塞式加锁。直到加锁成功，或者线程被中断，才返回。
     *
     * @throws InterruptedException
     */
    public void lock() throws InterruptedException {
        for (int tryCount = 0;; tryCount++) {
            // 尝试10次，休息一会
            if (tryCount > 0 && tryCount % 10 == 0) {
//                printLog("tryCount=" + tryCount + ", have a sleep.");
                Thread.sleep(randomValueWithin(3));
            }

            // 不能根据lockCount来判断锁状态。因为解锁时，先减count，再清空线程引用。
            // 如果通过lockCount来判断状态，线程解锁时在这两步中间可能被另一个线程插入，导致清除线程引用时出错。
            // 所以，必须根据解锁最后一步的特征，来判断状态。
            if (!isFree()) {
                // 被当前线程占用。重入加锁
                if (isHeldByCurrentThread()) {
                    lockCount.incrementAndGet();
                    printLog("lock success. value=" + lockCount.get());
                    return;
                } else {
                    // 锁仍在被其他线程占用
                    continue;
                }
            }

            if (lockCount.compareAndSet(0, 1)) {
                holdingThread = Thread.currentThread();
                printLog("lock success. value=" + lockCount.get());
                return;
            }
        }
    }

    public void unlock() {
        if (!isHeldByCurrentThread()) {
            throw new IllegalMonitorStateException();
        }

        // decrease lock count。if decrease to zero, set holdingThread = null
        int newCount = lockCount.decrementAndGet();
        if (0 == newCount) {
            holdingThread = null;
        }
        printLog("unlock success, lockCount=" + newCount);
    }

    public boolean isHeldByCurrentThread() {
        return Thread.currentThread() == holdingThread;
    }

    /**
     * 返回锁是否空闲。
     * 不能根据lockCount来判断锁状态。因为解锁时，先减count，再清空线程引用。
     * 如果通过lockCount来判断状态，线程解锁时在这两步中间可能被另一个线程插入，导致清除线程引用时出错。
     * 所以，必须根据解锁最后一步的特征，来判断状态。
     */
    public boolean isFree() {
        return holdingThread == null;
    }

    // 返回 [0, maxValue) 的随机值
    private long randomValueWithin(long maxValue) {
        return System.nanoTime() % maxValue;
    }
}
