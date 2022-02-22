package datastructure.array.algo.twopointers;

import org.testng.annotations.Test;

import static datastructure.array.algo.twopointers.FindMidPosValue.findTargetPosValue;
import static datastructure.array.algo.twopointers.QuickSort2.quickSort3;
import static org.testng.Assert.assertEquals;

public class QuickSortTest {

    @Test
    public void test() {
        int[] array = {6, 1, 2, 7, 9, 3, 9, 6, 4, 5, 10, 8};
        quickSort3(array, 0, array.length - 1);

        assertEquals(array, new int[]{1, 2, 3, 4, 5, 6, 6, 7, 8, 9, 9, 10});

        int midPos = array.length / 2;
        int value = findTargetPosValue(array, 0, array.length - 1, midPos);
        assertEquals(value, 6);
    }

    @Test
    public void test2() {
        int[] array = {6, 5, 4, 3, 2, 1, 0};
        quickSort3(array, 0, array.length - 1);

        assertEquals(array, new int[]{0, 1, 2, 3, 4, 5, 6});

        int midPos = array.length / 2;  // pos=3
        int value = findTargetPosValue(array, 0, array.length - 1, midPos);
        assertEquals(value, 3);
    }
}
