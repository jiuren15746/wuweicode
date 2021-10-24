package algo;

/**
 * 字节算法题：
 给定一个数组A，其中元素均为正整数，其长度为n，1 ≤ n ≤ 1000
 同时给定一个目标和sum，1 ≤ sum ≤ 1000
 求数组A中的元素相加得到和为sum的组合数。
 当两种组合有至少一个下标不一样，就被认为是不同的组合
 示例
 输入：A=[5, 5, 10, 2, 3], sum=15
 输出
 4
 解释：
 组合1: A[0] + A[1] + A[3] + A[4]
 组合2: A[0] + A[2]
 组合3: A[1] + A[2]
 组合4: A[2] + A[3] + A[4]
 */
public class AllCombinationWithFixSum {

    /**
     * 采用深度优先思路。在一个位置pos，下一个位置可以选择pos+1，pos+2，。。。
     * @param array
     * @param fixSum
     * @param currentPos 当前所在位置
     * @param currentSum 当前sum
     */
    static public void sum(int[] array, int fixSum, int currentPos, int currentSum) {
        if (currentSum == fixSum) {
            System.out.println("+1");
            return;
        }

        if (currentSum > fixSum) {
            return;
        }

        for (int next = currentPos + 1; next <= array.length - 1; next++) {
            sum(array, fixSum, next, currentSum + array[next]);
        }
    }

    static public void main(String[] args) {
        int[] array = new int[]{5, 5, 10, 2, 3};
        int fixSum = 15;

        sum(array, fixSum, -1, 0);
    }
}
