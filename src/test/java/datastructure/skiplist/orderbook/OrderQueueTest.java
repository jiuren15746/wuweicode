package datastructure.skiplist.orderbook;

import org.testng.annotations.Test;

import java.util.UUID;

import static org.testng.Assert.*;

public class OrderQueueTest {

    @Test
    public void enqueue() {
        OrderQueue queue = new OrderQueue();
        assertEquals(queue.size(), 0);
        assertEquals(queue.capacity(), 4);

        Order order = new Order();
        queue.enqueue(order);
        assertEquals(queue.size(), 1);
        assertEquals(queue.getHead(), 0);
        assertEquals(queue.getTail(), 0);
    }

    @Test
    public void dequeue_empty() {
        OrderQueue queue = new OrderQueue();
        assertNull(queue.dequeue());
    }

    @Test
    public void peek_dequeue_notEmpty() {
        String orderId = UUID.randomUUID().toString();
        Order order = new Order();
        order.setOrderId(orderId);

        OrderQueue queue = new OrderQueue();
        queue.enqueue(order);

        // peek
        Order peekOrder = queue.peek();
        assertTrue(peekOrder == order);
        assertEquals(queue.size(), 1);
        assertEquals(queue.getHead(), 0);
        assertEquals(queue.getTail(), 0);

        // dequeue
        Order dequeueOrder = queue.dequeue();
        assertTrue(dequeueOrder == order);
        assertEquals(queue.size(), 0);
        assertEquals(queue.getHead(), -1);
        assertEquals(queue.getTail(), -1);
    }

    @Test
    public void enqueue_expand() {
        OrderQueue queue = new OrderQueue();
        assertEquals(queue.size(), 0);
        assertEquals(queue.capacity(), 4);

        for (int i = 0; i < 4; ++i) {
            queue.enqueue(Order.builder()
                    .orderId("" + i)
                    .build());
        }
        assertEquals(queue.size(), 4);
        assertEquals(queue.capacity(), 4);

        queue.enqueue(Order.builder()
                .orderId("4")
                .build());
        assertEquals(queue.capacity(), 8);
        assertEquals(queue.size(), 5);
        assertEquals(queue.getHead(), 0);
        assertEquals(queue.getTail(), 4);

        for (int i = 0; i < queue.size(); ++i) {
            assertEquals(queue.dequeue().getOrderId(), "" + i);
        }
    }

    @Test
    public void enqueue_dequeue_expand() {
        OrderQueue queue = new OrderQueue();
        assertEquals(queue.size(), 0);
        assertEquals(queue.capacity(), 4);

        for (int i = 0; i < 4; ++i) {
            queue.enqueue(Order.builder()
                    .orderId("" + i)
                    .build());
        }
        assertEquals(queue.size(), 4);
        assertEquals(queue.capacity(), 4);

        // 出一个
        queue.dequeue();
        assertEquals(queue.capacity(), 4);
        assertEquals(queue.size(), 3);
        assertEquals(queue.getHead(), 1);
        assertEquals(queue.getTail(), 3);

        // 入一个
        queue.enqueue(Order.builder()
                .orderId("4")
                .build());
        assertEquals(queue.capacity(), 4);
        assertEquals(queue.size(), 4);
        assertEquals(queue.getHead(), 1);
        assertEquals(queue.getTail(), 4);

        // 再入一个
        queue.enqueue(Order.builder()
                .orderId("5")
                .build());
        assertEquals(queue.capacity(), 8);
        assertEquals(queue.size(), 5);
        assertEquals(queue.getHead(), 0);
        assertEquals(queue.getTail(), 4);

        for (int i = 1; i <= queue.size(); ++i) {
            assertEquals(queue.dequeue().getOrderId(), "" + i);
        }
    }
}
