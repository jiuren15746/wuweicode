package algo.binarysearch;

import org.testng.Assert;
import org.testng.annotations.Test;

import static algo.binarysearch.BinarySearchTemplate.*;

public class BinarySearchTemplateTest {

    @Test
    public void target在数组头() {
        int[] array = new int[]{1, 4, 4, 5, 7, 7, 8, 9, 9, 10};
        int target = 1;

        Assert.assertEquals(binarySearch(array, target), 0);
    }

    @Test
    public void target在数组尾() {
        int[] array = new int[]{1, 4, 4, 5, 7, 7, 8, 9, 9, 10};
        int target = 10;

        Assert.assertEquals(binarySearch(array, target), array.length - 1);
    }

    @Test
    public void target在数组中有重复() {
        int[] array = new int[]{1, 4, 4, 5, 7, 7, 8, 9, 9, 10};
        int target = 4;

        Assert.assertEquals(binarySearch(array, target), 1);
    }

    @Test
    public void target比数组头还小() {
        int[] array = new int[]{1, 4, 4, 5, 7, 7, 8, 9, 9, 10};
        int target = -2;

        Assert.assertEquals(binarySearch(array, target), -1);
    }

    @Test
    public void target比数组尾还大() {
        int[] array = new int[]{1, 4, 4, 5, 7, 7, 8, 9, 9, 10};
        int target = 15;

        Assert.assertEquals(binarySearch(array, target), -1);
    }
}
