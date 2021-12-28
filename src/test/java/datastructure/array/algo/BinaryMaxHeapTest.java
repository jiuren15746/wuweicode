package datastructure.array.algo;

import org.testng.Assert;
import org.testng.annotations.Test;

public class BinaryMaxHeapTest {

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

        Assert.assertEquals(heap.getElements(), new int[]{90, 40, 70, 10, 20, 50, 60});

        Assert.assertEquals(heap.poll(), 90);
        Assert.assertEquals(heap.getElements(), new int[]{70, 40, 60, 10, 20, 50});
        Assert.assertEquals(heap.poll(), 70);
        Assert.assertEquals(heap.getElements(), new int[]{60, 40, 50, 10, 20});
        Assert.assertEquals(heap.poll(), 60);
        Assert.assertEquals(heap.getElements(), new int[]{50, 40, 20, 10});
    }
}
