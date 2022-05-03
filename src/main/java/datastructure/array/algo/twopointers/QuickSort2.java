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
                swap(array, begin, i); // 2022.5.3 把quickSort递归调用放在这里，更清晰
                quickSort(array, begin, i-1);
                quickSort(array, i+1, end);
            }
        }
    }

    static private void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
