package datastructure.array.algo.binarysearch;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * 珂珂喜欢吃香蕉。这里有N堆香蕉，警卫已经离开了，将在H小时后回来。
 *
 * 珂珂可以决定她吃香蕉的速度K（单位：根/小时）。
 * 每个小时，她将会选择一堆香蕉，从中吃掉 K 根。如果这堆香蕉少于 K 根，她将吃掉这堆的所有香蕉，然后这一小时内不会再吃更多的香蕉。
 *
 * 珂珂喜欢慢慢吃，但仍然想在警卫回来前吃掉所有的香蕉。
 * 返回她可以在 H 小时内吃掉所有香蕉的最小速度 K（K 为整数）。
 *
 * https://leetcode-cn.com/problems/koko-eating-bananas
 * 难度：中等
 *
 * 思路：
 *  + 不要被piles[] 迷惑了，重点不在这里
 *  + 重点在于找到一个临界速度，大于等于这个速度可以吃完，比这个速度小一点就吃不完。
 *  + 速度范围：最低速度为1，最高速度为max(piles)
 *  + 每次缩小范围，缩小后的范围要包含缩小前的mid。
 *  + 结束条件：速度范围的边界相邻
 */
public class 爱吃香蕉的可可 {

    static public int minEatingSpeed(int[] piles, int limitHours) {
        // 最低速度和最高速度
        int low = 1;
        int high = findMax(piles);
        if (calculateHours(piles, low) <= limitHours) {
            return low;
        }

        // 选择的速度范围：最低速度吃不完，最高速度能吃完
        // 结束条件：范围的边界相邻
        while (!(low + 1 == high)) {
            int midSpeed = (low + high) / 2;
            if (calculateHours(piles, midSpeed) <= limitHours) {
                high = midSpeed;
            } else {
                low = midSpeed;
            }
        }
        return high;
    }

    static private int findMax(int[] piles) {
        int max = Integer.MIN_VALUE;
        for (int pile : piles) {
            if (pile > max) {
                max = pile;
            }
        }
        return max;
    }

    // 以speed速度，吃掉所有香蕉需要的时间。
    static private long calculateHours(int[] piles, int speed) {
        // 为了避免越界，使用long类型
        long hours = 0;
        for (int pile : piles) {
            hours += (int) Math.ceil(pile*1.0/speed); // 用double除法，否则结果会错误
        }
        return hours;
    }

    @Test
    public void test() {
        int[] piles = {3,6,7,11};
        int limitHours = 8;
        Assert.assertEquals(minEatingSpeed(piles, limitHours), 4);
    }

    @Test
    public void test2() {
        int[] piles = {332484035, 524908576, 855865114, 632922376, 222257295, 690155293, 112677673, 679580077, 337406589, 290818316, 877337160, 901728858, 679284947, 688210097, 692137887, 718203285, 629455728, 941802184};
        int limitHours = 823855818;
        Assert.assertEquals(minEatingSpeed(piles, limitHours), 14);
    }
}
