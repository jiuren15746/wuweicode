package concurrency.blockingqueue;

import org.testng.annotations.Test;
import org.testng.collections.Lists;

import java.util.List;

public class FairnessBoundedBlockingQueueTest {

    @Test
    public void test() throws Exception {
        FairBlockingQueueImpl queue = new FairBlockingQueueImpl(5);

        Runnable produce = () -> {
            long time = System.nanoTime();
            try {
                queue.offer(time);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };

        Runnable consume = () -> {
            try {
                queue.poll();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };

        List<Thread> threads = Lists.newArrayList();

        for (int i = 0; i < 10; ++i) {
            Thread producer = new Thread(produce);
            producer.setName("P" + i);
            producer.start();
            threads.add(producer);
        }

        Thread.sleep(5000L);

        for (int i = 0; i < 10; ++i) {
            Thread consumer = new Thread(consume);
            consumer.setName("C" + i);
            consumer.start();
            threads.add(consumer);
        }

        for (Thread t : threads) {
            t.join();
        }
    }
}
