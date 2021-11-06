package datastructure.array.algo.twopointers;

import java.util.ArrayList;
import java.util.List;

/**
 * 给定一个整数数组nums 和一个目标值target，请你在该数组中找出和为目标值的那两个整数，并返回他们的数组下标。
 * 首先排序数组；
 * 使用left，right两个指针；
 * 比较target与left值加right值的和，移动对应的指针；
 * 双指针解法的时间复杂度取决于对应的排序算法，空间复杂度为O(n)。
 *
 * 在两数之和这个case中我们使用碰撞指针的方式来实现，其它两种套路会在后续文章中介绍。
 * 所谓碰撞指针，是指在有序数组中定义left（数组起始位置）、right（数组终止位置）两个指针，
 * 在遍历时根据对应条件的不同来判断应该移动哪个指针，进而从数组两端遍历数组。
 *
 * see https://segmentfault.com/a/1190000023552474
 */
public class TwoNumSum {

    /**
     * 返回两数之和等于target的位置下标。
     *
     * @param array
     * @param target
     * @return
     */
    static public List<int[]> twoNumSum(int[] array, int target) {
        List<int[]> result = new ArrayList<>();
        // 首先对数组进行快排。
        QuickSort.quickSort(array, 0, array.length-1);
        System.out.println("after sort: "
                + QuickSort.toString(array, 0, array.length - 1));

        // 在一轮循环，i或j移动一个位置。
        int i = 0;
        int j = array.length - 1;
        while (i < j) {
            int compareResult = target - array[i] - array[j];
            if (compareResult == 0) {
                System.out.println("sum: " + array[i] + ", " + array[j]);
                result.add(new int[]{i, j});
                // 判断接下来移动哪个指针
                if (array[j] == array[j-1]) {
                    j--;
                } else if (array[i] == array[i+1]) {
                    i++;
                } else { // i右移或j左移都可以
                    i++;
                }
            } else if (compareResult < 0) {
                j--;
            } else {
                i++;
            }
        }
        return result;
    }
}
