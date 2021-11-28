package datastructure.array.algo.maxsubarray;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * 最大子序列和。给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
 * https://leetcode-cn.com/problems/maximum-subarray/
 *
 * 思路1：暴力解法。
 * 时间复杂度：O(N*N)
 * 空间复杂度：O(1)
 */
public class MaxSubArray {

    // 返回int数组，包含：最大和数值，子序列起始下标、结束下标。
    static public int[] maxSubArray(int[] array) {
        int maxSum = 0;
        int begin = 0;
        int end = 0;

        for (int i = 0; i < array.length; ++i) {
            if (array[i] <= 0) { // 最大子序列不能以负数开始
                continue;
            }
            int tempSum = 0;
            for (int j = i; j < array.length; ++j) {
                tempSum += array[j];
                if (tempSum > maxSum) {
                    maxSum = tempSum;
                    begin = i;
                    end = j;
                }
            }
        }
        return new int[]{maxSum, begin, end};
    }

    @Test
    public void test() {
        int[] array = {-2,1,-3,4,-1,2,1,-5,4};
        // maxSubArray [4,-1,2,1]
        int[] result = maxSubArray(array);
        Assert.assertEquals(result[0], 6);
        Assert.assertEquals(result[1], 3);
        Assert.assertEquals(result[2], 6);
    }
}
