package datastructure.array.algo.binarysearch;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * 有一排包裹，一艘船要把这批包裹在 D 天内送到另一个港口。
 * 每一天，我们都会按包裹顺序往传送带上装载包裹。我们装载的重量不会超过船的最大运载重量。
 * 假设船可以当天返回出发港。
 * 返回能在 D 天内将传送带上的所有包裹送达的船的最低运载能力。
 * https://leetcode-cn.com/problems/capacity-to-ship-packages-within-d-days
 * 难度：中等
 *
 * 跟猴子吃香蕉问题类似，这是一个关于运载量的临界值问题。
 * 运载量很大，一天就能送完，运载量降低，需要的天数就会增大。
 * 要找到一个临界运载量，这个运载量，可以D天内送完。但是低于这个运载量就送不完。
 */
public class 船运货物能力 {
    // 返回能够在days天内送完货物的最小载重量。
    static public int lowestCapacity(int[] weights, int days) {
        int max = 0;
        int sum = 0;
        for (int weight : weights) {
            sum += weight;
            if (weight > max) {
                max = weight;
            }
        }
        // 最低运载量等于最重的货物. 最大运载量等于sum(货物重量)
        int lowCapacity = max;
        int highCapacity = sum;
        if (shippingDays(weights, lowCapacity) <= days) {
            return lowCapacity;
        }

        while (!(lowCapacity + 1 == highCapacity)) {
            int midCapacity = (lowCapacity + highCapacity) / 2;
            if (shippingDays(weights, midCapacity) <= days) {
                highCapacity = midCapacity;
            } else {
                lowCapacity = midCapacity;
            }
        }
        return highCapacity;
    }

    // 负重容量为capacity的船，运载weights表示的包裹，需要多少天
    static private int shippingDays(int[] weights, int capacity) {
        int days = 0;
        int tempSum = 0;
        for (int weight : weights) {
            tempSum += weight;
            if (tempSum > capacity) {
                days++;
                tempSum = weight;
            }
        }
        return days+1;
    }

    @Test
    public void test() {
        int[] weights = {1,2,3,4,5,6,7,8,9,10};
        int days = 5;
        Assert.assertEquals(lowestCapacity(weights, days), 15);
    }

    @Test
    public void test2() {
        int[] weights = {1,2,3,1,1};
        int days = 4;
        Assert.assertEquals(lowestCapacity(weights, days), 3);
    }
}
