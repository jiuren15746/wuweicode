package concurrency.lock;

import concurrency.lock.impl.ReentrantLock2;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.collections.Lists;

public class ReentranceLockTest {

    @Test
    public void unlock_但是没有持有锁() throws Exception {
        ReentrantLockJiuren lock = new ReentrantLock2();
        try {
            lock.unlock();
        } catch (IllegalMonitorStateException ex) {
            // expectation, do nothing
        }
    }

    @Test
    public void lock_单线程_多次加锁() throws Exception {
//        IntReentranceLock lock = new IntReentranceLock();
        ReentrantLockJiuren lock = new ReentrantLock2();

        for (int i = 0; i < 10; ++i) {
            lock.lock();
            Assert.assertTrue(lock.isHeldByCurrentThread());
        }
        for (int i = 0; i < 10; ++i) {
            lock.unlock();
            if (i < 9) {
                Assert.assertTrue(lock.isHeldByCurrentThread());
            } else {
                Assert.assertFalse(lock.isHeldByCurrentThread());
            }
        }
    }

    @Test
    public void lock_多线程() throws Exception {

//        final IntReentranceLock lock = new IntReentranceLock();
        ReentrantLockJiuren lock = new ReentrantLock2();

        // 每个线程加锁后解锁
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    lock.lock();
                    Assert.assertTrue(lock.isHeldByCurrentThread());

                    Thread.sleep(100);

                    lock.unlock();
                    Assert.assertFalse(lock.isHeldByCurrentThread());

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };

        int threadCount = 50;
        List<Thread> threads = Lists.newArrayList();
        for (int i = 0; i < threadCount; ++i) {
            Thread t = new Thread(task);
            t.setName("thread-" + i);
            t.start();
            threads.add(t);
        }

        for (Thread t : threads) {
            t.join();
        }
    }

}
