package datastructure.array.algo.twopointers;

public class QuickSort2 {

    public static void quickSort(int[] array, int begin, int end) {
        if (begin >= end) {
            return;
        }
        int base = array[begin];
        int i = begin;
        int j = end;

        while (i < j) {
            // 左侧小于等于，右侧大于
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
}
