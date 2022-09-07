package datastructure.tree.binarytree;

import datastructure.array.algo.heap.impl.BinaryMaxHeap;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BinaryMaxHeapTest {

    @Test
    public void check() {
        BinaryMaxHeap heap = new BinaryMaxHeap(new int[]{14, 1, 8, 3, 2});
        Assert.assertEquals(heap.check(), false);

        heap = new BinaryMaxHeap(new int[]{14, 3, 8, 1, 2});
        Assert.assertEquals(heap.check(), true);
    }

    @Test
    public void test() {
        BinaryMaxHeap heap = new BinaryMaxHeap(20);
        heap.offer(10);
        heap.offer(50);
        heap.offer(60);
        heap.offer(40);
        heap.offer(20);
        heap.offer(70);
        heap.offer(90);

        Assert.assertEquals(heap.poll(), 90);
        Assert.assertEquals(heap.poll(), 70);
        Assert.assertEquals(heap.poll(), 60);
        Assert.assertEquals(heap.poll(), 50);
        Assert.assertEquals(heap.poll(), 40);
        Assert.assertEquals(heap.poll(), 20);
        Assert.assertEquals(heap.poll(), 10);
    }
}
