package concurrency;

import java.util.concurrent.atomic.AtomicInteger;
import static concurrency.PrintLog.printLog;

/**
 * 可重入的锁。
 */
public class IntReentranceLock {

    private AtomicInteger value = new AtomicInteger(0);

    private Thread holdingThread = null;

    //--------

    public void lock() throws InterruptedException {
        if (isHeldByCurrentThread()) {
            value.incrementAndGet();
            return;
        }

        for (;;) {
            if (value.compareAndSet(0, 1)) {
                holdingThread = Thread.currentThread();
                printLog("lock success. value=" + value.get());
                return;
            }
            Thread.sleep(1);
        }
    }

    public void unlock() {
        if (!isHeldByCurrentThread()) {
            throw new IllegalMonitorStateException();
        }
        value.decrementAndGet();
        holdingThread = null;
        printLog("unlock success, value=" + value.get());
    }

    public boolean isHeldByCurrentThread() {
        return Thread.currentThread() == holdingThread;
    }

}
