package concurrency.lock;

import org.testng.Assert;
import org.testng.annotations.Test;

public class IntReentranceLockTest {

    @Test
    public void lock_单线程() throws Exception {

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
}
