package datastructure.array.algo.twopointers;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 快排序。相向双指针。
 */
public class QuickSort {

    /**
     * 对array数组从start到end区间范围进行快排。
     * @param array
     * @param begin 开始位置，包含
     * @param end 结束位置，包含
     */
    static public void quickSort(int[] array, int begin, int end) {
        System.out.println("quick sort array: " + toString(array, begin, end));
        int guard = array[begin];
        // 两个哨兵i、j
        int i = begin;
        int j = end;
        for (;;) {
            // 哨兵j先出动
            while (i < j && array[j] > guard) {
                j--;
            }
            // 哨兵i后出动. 哨兵i的比较条件是小于等于。而哨兵j的比较条件是大于
            // 效果就是右边小于的数抛到左边，左边大于等于的数抛到右边。
            while (i < j && array[i] <= guard) {
                i++;
            }

            // 没有相遇，i/j交换
            if (i < j) {
                swap(array, begin, end, i, j);
            }
            // 相遇，与array[begin]交换。结束本轮循环。
            // !!! 相遇位置的数字一定小于等于array[begin]. 因为哨兵j先出动。
            else {
                System.out.println("i and j meet at idx=" + i);
                swap(array, begin, end, begin, i);
                break;
            }
        }

        // 在i的左侧、右侧分别进行快排.
        // !!! 踩过的坑：如果i和j在end位置相遇，那么右侧不存在。所以这里一定要判断新范围两个下标的关系。
        if (begin < i-1) {
            quickSort(array, begin, i - 1);
        }
        if (i + 1 < end) {
            quickSort(array, i + 1, end);
        }
    }

    /**
     * 在数组中交换位置i和j的元素。begin和end只是用来打印日志。
     */
    static private void swap(int[] array, int begin, int end, int i, int j) {
        System.out.println("In " + toString(array, begin, end) + ", swap: " + array[i] + ", " + array[j]);
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
        System.out.println("After swap, array=" + toString(array, begin, end));
    }


    static public void main(String[] args) {

        int[] array = {6, 1, 2, 7, 9, 3, 9, 6, 4, 5, 10, 8};
        quickSort(array, 0, array.length - 1);
        System.out.println("result: " + toString(array, 0, array.length - 1));
    }

    static public String toString(int[] array, int begin, int end) {
        return Arrays.stream(array, begin, end + 1)
                .boxed()
                .collect(Collectors.toList()).toString();
    }
}
