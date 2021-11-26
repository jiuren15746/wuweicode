package datastructure.array.algo.twopointers;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 快排序。相向双指针。
 * 重点：
 * <li> 两个哨兵i和j。i从左向右走，j从右向左走。
 * <li> 哨兵j循环条件为大于guard。哨兵i循环条件为小于等于guard。
 * <li> 哨兵j先出动。最终哨兵j撞上哨兵i，这样在碰撞位置的数字一定小于等于guard。
 * <li> 在一次循环内，哨兵i和j各自找到不满足条件的位置，交换一次数据。直到i和j相遇。
 * <li> i和j相遇后，相遇位置数据和guard交换。然后在左右两个区域分别进行快排。递归调用。
 * <li> 循环 + 递归。
 */
public class QuickSort {

    /**
     * 对array数组从start到end区间范围进行快排。
     * @param array
     * @param begin 开始位置，包含
     * @param end 结束位置，包含
     */
    static public void quickSort(int[] array, int begin, int end) {
        // !!!退出条件
        if (begin >= end) {
            return;
        }

        // 两个哨兵i、j
        int base = array[begin];
        int i = begin;
        int j = end;

        // 1. 在一次循环内，哨兵i和j各自找到不满足条件的位置，交换一次数据。直到i和j相遇。
        while (i < j) {
            // 哨兵j先出动，直到找到一个数<=base
            // 哨兵i后出动. 哨兵i的比较条件是小于等于。而哨兵j的比较条件是大于
            // 效果就是右边小于的数抛到左边，左边大于等于的数抛到右边。
            while (i < j && array[j] > base) j--;
            while (i < j && array[i] <= base) i++;

            if (i < j) {
                swap(array, i, j);
            } else {
                // !!! 相遇位置的数字一定小于等于array[begin].
                swap(array, begin, i);
            }
        }

        // 在i的左侧、右侧分别进行快排.
        // !!! 踩过的坑：如果i和j在end位置相遇，那么右侧不存在。所以入口出要做判断。
        quickSort(array, begin, i - 1);
        quickSort(array, i + 1, end);
    }

    static private void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    static public String toString(int[] array, int begin, int end) {
        return Arrays.stream(array, begin, end + 1)
                .boxed()
                .collect(Collectors.toList())
                .toString();
    }
}
