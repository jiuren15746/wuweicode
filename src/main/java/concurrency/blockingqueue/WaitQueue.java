package concurrency.blockingqueue;


import concurrency.PrintLog;

public class WaitQueue {

    class WaitNode {
        // 生产者、消费者沟通的变量。
        boolean released = false;
        WaitNode next;

        synchronized void doWait() throws InterruptedException {
            while (!released) {
                PrintLog.printLog("wait()");
                wait();
                PrintLog.printLog("wake up");
            }
        }

        synchronized void doNotify() {
            if (!released) {
                released = true;
                PrintLog.printLog("notify()");
                notify();
            }
        }
    }

    private volatile WaitNode head;
    private volatile WaitNode tail;

    public WaitQueue() {
        this.head = new WaitNode();
        this.tail = head;
    }

    public synchronized WaitNode enqueue() {
        WaitNode temp = new WaitNode();
        tail.next = temp;
        tail = temp;
        return temp;
    }

    public synchronized WaitNode dequeue() {
        WaitNode temp = head.next;
        if (temp == null) {
            return null;
        } else {
            head.next = head.next.next;
            // 注意这里踩过的坑，当队列为空时，要更新tail
            if (head.next == null) {
                tail = head;
            }
            return temp;
        }
    }
}
