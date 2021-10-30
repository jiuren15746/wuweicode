package datastructure.array.algo.binarysearch;

import datastructure.array.algo.binarysearch.FindPosition.PositionResult;

import java.util.Comparator;

/**
 * https://www.lintcode.com/problem/460/
 *
 * 给一个目标数 target, 一个非负整数 k, 一个按照升序排列的数组 A。
 * 在A中找与target最接近的k个整数。返回这k个数并按照与target的接近程度从小到大排序，
 * 如果接近程度相当，那么小的数排在前面。
 * 1. k是一个非负整数，并且总是小于已排序数组的长度。
 *
 * 输入: A = [1, 4, 6, 8], target = 3, k = 3
 * 输出: [4, 1, 6]
 */
public class KClosestNumbers {


    public static int[] kClosestNumbers(int[] array, int target, int k) {

        PositionResult insertPositionResult = FindPosition.findInsertPosition(array, target);
        int targetPos = insertPositionResult.getPosition();
        System.out.println("target position: " + targetPos);

        int left = targetPos - 1;
        int right = targetPos + 1;
        int[] kclosestNumbers = new int[]{};
        Comparator<Integer> compareFunc = (a, b) ->
                Math.abs(a - target) - Math.abs(b - target);

        for (;;) {
            if (left >= 0) {
                FindPosition.findInsertPosition(kclosestNumbers, array[left], compareFunc);
            }
        }




    }
}
