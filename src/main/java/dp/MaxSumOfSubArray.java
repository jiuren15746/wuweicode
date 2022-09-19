package dp;

/**
 * 连续子数组的最大和
 * https://learnku.com/articles/47969
 */
public class MaxSumOfSubArray {

    public static int maxSum(int[] array) {
        int maxSum = array[0];
        for (int i = 1; i < array.length; ++i) {
            array[i] += array[i-1] >= 0 ? array[i-1] : 0;
            maxSum = array[i] > maxSum ? array[i] : maxSum;
        }
        return maxSum;
    }

    // 返回最大和子数组的sum, 起始下标，结束下标
    public static int[] maxSumPosition(int[] array) {
        // 第i个数字结尾的最大子数组和，以及最大子数组的起始下标
        int[][] subArraySum = new int[array.length][];
        subArraySum[0] = new int[]{array[0], 0};
        int maxSum = array[0];
        int maxSumIndex = 0;

        for (int i = 1; i < array.length; ++i) {
            if (subArraySum[i - 1][0] > 0) {
                subArraySum[i] = new int[]{subArraySum[i - 1][0] + array[i], subArraySum[i - 1][1]};
            } else {
                subArraySum[i] = new int[]{array[i], i};
            }
            if (subArraySum[i][0] > maxSum) {
                maxSum = subArraySum[i][0];
                maxSumIndex = i;
            }
        }
        return new int[]{subArraySum[maxSumIndex][0], subArraySum[maxSumIndex][1], maxSumIndex};
    }

    public static void main(String[] args) {
//        int[] array = {-2,1,-3,4,-1,2,1,-5,4};
//        System.out.println(maxSum(array));

        int[] array = {-2,1,-3,4,-1,2,1,-5,4};
        int[] result = maxSumPosition(array);
        System.out.println("maxSum=" + result[0]);
        System.out.println("startPos=" + result[1]);
        System.out.println("endPos=" + result[2]);
    }
}
