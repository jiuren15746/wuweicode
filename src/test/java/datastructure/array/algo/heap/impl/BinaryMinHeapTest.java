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
    public void size_not_empty() {
        for (int i = 0; i < 100; ++i) {
            minHeap.offer(System.nanoTime());
        }
        assertEquals(minHeap.size(), 100);
    }
}
