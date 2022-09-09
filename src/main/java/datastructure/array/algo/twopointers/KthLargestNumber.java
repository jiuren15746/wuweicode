package datastructure.array.algo.twopointers;

import static org.testng.Assert.*;


public class KthLargestNumber {


    public static int findKthLargest(int[] array, int k) {
        int begin = 0;
        int end = array.length - 1;

        for (;;) {
            final int baseValue = array[begin];

            for (int i = begin, j = end; i < j; ) {
                while (i < j && array[j] > baseValue) j--;
                while (i < j && array[i] <= baseValue) i++;
                if (i != j) {
                    swap(array, i, j);
                    continue;
                }
                swap(array, begin, i);
                int temp = array.length - i - k;
                if (temp == 0) {
                    return array[i];
                } else if (temp > 0) {
                    // 缩小范围在右半边
                    begin = i + 1;
                } else {
                    // 缩小范围在左半边
                    end = i - 1;
                }
            }
        }
    }

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void main(String[] args) {
        int[] array = {7, 5, 15, 3, 17, 2, 20, 24, 1, 9, 12, 8};
        // sorted: 1, 2, 3, 5, 7, 8, 9, 12, 15, 17, 20, 24

        assertEquals(findKthLargest(array, 1), 24);
        assertEquals(findKthLargest(array, 2), 20);
        assertEquals(findKthLargest(array, 3), 17);
        assertEquals(findKthLargest(array, 4), 15);
        assertEquals(findKthLargest(array, 5), 12);
        assertEquals(findKthLargest(array, 6), 9);
    }
}
