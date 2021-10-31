package datastructure.array.algo.binarysearch;

import datastructure.array.algo.binarysearch.FindPosition.*;

import java.util.Arrays;
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

        int targetPos = FindPosition.findInsertPosition(array, target).getPosition();
        int[] kclosestNumbers = new int[]{};
        Comparator<Integer> compareFunc = (a, b) ->
                Math.abs(a - target) - Math.abs(b - target);

        for (int i = 1; i <= k; ++i) {
            InsertResult insertResult = null;
            if (targetPos - i >= 0) {
                insertResult = FindPosition.insertValue(
                        kclosestNumbers, array[targetPos - i], compareFunc);
                kclosestNumbers = insertResult.getNewArray();
            }
            if (targetPos+i-1 < array.length) {
                insertResult = FindPosition.insertValue(
                        kclosestNumbers, array[targetPos+i-1], compareFunc);
                kclosestNumbers = insertResult.getNewArray();
            }
        }

        // 取前面k个元素
        int[] result = new int[k];
        System.arraycopy(kclosestNumbers, 0, result, 0, k);
        return result;
    }


    static public void main(String[] args) {
        int[] array = new int[]{1,4,6,8};
        int target = 3;
        int k = 3;

        int[] result = kClosestNumbers(array, target, k);
    }
}
