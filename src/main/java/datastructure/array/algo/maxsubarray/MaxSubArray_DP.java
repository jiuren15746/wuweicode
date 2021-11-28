package datastructure.array.algo.maxsubarray;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

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
public class MaxSubArray_DP {

    static public int[] maxSubArray_DP(int[] array) {
        Map<Integer, int[]> dpFunc = new HashMap<>();
        int maxSum = Integer.MIN_VALUE;
        int maxSumIndex = -1;

        for (int i = 0; i < array.length; ++i) {
            if (i == 0) {
                dpFunc.put(0, new int[]{array[0], 0, 0});
            } else {
                int[] previousResult = dpFunc.get(i-1);
                if (previousResult[0] > 0) {
                    dpFunc.put(i, new int[]{previousResult[0] + array[i], previousResult[1], i});
                } else {
                    dpFunc.put(i, new int[]{array[i], i, i});
                }
            }

            if (dpFunc.get(i)[0] > maxSum) {
                maxSum = dpFunc.get(i)[0];
                maxSumIndex = i;
            }
        }
        return dpFunc.get(maxSumIndex);
    }

    static public int maxSubArray_DP2(int[] nums) {
        Map<Integer, Integer> dpFunc = new HashMap<>();
        int maxSum = Integer.MIN_VALUE;

        for (int i = 0; i < nums.length; ++i) {
            if (i == 0) {
                dpFunc.put(0, nums[0]);
            } else {
                if (dpFunc.get(i-1) > 0) {
                    dpFunc.put(i, dpFunc.get(i-1) + nums[i]);
                } else {
                    dpFunc.put(i, nums[i]);
                }
            }

            if (dpFunc.get(i) > maxSum) {
                maxSum = dpFunc.get(i);
            }
        }
        return maxSum;
    }

    @Test
    public void test() {
        int[] array = {-2,1,-3,4,-1,2,1,-5,4};
        // maxSubArray [4,-1,2,1]
        int[] result = maxSubArray_DP(array);
        Assert.assertEquals(result[0], 6);
        Assert.assertEquals(result[1], 3);
        Assert.assertEquals(result[2], 6);

        int result2 = maxSubArray_DP2(array);
        Assert.assertEquals(result2, 6);
    }
}
