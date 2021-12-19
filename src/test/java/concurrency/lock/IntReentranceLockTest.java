package concurrency.lock;

import concurrency.lock.impl.IntReentranceLock;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.collections.Lists;

import java.util.List;

public class IntReentranceLockTest {

    @Test
    public void lock_单线程_多次加锁() throws Exception {

        IntReentranceLock lock = new IntReentranceLock();

        lock.lock();
        Assert.assertTrue(lock.isHeldByCurrentThread());
        lock.lock();
        Assert.assertTrue(lock.isHeldByCurrentThread());
        lock.lock();
        Assert.assertTrue(lock.isHeldByCurrentThread());

        lock.unlock();
        Assert.assertTrue(lock.isHeldByCurrentThread());

        lock.unlock();
        Assert.assertTrue(lock.isHeldByCurrentThread());

        lock.unlock();
        Assert.assertFalse(lock.isHeldByCurrentThread());
    }

    @Test
    public void lock_多线程() throws Exception {

        final IntReentranceLock lock = new IntReentranceLock();

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

        int threadCount = 10;
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

        Assert.assertTrue(lock.isFree());
    }

}
