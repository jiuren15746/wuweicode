package datastructure.array.algo.twopointers;

import java.util.Arrays;

/**
 * 给定一个整数数组nums 和一个目标值target，请你在该数组中找出和为目标值的那两个整数，并返回他们的数组下标。
 * 首先排序数组；
 * 使用left，right两个指针；
 * 比较target与left值加right值的和，移动对应的指针；
 * 双指针解法的时间复杂度取决于对应的排序算法，空间复杂度为O(n)。
 *
 * 在两数之和这个case中我们使用碰撞指针的方式来实现，其它两种套路会在后续文章中介绍。
 */
public class TwoNumSum {

//
//    static public void twoNumSum(int[] array, int target) {
//        int[] copy = Arrays.copyOf(array, array.length);
//        int i = copy[0];
//        int j = copy[copy.length-1];
//
//        for(;;) {
//            int compareResult =
//        }
//    }
}
