package datastructure.skiplist.orderbook;

public class OrderQueue {

    private Order[] queue;

    /**
     * increase, no wrap
     */
    private long tail = -1;

    /**
     * increase, no wrap
     */
    private long head = -1;

    private int size = 0;

    private int mask;
    //========

    public OrderQueue() {
        queue = new Order[4];
        mask = queue.length - 1;
    }

    public int size() {
        return size;
    }

    public int capacity() {
        return queue.length;
    }

    public void enqueue(Order order) {
        expandIfNecessary();
        queue[(int) (++tail & mask)] = order;
        if (size++ == 0) {
            head = 0;
        }
    }

    public Order dequeue() {
        if (size == 0) {
            return null;
        }
        Order order = queue[(int) (head++ & mask)];
        if (--size == 0) {
            head = tail = -1;
        }
        return order;
    }

    public Order peek() {
        if (size == 0) {
            return null;
        }
        return queue[(int) (head & mask)];
    }

    private void expandIfNecessary() {
        if (size < queue.length) {
            return;
        }

        int headIdx = (int) (head & mask);
        int tailIdx = (int) (tail & mask);
        Order[] newQueue = new Order[(mask + 1) << 1];

        if (headIdx == 0) {
            System.arraycopy(queue, 0, newQueue, 0, size);
        } else {
            System.arraycopy(queue, headIdx, newQueue, 0, queue.length - headIdx);
            System.arraycopy(queue, tailIdx, newQueue, queue.length - headIdx, tailIdx + 1);
        }
        queue = newQueue;
        mask = queue.length - 1;
        head = 0;
        tail = size - 1;
    }

    protected long getHead() {
        return head;
    }
    protected long getTail() {
        return tail;
    }
    protected int getMask() {
        return mask;
    }
}
