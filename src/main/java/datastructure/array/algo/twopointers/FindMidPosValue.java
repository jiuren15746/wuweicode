package datastructure.array.algo.twopointers;

import static datastructure.array.algo.twopointers.QuickSort2.quickSort;
import static org.testng.Assert.assertEquals;

/**
 * 找到排序后的数组中位数.
 */
public class FindMidPosValue {

    static public int findTargetPosValue(int[] array, int begin, int end, int targetPos) {
        int base = array[begin];
        int i = begin;
        int j = end;
        while (i < j) {
            while (array[j] > base && i < j) j--;
            while (array[i] <= base && i < j) i++;
            if (i < j) {
                swap(array, i, j);
            } else {
                swap(array, begin, i);
            }
        }

        if (i == targetPos) {
            return array[targetPos];
        } else if (i < targetPos) {
            return findTargetPosValue(array, i+1, end, targetPos);
        } else {
            return findTargetPosValue(array, begin, i-1, targetPos);
        }
    }

    static private void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
