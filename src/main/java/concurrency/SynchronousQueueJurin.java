package concurrency;


import static concurrency.PrintLog.printLog;

import concurrency.lock.ReentrantLockJiuren;
import concurrency.lock.impl.ReentrantLock2;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * 难点：
 *  <li>队列不用来保存数据，而是用来对线程排队。该数据结构更像是一种同步器。
 *  <li>使用到两种锁：一种是节点锁，用来同步生产者、消费者线程。另一种是队列锁，用来保证对队列的串行访问。
 *      但是当线程在队列节点上wait前，需要释放队列锁。
 *
 * 把它想象成成双成对的匹配器，但是它不考虑双方是否情愿，只要是异性，都可以匹配成功。
 */
public class SynchronousQueueJurin<E> {

    private final List<Node> queue = new LinkedList<>();
    private final ReentrantLockJiuren queueLock = new ReentrantLock2();

    private class Node {
        // 当前阻塞线程是生产者还是消费者
        private final boolean isPublisher;
        // indicate whether exchange has happened
        private volatile boolean exchangeFlag = false;
        // 交换的数据
        private volatile E data;
        private Thread waitingThread;

        Node(boolean isPublisher, E data) {
            this.isPublisher = isPublisher;
            this.data = data;
            this.waitingThread = Thread.currentThread();
        }

        // 生产者调用此方法，与阻塞的消费者交换数据，并唤醒消费者线程
        synchronized void exchangeForConsumerNode(E data) {
            this.data = data;
            this.exchangeFlag = true;
            notify();
        }

        // 消费者调用此方法，与阻塞的生产者交换数据，并唤醒生产者
        synchronized E exchangeForProducerNode() {
            E data = this.data;
            this.exchangeFlag = true;
            notify();
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
            // 1. 如果存在消费者节点，交换数据，唤醒消费者，最后返回
            Node consumerNode = findUnExchangedNode(false);
            if (null != consumerNode) {
                printLog("find consumer node, wake it up." + consumerNode.waitingThread.getName());
                consumerNode.exchangeForConsumerNode(element);
                return;
            }

            // 2.1 插入生产者节点
            Node producerNode = new Node(true, element);
            queue.add(producerNode);
            printLog("insert producer node and wait.");

            // 2.2 在节点上等待消费者
            synchronized (producerNode) {
                while (producerNode.exchangeFlag == false) {
                    queueLock.unlock();
                    producerNode.wait();
                    queueLock.lock();
                }
                queue.remove(producerNode);
            }
            printLog("after wake up, just return.");
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
            // 1. 如果存在生产者节点，交换数据后将其唤醒，最后返回数据
            Node producerNode = findUnExchangedNode(true);
            if (null != producerNode) {
                printLog("find producer node, wake it up." + producerNode.waitingThread.getName());
                return producerNode.exchangeForProducerNode();
            }

            // 2.1 插入消费者节点
            Node consumerNode = new Node(false, null);
            queue.add(consumerNode);
            printLog("insert consumer node and wait.");

            // 2.2 在节点上等待生产者。知道被交换后，删除节点，返回数据。
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
}
