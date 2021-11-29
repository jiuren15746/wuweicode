package datastructure.array.algo;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * 最大子序列和。给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
 * https://leetcode-cn.com/problems/maximum-subarray/
 * 难度：简单
 */
public class MaxSubArray {
    /**
     *  思路1：暴力解法。两个指针i和j。
     *  时间复杂度：O(N^2)
     *  空间复杂度：O(1)
     */
    // 返回子序列数组。
    static public int[] maxSubArray(int[] array) {
        int maxSum = Integer.MIN_VALUE;
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

        int[] result = new int[end-begin+1];
        System.arraycopy(array, begin, result, 0, end-begin+1);
        return result;
    }

    /**
     * 思路2：动态规划思路。
     * 假设f(i)表示以第i个元素结尾的最大子序列和。那么:
     *  f(i+1) = f(i)+array[i+1], 如果f(i)>0
     *  f(i+1) = array[i+1], 如果f(i)<=0
     *
     * 时间复杂度：O(N)
     * 空间复杂度：O(1)
     *
     * 进一步思考，如果f(i)表示第i个元素开始的最大子序列和，可以么？
     * 随着i增加，问题规模在缩小，与DP思路不符合。
     */
    // 返回子序列数组。
    static public int[] maxSubArray_DP(int[] array) {
        // DP函数值。随着i+1滚动计算
        int currentSum = 0;
        int currentSumBeginIdx = 0;
        int currentSumEndIdx = 0;
        // 函数值的最大值
        int maxSum = Integer.MIN_VALUE;
        int begin = 0;
        int end = 0;

        for (int i = 0; i < array.length; ++i) {
            if (i == 0) {
                currentSum = array[0];
                currentSumBeginIdx = currentSumEndIdx = 0;
            } else {
                if (currentSum > 0) {
                    currentSum += array[i];
                    currentSumEndIdx = i;
                } else {
                    currentSum = array[i];
                    currentSumBeginIdx = currentSumEndIdx = i;
                }
            }

            if (currentSum > maxSum) {
                maxSum = currentSum;
                begin = currentSumBeginIdx;
                end = currentSumEndIdx;
            }
        }

        int[] result = new int[end-begin+1];
        System.arraycopy(array, begin, result, 0, end-begin+1);
        return result;
    }

    @Test
    public void test() {
        int[] array = {-2,1,-3,4,-1,2,1,-5,4};

        int[] result = maxSubArray(array);
        Assert.assertEquals(result, new int[]{4,-1,2,1});
    }

    @Test
    public void test_dp() {
        int[] array = {-2,1,-3,4,-1,2,1,-5,4};

        int[] result = maxSubArray_DP(array);
        Assert.assertEquals(result, new int[]{4,-1,2,1});
    }
}
