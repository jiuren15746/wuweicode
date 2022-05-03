package datastructure.array.algo.twopointers;

public class QuickSort3 {

    public static void quickSort(int[] array, int begin, int end) {
        // 保护条件。这样下面递归的地方就不用再判断了
        if (begin >= end) {
            return;
        }

        final int base = array[begin];
        int i = begin;
        int j = end;
        while (i < j) {
            while (i < j && array[j] > base) j--;
            while (i < j && array[i] <= base) i++;
            if (i != j) {
                swap(array, i, j);
            } else { // i meets j
                swap(array, begin, i);
                quickSort(array, begin, i - 1);
                quickSort(array, i + 1, end);
            }
        }
    }

    static public void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
