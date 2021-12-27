package concurrency.blockingqueue;

import concurrency.PrintLog;
import concurrency.blockingqueue.WaitQueue.WaitNode;
import concurrency.lock.ReentrantLockJiuren;
import concurrency.lock.impl.ReentrantLock2;

import java.util.ArrayList;
import java.util.List;


public class FairnessBoundedBlockingQueue implements Queue {
    class Node {
        Object value;
        Node next;

        Node(Object obj) {
            this.value = obj;
            next = null;
        }

        public String toString() {
            return "Node (" + value + ", " + super.toString() + ")";
        }
    }

    private volatile Node head;
    private volatile Node tail;
    private volatile int size;
    private final int capacity;

    ReentrantLockJiuren lock = new ReentrantLock2();
    private WaitQueue waitQueue = new WaitQueue();

    public FairnessBoundedBlockingQueue(int capacity) {
        this.capacity = capacity;
        this.head = new Node(null);
        this.tail = head;
        this.size = 0;
    }

    @Override
    public boolean offer(Object obj) throws InterruptedException {
        lock.lock();
        try {
            while (size >= capacity) {
                // wait. 需要释放队列锁。被唤醒后再次获得锁。
                // synchronized无法实现该功能。所以用显示的Lock对象。
                WaitNode waitNode = waitQueue.enqueue();
                lock.unlock();
                waitNode.doWait();
                lock.lock();
            }

            Node temp = new Node(obj);
            tail.next = temp;
            tail = temp;
            size++;
            PrintLog.printLog("offer success, " + toString());

            // notify
            WaitNode waitNode = waitQueue.dequeue();
            if (waitNode != null) {
                waitNode.doNotify();
            }
            return true;

        } finally {
            lock.unlock();
        }
    }

    @Override
    public Object poll() throws InterruptedException {
        lock.lock();
        try {
            while (size == 0) {
                // wait.
                WaitNode waitNode = waitQueue.enqueue();
                lock.unlock();
                waitNode.doWait();
                lock.lock();
            }

            Object result = head.next.value;
            head.next = head.next.next;
            // 这里踩过的坑，一定要记住。当队列为空时，记得更新tail
            if (head.next == null) {
                tail = head;
            }
            size--;

            // notify
            WaitNode waitNode = waitQueue.dequeue();
            if (waitNode != null) {
                waitNode.doNotify();
            }
            PrintLog.printLog("poll success, " + this.toString());
            return result;

        } finally {
            lock.unlock();
        }
    }

    @Override
    public List toList() {
        List list = new ArrayList();
        for (Node i = head.next; i != null; i = i.next) {
            list.add(i.value);
        }
        return list;
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("capacity=").append(capacity).append(", ");
        sb.append("size=").append(size).append(", ");
        sb.append(toList());
        return sb.toString();
    }

}
