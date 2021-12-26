package concurrency.blockingqueue;

import concurrency.PrintLog;

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
    }

    private Node head = new Node(null);
    private Node tail = head;
    private int size = 0;
    private final int capacity;

    public FairnessBoundedBlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public synchronized boolean offer(Object obj) throws InterruptedException {
        while (size >= capacity) {
            PrintLog.printLog("thread is about to wait, obj=" + obj);
            this.wait();
        }
        Node temp = new Node(obj);
        tail.next = temp;
        tail = temp;
        size++;
        this.notifyAll();
        PrintLog.printLog("offer success, obj=" + obj);
        return true;
    }

    @Override
    public synchronized Object poll() throws InterruptedException {
        while (size == 0) {
            PrintLog.printLog("thread is about to wait");
            this.wait();
        }
        Object result = head.next.value;
        head.next = head.next.next;
        size--;
        this.notifyAll();
        PrintLog.printLog("poll success, result=" + result);
        return result;
    }

    @Override
    public List toList() {
        List list = new ArrayList();
        for (Node i = head.next; i != null; i = i.next) {
            list.add(i.value);
        }
        return list;
    }

}
