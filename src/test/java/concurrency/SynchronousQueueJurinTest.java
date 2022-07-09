package concurrency;

import static concurrency.PrintLog.printLog;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.testng.annotations.Test;

public class SynchronousQueueJurinTest {
    private SynchronousQueueJurin<String> queue = new SynchronousQueueJurin<>();

    Runnable producerTask = new Runnable() {
        @Override
        public void run() {
            String data = UUID.randomUUID().toString();
            try {
                queue.offer(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    Runnable consumerTask = new Runnable() {
        @Override
        public void run() {
            try {
                Object data = queue.take();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };


    @Test
    public void test() throws Exception {

        int count = 30;
        List<Thread> threadList = new ArrayList<>();

        // start producer
        for (int i = 0; i < count; ++i) {
            Thread producer = new Thread(producerTask);
            producer.setName("producer" + i);
            producer.start();
            threadList.add(producer);
        }

//        Thread.sleep(1000);

        // start consumer
        for (int i = 0; i < count; ++i) {
            Thread consumer = new Thread(consumerTask);
            consumer.setName("consumer" + i);
            consumer.start();
            threadList.add(consumer);
        }

        // wait for threads to die
        for (Thread t : threadList) {
            t.join();
        }
        printLog("exit");
    }
}
