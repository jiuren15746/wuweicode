package datastructure.array.algo.twopointers;

public class QuickSort2 {

    public static void quickSort(int[] array, int begin, int end) {
        if (!(begin >= 0 && begin < end)) { // 2022.2.22调整
            return;
        }
        int base = array[begin];
        int i = begin;
        int j = end;

        while (i < j) {
            // 左侧小于等于base，右侧大于base
            while (i < j && array[j] > base) j--;
            while (i < j && array[i] <= base) i++;
            if (i != j) { // 2022.2.22调整
                swap(array, i, j);
            } else { // i和j相遇
                swap(array, i, begin);
            }
        }
        quickSort(array, begin, i-1);
        quickSort(array, i+1, end);
    }


    static void quickSort3(int[] array, int start, int end) {
        if (start < 0 || start >= end) return;

        int base = array[start];
        int i = start;
        int j = end;
        while (i < j) {
            while (i < j && array[j] > base) --j;
            while (i < j && array[i] <= base) ++i;
            if (i != j) {
                swap(array, i, j);
            } else {
                // i和j相遇
                swap(array, i, start);
            }
        }
        quickSort3(array, start, i - 1);
        quickSort3(array, i + 1, end);
    }

    static private void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
