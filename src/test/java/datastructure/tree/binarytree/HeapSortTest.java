package datastructure.tree.binarytree;

import org.testng.Assert;
import org.testng.annotations.Test;

public class HeapSortTest {

    @Test
    public void test_sort() {
        int[] array = {5, 12, 11, 13, 6, 7};

        HeapSort.sort(array);

        Assert.assertEquals(array, new int[]{5, 6, 7, 11, 12, 13});
    }
}
