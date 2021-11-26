package datastructure.array.algo.twopointers;

import org.testng.annotations.Test;

import static datastructure.array.algo.twopointers.QuickSort.quickSort;
import static org.testng.Assert.assertEquals;

public class QuickSort2 {

    public void quickSort(int[] array, int begin, int end) {
        if (begin >= end) {
            return;
        }
        int base = array[begin];
        int i = begin;
        int j = end;

        while (i < j) {
            while (i < j && array[j] > base) j--;
            while (i < j && array[i] <= base) i++;

            if (i < j) {
                swap(array, i, j);
            } else {
                swap(array, i, begin);
            }
        }

        quickSort(array, begin, i-1);
        quickSort(array, i+1, end);
    }

    static private void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }


    @Test
    public void test() {
        int[] array = {6, 1, 2, 7, 9, 3, 9, 6, 4, 5, 10, 8};
        quickSort(array, 0, array.length - 1);

        assertEquals(array, new int[]{1, 2, 3, 4, 5, 6, 6, 7, 8, 9, 9, 10});
    }
}
