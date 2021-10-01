package concurrency;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

import static concurrency.PrintLog.printLog;

/**
 * 难点：
 *  <li>队列不用来保存数据，而是用来对线程排队。
 *  <li>有两种锁：一种是节点锁，用来同步生产者、消费者线程。另一种是队列锁，用来保证对队列的串行访问。
 *      但是当线程在队列节点上wait前，需要释放队列锁。
 */
public class SynchronousQueueJurin<E> {

    private final List<Node> queue = new LinkedList<>();
    //    private final IntReentranceLock queueLock = new IntReentranceLock();
    private final ReentrantLock queueLock = new ReentrantLock();

    private class Node {
        private final boolean isPublisher;
        // indicate whether exchange has happened
        private volatile boolean exchangeFlag = false;
        // used for data exchange between publisher and consumer
        private volatile E data;
        private Thread waitingThread;

        Node(boolean isPublisher, E data) {
            this.isPublisher = isPublisher;
            this.data = data;
            this.waitingThread = Thread.currentThread();
        }

        void exchangeForConsumerNode(E data) {
            this.data = data;
            this.exchangeFlag = true;
        }

        E exchangeForProducerNode() {
            E data = this.data;
            this.exchangeFlag = true;
            return data;
        }
    }

    /**
     * 有两把锁。第一把锁是整个queue的锁，确保对队列的串行访问。
     * 第二把锁，是插入的生产者节点，用来实现生产者、消费者之间的同步。
     *
     * !!! 当wait前、或返回前，需要释放queue锁。
     */
    public void offer(E element) throws InterruptedException {
        queueLock.lock();
        try {
            // find consumer node
            Node consumerNode = findUnExchangedNode(false);

            // if consumer node exists, exchange data, then wake it up
            if (null != consumerNode) {
                synchronized (consumerNode) {
                    printLog("find consumer node, wake it up."+ consumerNode.waitingThread.getName() );
                    consumerNode.exchangeForConsumerNode(element);
                    consumerNode.notify();
                    return;
                }
            }

            // Else, insert producer node and wait
            Node producerNode = new Node(true, element);
            queue.add(producerNode);
            printLog("insert producer node and wait.");

            // check exchangeFlag after wake up, then remove node
            synchronized (producerNode) {
                while (producerNode.exchangeFlag == false) {
                    queueLock.unlock();
                    producerNode.wait();
                    queueLock.lock();
                }
                queue.remove(producerNode);
            }

            printLog("after wake up, just return.");
            return;
        }
        finally {
            if (queueLock.isHeldByCurrentThread()) {
                queueLock.unlock();
            }
        }
    }

    //
    public E take() throws InterruptedException {
        queueLock.lock();
        try {
            // find producer node
            Node producerNode = findUnExchangedNode(true);

            // if producer node exists, exchange data, then wake it up
            if (null != producerNode) {
                synchronized (producerNode) {
                    printLog("find producer node, wake it up." + producerNode.waitingThread.getName());
                    E data = producerNode.exchangeForProducerNode();
                    producerNode.notify();
                    return data;
                }
            }

            // Else, insert consumer node and wait
            Node consumerNode = new Node(false, null);
            queue.add(consumerNode);
            printLog("insert consumer node and wait.");

            // check exchangeFlag after wake up, then remove node
            synchronized (consumerNode) {
                while (consumerNode.exchangeFlag == false) {
                    queueLock.unlock();
                    consumerNode.wait();
                    queueLock.lock();
                }
                queue.remove(consumerNode);
            }

            printLog("after wake up, return data=" + consumerNode.data);
            return consumerNode.data;
        }
        finally {
            if (queueLock.isHeldByCurrentThread()) {
                queueLock.unlock();
            }
        }
    }

    /**
     * 找到没有交换的节点
     */
    private Node findUnExchangedNode(boolean isPublisher) {
        return queue.stream()
                .filter(x -> x.isPublisher == isPublisher && x.exchangeFlag == false)
                .findFirst()
                .orElse(null);
    }

    static public void main(String[] args) throws Exception {

        final SynchronousQueueJurin<String> queue = new SynchronousQueueJurin<>();

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

        int consumerCount = 5;
        List<Thread> threadList = new ArrayList<>();

        // start producer
        for (int i = 0; i < consumerCount; ++i) {
            Thread producer = new Thread(producerTask);
            producer.setName("producer" + i);
            producer.start();
            threadList.add(producer);
        }

        Thread.sleep(1000);

        // start consumer
        for (int i = 0; i < consumerCount; ++i) {
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
