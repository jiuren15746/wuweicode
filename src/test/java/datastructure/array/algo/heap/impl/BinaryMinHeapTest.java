package datastructure.array.algo.heap.impl;

import static org.testng.Assert.*;
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
}
