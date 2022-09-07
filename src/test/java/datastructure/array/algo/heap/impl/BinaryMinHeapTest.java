package datastructure.array.algo.heap.impl;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class BinaryMinHeapTest {

    private BinaryMinHeap<Long> minHeap;

    @BeforeMethod
    public void init() {
        minHeap = new BinaryMinHeap<>();
    }

    @Test
    public void size_empty() {
        assertEquals(minHeap.size(), 0);
    }

    @Test
    public void offer() {
        for (int i = 0; i < 100; ++i) {
            minHeap.offer(System.nanoTime());
        }
        assertEquals(minHeap.size(), 100);
    }

    @Test
    public void poll() {
        for (int i = 0; i < 10000; ++i) {
            minHeap.offer(new Long(i));
        }

        for (int i = 0; i < 10000; ++i) {
            assertEquals(minHeap.poll(), new Long(i));
        }
    }
}
